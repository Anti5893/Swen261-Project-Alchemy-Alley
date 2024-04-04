import { Component } from "@angular/core";
import { Router } from "@angular/router";
import { NgZone } from "@angular/core";

import { User } from "../user";
import { UserService } from "../user.service";
import { CredentialsService } from "../credentials.service";
import { HtmlParser } from "@angular/compiler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  
	username: string = "";
	password: string = "";
	isAuthenticated = false;
	requestSent = false;
	
	constructor(
		private userService: UserService,
		private credentialsService: CredentialsService,
		private router: Router,
		private ngZone : NgZone
	) {}

	onMouseEnter(buttonName : HTMLElement): void{
		buttonName.innerHTML = 'Login<i class="fa-solid fa-door-open"></i>'
	}
	onMouseLeave(buttonName : HTMLElement): void{
		buttonName.innerHTML = 'Login<i class="fa-solid fa-door-closed"></i>'
	}

	fieldsFull(): boolean {
		return this.username !== "" && this.password !== "";
	}

	showErrorMesssage(): boolean {
		return this.requestSent && !this.isAuthenticated;
	}

	animateLogin(route: string[]): void{
		const loginBox = document.getElementById('login-box');
		if(loginBox) {
			loginBox.animate(
				[
			  		{ 
						transform: 'translate(250%, -50%)'
					}
				],
				{
			  		duration: 750,
			  		easing: 'ease-out',
			  		fill: 'forwards'
				}
		  	).onfinish = () => {
				this.ngZone.run(()=>{
					this.router.navigate(route);
				})
				
		  	};
		}
	}

	animateFailedLogin(): void{
		const loginBox = document.getElementById('login-box');
		if(loginBox){
			loginBox.animate(
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
	
	onClick(username: string, password: string): void {
		this.userService.authenticateUser({username,password} as User).subscribe(
			(response) => {
				if(response.status == 200) {
			  		this.isAuthenticated = true;
			  		const loggedUser = response.body!;
			  		this.credentialsService.storeCurrentUser(loggedUser);
			  		if(loggedUser.admin) {
			   			this.animateLogin(['/admin']);
			  		} else {
						this.animateLogin(['/catalog']);
			  		}
				}
				this.requestSent = true;
		  	},
		  	(error) => {
				this.animateFailedLogin()
				if(this.fieldsFull()) {
			  		this.isAuthenticated = false;
				}
				this.requestSent = true;
		  	}
		);
	}
}
