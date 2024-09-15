import { AuthenticationResponse } from './../../../../services/models/authentication-response';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { OnInit } from '@angular/core';
import { TokenService } from '../../../../services/token/token.service';
import { Inject } from '@angular/core';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule, RouterModule],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent implements OnInit{
  ngOnInit(): void {
    const linkColor = document.querySelectorAll('.nav-link');
    linkColor.forEach(l =>{
      if (window.location.href.endsWith(l.getAttribute('href') || '')){
        l.classList.add('active');
      }
      l.addEventListener('click', () => {
        linkColor.forEach(l => {
          l.classList.remove('active');
        });
        l.classList.add('active');
      });
    });
  }

  firstName: string | null = null;

  constructor(@Inject(TokenService) private tokenService: TokenService) { // Use @Inject decorator
    this.loadUserDetails();
  }

  loadUserDetails(): void {
    const token = this.tokenService.token;
    if (token) {
      const payload = this.decodeToken(token);
      this.firstName = payload.firstName; // Adjust according to your token structure
    }
  }

  decodeToken(token: string): any {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload)); // Decode the token payload
  }


}
