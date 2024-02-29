import { Injectable } from '@angular/core';
import { User } from '../user';




@Injectable({
  providedIn: 'root'
})
export class CredentialsService {
  
  storeCurrentUser(user : User){
    localStorage.setItem('currentUsername', user.username)
    localStorage.setItem('currentPassword', user.password)
  }
  removeCurrentUser(){
    localStorage.removeItem('currentUsername');
    localStorage.removeItem('currentPassword');
  }
}
