import { Injectable } from '@angular/core';


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

}
