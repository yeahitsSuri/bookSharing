import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Router } from '@angular/router';
import { inject } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenService {

  set token(token: string) {
    if (typeof window !== 'undefined') { // Check if in a browser environment
      localStorage.setItem('token', token);
    }
  }

  get token(): string {
    if (typeof window !== 'undefined') { // Check if in a browser environment
      return localStorage.getItem('token') as string;
    }
    return ''; // Return an empty string or handle accordingly
  }

  isTokenNotValid(): boolean {
    return !this.isTokenValid();
  }

  private isTokenValid(): boolean {
    const token = this.token;
    if (!token) {
      return false;
    }
    // decode the token
    const jwtHelper = new JwtHelperService();
    const isTokenExpired = jwtHelper.isTokenExpired(token);
    if (isTokenExpired) {
      localStorage.clear();
      return false;
    }
    return true;
  }
}
