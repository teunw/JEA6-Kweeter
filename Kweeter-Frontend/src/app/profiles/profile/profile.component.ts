import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {ActivatedRoute} from '@angular/router';
import {IProfile, Profile} from '../../classes/profile';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../classes/kweet';
import {LoginService} from '../../services/login.service';
import {FollowService} from '../../services/follow.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public profile: IProfile;
  public kweets: IKweet[] = [];
  public isProfileSelf = false;
  public isProfileBeingFollowed: boolean = null;

  constructor(private profileService: ProfileService,
              private followService: FollowService,
              private loginService: LoginService,
              private kweetService: KweetService,
              private route: ActivatedRoute) {
  }

  public getProfile() {
    return new Profile(this.profile);
  }

  ngOnInit() {
    console.log(this.route.params['id']);
    this.route.params.subscribe(async param => {
      if (param.id !== undefined && param.id !== null) {
        const profileData = await this.profileService.getProfile(param.id);
        this.profile = profileData;
        this.isProfileSelf = this.loginService.getLoginInfo().username === this.profile.username;

        this.kweetService.getKweetsForProfile(this.profile)
          .subscribe(kweetData => {
            this.kweets = kweetData;
          });
        this.followService.getFollowers(this.profile.id)
          .subscribe(profileFollower => {
            console.log(profileFollower.followingProfiles);
            console.log(this.loginService.getLoginInfo().username);
            this.isProfileBeingFollowed = profileFollower.followingProfiles
              .filter(followingUser => followingUser.username.toString() === this.loginService.getLoginInfo().username.toString())
              .length > 0;
          });
      }
    });
  }

  public toggleFollow() {
    if (!this.isProfileBeingFollowed) {
      this.followService.startFollowing(this.profile)
        .subscribe(d => {
        });
    } else {
      this.followService.stopFollowing(this.profile)
        .subscribe(d => {
        });
    }
    this.isProfileBeingFollowed = !this.isProfileBeingFollowed;
  }
}

