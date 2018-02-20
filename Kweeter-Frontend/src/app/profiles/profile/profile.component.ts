import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {Observable} from 'rxjs/Observable';
import {Profile} from '../../profile';
import {ActivatedRoute, ActivatedRouteSnapshot} from '@angular/router';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public profiles: Observable<Profile[]>;

  constructor(private profileService: ProfileService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    console.log(this.route.params['id']);
    if (this.route.params['id']) {
      this.profiles = Observable.create(function (observer) {
        observer.next(this.profileService.getProfile(this.route.params['id']));
      });
    } else {
      this.profiles = this.profileService.getProfiles();
    }
  }

}
