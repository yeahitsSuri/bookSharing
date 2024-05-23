package com.yeahitsSuri.booksharing.auth;

import com.yeahitsSuri.booksharing.email.EmailService;
import com.yeahitsSuri.booksharing.email.EmailTemplateName;
import com.yeahitsSuri.booksharing.role.RoleRepo;
import com.yeahitsSuri.booksharing.security.JwtService;
import com.yeahitsSuri.booksharing.user.Token;
import com.yeahitsSuri.booksharing.user.TokenRepo;
import com.yeahitsSuri.booksharing.user.User;
import com.yeahitsSuri.booksharing.user.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final TokenRepo tokenRepo;
  private final RoleRepo roleRepo;
  private final EmailService emailService;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Value("${spring.application.mailing.frontend.activation-url}")
  private String activationUrl;

  public void register(RegistrationRequest request) throws MessagingException {
    var userRole = roleRepo.findByName("USER")
            // todo - better exception handling
            .orElseThrow(() -> new IllegalStateException("ROLE USER was not initiated"));
    var user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .accountLocked(false)
            .enabled(false)
            .roles(List.of(userRole))
            .build();
    userRepo.save(user);
    sendValidationEmail(user);
  }

  private String generateAndSaveActivationToken(User user) {
    // Generate a token
    String generatedToken = generateActivationCode(6);
    var token = Token.builder()
            .token(generatedToken)
            .createdAt(LocalDateTime.now())
            .expiresAt(LocalDateTime.now().plusMinutes(15))
            .user(user)
            .build();
    tokenRepo.save(token);

    return generatedToken;
  }

  private void sendValidationEmail(User user) throws MessagingException {
    var newToken = generateAndSaveActivationToken(user);

    emailService.sendEmail(
            user.getEmail(),
            user.getFullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl,
            newToken,
            "Account activation"
    );
  }

  private String generateActivationCode(int length) {
    String characters = "0123456789";
    StringBuilder codeBuilder = new StringBuilder();

    SecureRandom secureRandom = new SecureRandom();

    for (int i = 0; i < length; i++) {
      int randomIndex = secureRandom.nextInt(characters.length());
      codeBuilder.append(characters.charAt(randomIndex));
    }

    return codeBuilder.toString();
  }

  public AuthenticationResponse authenticate(AuthticationRequest request) {
    var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
    );
    var claims = new HashMap<String, Object>();
    var user = ((User) auth.getPrincipal());
    claims.put("fullName", user.getFullName());
    var jwtToken = jwtService.generateToken(claims, user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  @Transactional
  public void activateAccount(String token) throws MessagingException {
    Token savedToken = tokenRepo.findByToken(token).orElseThrow(
            // exception has to be defined
            () -> new RuntimeException("Invalid token"));
    if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
      sendValidationEmail(savedToken.getUser());
      throw new MessagingException("Token has expired. New token has been sent to your email.");
    }
    var user = userRepo.findById(savedToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("user not found"));
    user.setEnabled(true);
    userRepo.save(user);
    savedToken.setValidatedAt(LocalDateTime.now());
    tokenRepo.save(savedToken);
  }
}

