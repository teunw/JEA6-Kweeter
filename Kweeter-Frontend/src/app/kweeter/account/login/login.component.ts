import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../../services/login.service";
import {ProfileService} from "../../../services/profile.service";
import {Router} from "@angular/router";
import {IProfile} from "../../../profile";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public email: string;
  public isLoggingIn: boolean = false;

  constructor(private loginService: LoginService, private profileService: ProfileService, private router: Router) {
  }

  ngOnInit() {
  }

  public login() {
    this.loginService.saveLoginEmail(this.email);
    this.isLoggingIn = true;
    this.profileService
      .getProfileByEmail(this.email)
      .subscribe(res => {
        if (res instanceof Object) {
          const profile = res as IProfile;
          this.router.navigate([`profiles/${profile.id}`]);
        }
      })
  }

  formKeyUp(e: KeyboardEvent) {
    // 13 == enter
    if (e.keyCode == 13) {
      this.login();
    }
  }
}
