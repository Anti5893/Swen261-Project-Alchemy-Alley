import { Component, Input } from '@angular/core';
import { Observable, map } from 'rxjs';

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
  alreadyExists = false;
  buttonClicked = false;
  constructor(private userService: UserService, private router : Router) { }

  
  registerOnClick(username : string , password : string){
    this.userService.authenticateUser({username, password} as User).subscribe(
      (response) =>{
        this.alreadyExists = (response.status == 200)
      },
      (error) =>{
        this.alreadyExists = false;
        this.userService.addUser({username, password} as User).subscribe();
        this.router.navigate(['/login']);
      }
    )
  }
}

