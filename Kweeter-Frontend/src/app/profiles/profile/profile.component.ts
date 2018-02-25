import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {ActivatedRoute} from '@angular/router';
import {IProfile} from '../../profile';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public profile: IProfile;
  public kweets: IKweet[] = [];

  constructor(private profileService: ProfileService, private kweetService: KweetService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    console.log(this.route.params['id']);
    this.route.params.subscribe(param => {
      if (param.id !== undefined && param.id !== null) {
        this.profileService.getProfile(param.id)
          .subscribe(profileData => {
            this.profile = profileData;

            this.kweetService.getKweetsForProfile(this.profile)
              .subscribe(kweetData => {
                this.kweets = kweetData;
              });
          });
      }
    });
  }
}
