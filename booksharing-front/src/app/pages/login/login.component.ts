import { AuthenticationRequest } from './../../services/models/authentication-request';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  authRequest: AuthenticationRequest = {
    email: '',
    password: ''
  };
  errorMsg: Array<string> = [];

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {

  }
  login(): void {
    this.errorMsg = []; // always clear the error message before setting it
    this.authService.authenticate({
      body: this.authRequest
    }).subscribe({
      next: () => {
        //todo save the token
        this.router.navigate(['/books']);
      },
      error: (error) => {
        console.log(error);
        if (error.error.validationErrors) {
          this.errorMsg = error.error.validationErrors;
        } else if (error.error.businessErrorDescription) {
          this.errorMsg.push(error.error.businessErrorDescription);
        } else {
          this.errorMsg.push(error.error.error);
        }
      }
    });
  }

  register(): void {
    this.router.navigate(['/register']);
  }
}
