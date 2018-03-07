import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../../services/login.service';
import {ProfileService} from '../../../services/profile.service';
import {Router} from '@angular/router';
import 'materialize-css';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public email: string;
  public password: string;
  public isSavingChanges = false;

  constructor(private loginService: LoginService, private profileService: ProfileService, private router: Router) {
  }

  ngOnInit() {
  }

  public login() {
    this.isSavingChanges = true;
    this.loginService
      .attemptLogin(this.email, this.password)
      .then((res) => {
        M.toast({html: 'You are logged in'});
        this.router.navigate(['/']);
      })
      .catch((res) => {
        M.toast({html: 'Invalid login'});
        this.password = '';
      });
  }

  formKeyUp(e: KeyboardEvent) {
    // 13 == enter
    if (e.keyCode === 13) {
      this.login();
    }
  }
}
