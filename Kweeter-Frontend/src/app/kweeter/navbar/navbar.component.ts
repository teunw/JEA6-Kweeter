import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';
import {IProfile} from '../../profile';

declare var $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  public profile: IProfile;

  constructor(public loginService: LoginService) {
  }

  ngOnInit(): void {
    this.loginService
      .loginInfo
      .subscribe(data => {
        this.profile = data as IProfile;
        $(function () {
          $(".dropdown-trigger").dropdown();
        });
      });

    $(document).ready(function () {
      $('.sidenav').sidenav();
    });
  }
}
