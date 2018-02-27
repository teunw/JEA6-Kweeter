import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../../services/login.service";
import {ProfileService} from "../../../services/profile.service";
import {Router} from "@angular/router";
import {IProfile} from "../../../profile";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public email: string;
  public isSavingChanges: boolean = false;

  constructor(private loginService: LoginService, private profileService: ProfileService, private router: Router) {
  }

  ngOnInit() {
  }

  public login() {
    this.isSavingChanges = true;
    this.profileService
      .getProfileByEmail(this.email)
      .subscribe(res => {
        if (res instanceof Object) {
          const profile = res as IProfile;
          this.loginService.saveLoginInfo(profile);
          //TODO Implement navigation
          //this.router.navigate([`profiles/${profile.id}`]);
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
