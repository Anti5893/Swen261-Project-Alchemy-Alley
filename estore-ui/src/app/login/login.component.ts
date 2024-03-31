import { Component } from '@angular/core';
import { Router} from '@angular/router';

import { User } from '../user';
import { UserService } from '../user.service';
import { CredentialsService } from '../credentials.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  users: User[] = [];
  isAuthenticated = false;
  requestSent = false;

  constructor(private userService: UserService, private credentialsService: CredentialsService, private router: Router) {}

  fieldsFull(): boolean {
    return this.password != '' && this.username != '';
  }
 
  showErrorMesssage(): boolean {
    return this.requestSent && !this.isAuthenticated;
  }

  animateLogin(route: string[]): void{
    const loginBox = document.getElementById('login-box')
    if(loginBox){
      loginBox.animate(
        [
          {transform : 'translate(250%, -50%)'}
        ],
        {
          duration: 1000,
          easing: 'ease-out',
          fill : 'forwards'
        }
      ).onfinish = ()=>{
        this.router.navigate(route);
        // const navRight = document.getElementById('nav-right');
        // if(navRight){
        //   navRight.animate(
        //     [
        //       {visibility : 'visible'},
        //       {opacity : '1'}
        //     ],
        //     {
        //       duration: 1000,
        //       easing: 'ease-in',
        //       fill : 'forwards'
        //     }
        //   )
        // }
      }
    }
  }
  
  onClick(username: string, password: string): void {
    this.userService.authenticateUser({username,password} as User).subscribe(
      (response) => {
        if(response.status == 200) {
          this.isAuthenticated = true;
          const loggedUser = response.body!;
          this.credentialsService.storeCurrentUser(loggedUser);
          if(loggedUser.admin) {
           this.animateLogin(['/admin'])
          } else {
            this.animateLogin(['/catalog']);
          }
        }
        this.requestSent = true;
      },
      (error) => {
        if(this.fieldsFull()) {
          this.isAuthenticated = false;
        }
        this.requestSent = true;
      }
    );
  }
  
}
