import { Component , Input} from '@angular/core';
import { Router } from '@angular/router';

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
  buttonClicked = false;

  
  constructor(private userService : UserService, private credentialsService : CredentialsService, private router : Router){ }

  fieldsFull(){
    return(this.password != '' && this.username != '');
  }

  storeCurrentUser(username : string, password : string){
    if(this.username == 'Admin' && this.password == 'Admin123'){
      this.credentialsService.storeCurrentUser({username,password,isAdmin:true} as User)
    }
    else{
      this.credentialsService.storeCurrentUser({username, password} as User)
    }
    
  }

 
  showErrorMesssage(){
    return (this.buttonClicked && !this.isAuthenticated)
  }

  //Again - not sure why the first .subscribe is crossed out but it works and is cleaner than my other solution
  onClick(username : string, password : string){
    this.buttonClicked = true;
    this.userService.authenticateUser({username,password} as User).subscribe(
      (response) =>{
        if(response.status === 200){
          this.isAuthenticated = true;
          this.storeCurrentUser(username,password);
          this.router.navigate(['/catalog']);
        }
      },
      (error) =>{
        if(this.fieldsFull()){
          this.isAuthenticated = false;
        }
      }
    )
  }
}
