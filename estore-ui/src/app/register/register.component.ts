import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { User } from '../user';
import { UserService } from '../user.service';

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

  constructor(private userService: UserService, private router: Router) {}

  fieldsFull(): boolean {
    return this.password != '' && this.username != '' && this.passwordConfirm != '';
  }

  showPasswordsDontMatch(): boolean {
    return !this.passwordsMatch && this.buttonClicked;
  }

  registerOnClick(username: string, password: string): void {
    this.buttonClicked = true;

    if(this.password != this.passwordConfirm) {
      this.passwordsMatch = false;
      return;
    }

    if(this.fieldsFull()) {
      this.userService.addUser({username,password} as User).subscribe(
        (response) => {
          if(response.status == 201) {
            this.router.navigate(['/login']);
          }
        },
        (error) => {
          if(error.status == 409) {
            this.showErrorMessage = true;
          }
        }
      );
    }
  }
}

