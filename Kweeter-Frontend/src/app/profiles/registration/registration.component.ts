import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {LoginService} from '../../services/login.service';
import {IProfile} from '../../classes/profile';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  public isLoggingIn = false;
  public profile: IProfile | any = {};

  constructor(private profileService: ProfileService, private loginService: LoginService) {
  }

  ngOnInit() {

  }

  formKeyUp($event: KeyboardEvent) {
    if ($event.keyCode === 13) {
      this.attemptRegistration();
    }
  }

  async attemptRegistration() {
    await this.profileService.createProfile(this.profile);
    await this.loginService.attemptLogin(this.profile.emailAddress, this.profile.password);
  }

}
