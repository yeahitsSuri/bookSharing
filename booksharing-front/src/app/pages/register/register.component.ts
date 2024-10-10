import { Component } from '@angular/core';
import { RegistrationRequest } from './../../services/models/registration-request';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  registerRequest: RegistrationRequest = {
    email: '',
    firstName: '',
    lastName: '',
    password: '',
  };
  errorMsg: Array<string> = [];

  constructor(private router: Router,
    private authService: AuthenticationService,
    private tokenService: TokenService
  ) { }

  register(): void {
    this.errorMsg = []; // always clear the error message before setting
    this.authService.register({
      body: this.registerRequest
    }).subscribe({
      next: () => {
        this.router.navigate(['/activate-account']);
      },
      error: (err) => {
        console.log('error', err);
        this.errorMsg = err.error?.validationErrors || ['An unexpected error occurred.'];
      }
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }
}
