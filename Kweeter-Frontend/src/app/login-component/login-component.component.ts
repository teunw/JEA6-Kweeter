import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../services/profile.service';
import {Observable} from 'rxjs/Observable';
import {Profile} from '../domain/profile';

@Component({
  selector: 'app-login-component',
  templateUrl: './login-component.component.html',
  styleUrls: ['./login-component.component.css'],
  providers: [ProfileService]
})
export class LoginComponentComponent implements OnInit {

  private profiles: Observable<Profile[]>;

  constructor(private profileService: ProfileService) {
  }

  ngOnInit() {
    this.profiles = this.profileService.getAllProfiles();
  }

}
