import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgZone } from '@angular/core';

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
  buttonClicked = false;
  showErrorMessage = false;
  passwordsMatch = true;

  constructor(private userService: UserService, private router: Router, private ngZone : NgZone) {}

  animateRegister(route: string[]): void {
    const registerBox = document.getElementById('registerBox');
    if(registerBox) {
      registerBox.animate(
        [
          {
            transform: 'translate(250%,-50%)'
          }
        ],
        {
          duration : 750,
          easing : 'ease-out',
          fill : 'forwards'
        }
      ).onfinish = () => {
        this.ngZone.run(()=>{
          this.router.navigate(route)
        })
      }
    }
  }

  animateFailedRegister(): void{
		const registerBox = document.getElementById('registerBox');
    console.log("GOT HERE")
		if(registerBox){
			registerBox.animate(
				[
    			{transform: 'translate(-50%, -50%) rotate(0deg)'},
					{transform: 'translate(-50%,-50%) rotate(-10deg)'},
					{transform: 'translate(-50%,-50%) rotate(10deg)'},
					{transform: 'translate(-50%,-50%) rotate(-10deg)'},
					{transform: 'translate(-50%,-50%) rotate(10deg)'},
					{transform: 'translate(-50%,-50%) rotate(0deg)'},
				],
				{
					duration: 600,
				}
			)
		}
	}

  fieldsFull(): boolean {
    return this.password !== "" && this.username !== "" && this.passwordConfirm !== "";
  }

  showPasswordsDontMatch(): boolean {
    return !this.passwordsMatch && this.buttonClicked;
  }

  isStrongPassword() {
    let match = this.password.match(/^(?=.*[A-Z])(?=.*\d)(?=.*[^A-Za-z0-9]).{8,}$/);
    return match !== null;
  }

  registerOnClick(username: string, password: string): void {
    this.buttonClicked = true;
    

    if(this.password != this.passwordConfirm) {
      this.passwordsMatch = false;
      return;
    }

    if(this.fieldsFull() && this.isStrongPassword()) {
      this.userService.addUser({username,password} as User).subscribe(
        (response) => {
          if(response.status == 201) {
            this.animateRegister(['/login'])
          }
        },
        (error) => {
          if(error.status == 409) {
            this.showErrorMessage = true;
          }
        }
      );
    }else{
      this.animateFailedRegister()
    }
  }
}

