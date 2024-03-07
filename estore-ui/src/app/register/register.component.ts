import { Component, Input } from '@angular/core';
import { Observable, catchError, map } from 'rxjs';

import { User } from '../../user';
import { UserService } from '../user.service';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})

export class RegisterComponent {
  username: string = '';
  password: string = '';
  passwordConfirm: string = '';
  users: User[] = [];
  buttonClicked = false;
  showErrorMessage = false;
  passwordsMatch = true;
  constructor(private userService: UserService, private router : Router) { }

  fieldsFull(){
    return(this.password != '' && this.username != '' && this.passwordConfirm != '');
  }

  showPasswordsDontMatch(){
    return(!this.passwordsMatch && this.buttonClicked);
  }

  
  registerOnClick(username: string, password: string) {
    this.buttonClicked = true;

    if(this.password != this.passwordConfirm){
      this.passwordsMatch = false;
      return;
    }
    if(this.fieldsFull()){
      this.userService.addUser({username,password} as User).subscribe(
        (response) =>{
          if(response.status == 201){
            this.router.navigate(['/login']);
            console.log("User Registered Successfully");
          }
        },
        (error) =>{
          if(error.status == 409){
            this.showErrorMessage = true;
          }
        }
      )
    }
  }
}

