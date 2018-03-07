import {Component, OnInit} from '@angular/core';
import {LoginService} from "./services/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(private loginService: LoginService) {

  }

  ngOnInit(): void {
    if (!this.loginService.hasAuthToken()) {
      console.log('no auth');
      return;
    }
    this.loginService._loginInfo.next(this.loginService.validateLogin(this.loginService.getAuthToken()));
  }
}
