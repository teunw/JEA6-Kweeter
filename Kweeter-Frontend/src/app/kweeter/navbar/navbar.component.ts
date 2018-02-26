import {Component, OnInit} from '@angular/core';
import {LoginService} from '../../services/login.service';

declare var $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public loginService: LoginService) {
  }

  ngOnInit(): void {
    $(function() {
      $(".dropdown-trigger").dropdown();
    });
  }
}
