import { Component , Input} from '@angular/core';
import { Router} from '@angular/router';

import { User } from '../../user';
import { UserService } from '../user.service';
import { CredentialsService } from '../credentials.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  username : string = '';
  password : string = '';
  users: User[] = [];
  isAuthenticated = false;
  requestSent = false;

  
  constructor(private userService : UserService, private credentialsService : CredentialsService, private router : Router){ }

  fieldsFull(){
    return(this.password != '' && this.username != '');
  }


 
  showErrorMesssage(){
    return (this.requestSent && !this.isAuthenticated)
  }

 
  onClick(username : string, password : string){
    this.userService.authenticateUser({username,password} as User).subscribe(
      (response) =>{
        if(response.status == 200){
          this.isAuthenticated = true;
          const loggedUser = response.body!;
          this.credentialsService.storeCurrentUser(loggedUser);
          this.router.navigate(['/catalog']);
        }
        this.requestSent = true;
      },
      (error) =>{
        if(this.fieldsFull()){
          this.isAuthenticated = false;
        }
        this.requestSent = true;
      }
    )
  }
}
