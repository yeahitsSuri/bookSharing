import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { CodeInputModule } from 'angular-code-input';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [CodeInputModule, CommonModule, FormsModule, HttpClientModule],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss'
})
export class ActivateAccountComponent {
    message = '';
    isOkay: boolean = true;
    submitted: boolean = false;

    constructor(
      private route: Router,
      private authService: AuthenticationService
    ) { }

    onCodeCompleted(token: string):void {
      this.confirmAccount(token);
    }

    private confirmAccount(token: string): void {
      this.authService.confirm({token}).subscribe({
        next: () => {
          this.message = 'Your account has been activated.\n Please login to your account.';
          this.submitted = true;
          this.isOkay = true;
        },
        error: () => {
          this.message = "Token has been expired.";
          this.submitted = true;
          this.isOkay = false;
        }
      });
    }

    navigateToLogin(): void {
      this.route.navigate(['/login']);
    }
}
