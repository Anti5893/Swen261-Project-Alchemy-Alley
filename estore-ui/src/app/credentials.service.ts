import { Injectable } from '@angular/core';

import { User } from '../user';


@Injectable({
  providedIn: 'root'
})
export class CredentialsService {
  
  storeCurrentUser(user : User){
    localStorage.setItem('currentUsername', user.username)
    localStorage.setItem('currentPassword', user.password)
    localStorage.setItem('currentCart', user.cart.toString())
    localStorage.setItem('currentUnlocked', user.unlocked.toString())
    if(user.isAdmin == true){
      localStorage.setItem('currentAdmin', 'true')
    }
  }
  removeCurrentUser(){
    localStorage.removeItem('currentUsername');
    localStorage.removeItem('currentPassword');
    if(localStorage.getItem('currentAdmin') != null){
      localStorage.removeItem('currentAdmin')
    }
  }

  isLoggedIn() : boolean{
    return (localStorage.getItem('currentUsername') != null && localStorage.getItem('currentPassword') != null);
  }

  isAdmin() : boolean{
    return (localStorage.getItem('currentAdmin') == 'true');
  }
}
