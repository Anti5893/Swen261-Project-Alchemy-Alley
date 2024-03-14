import { Component } from '@angular/core';
import { CredentialsService } from './credentials.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  constructor(public credentialsService: CredentialsService) {}

}
