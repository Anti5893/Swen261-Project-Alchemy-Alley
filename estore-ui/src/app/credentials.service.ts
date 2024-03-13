import { Injectable } from '@angular/core';

import { User } from './user';

@Injectable({ providedIn: 'root' })
export class CredentialsService {
  
  storeCurrentUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  removeCurrentUser(): void {
    localStorage.removeItem('user');
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('user') != null;
  }
  
  getUser(): User | null {
    if(this.isLoggedIn()) {
      const userJson = JSON.parse(localStorage.getItem('user') || '{}');
      return userJson;
    }

    return null;
  }

  isAdmin(): boolean {
    return this.getUser()?.admin == true;
  }

}
