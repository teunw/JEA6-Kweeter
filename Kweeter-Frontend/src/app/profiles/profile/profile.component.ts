declare function require(s:string);
import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {ActivatedRoute} from '@angular/router';
import {IProfile, Profile} from '../../profile';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';
import {LoginService} from '../../services/login.service';
import {FollowService} from '../../services/follow.service';
const md5 = require("blueimp-md5");

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public profile: IProfile;
  public kweets: IKweet[] = [];
  public isProfileSelf = false;
  public profileImageLink = "";
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
    this.route.params.subscribe(param => {
      if (param.id !== undefined && param.id !== null) {
        this.profileService.getProfile(param.id)
          .subscribe(profileData => {
            this.profile = profileData;
            this.profileImageLink = this.getGravatarLink();
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

  public getGravatarLink() {
    return this.gravatar(this.profile.emailAddress, {});
  }

  public gravatar(email, options) {
    // using md5() from here: http://www.myersdaily.org/joseph/javascript/md5-text.html
    //check to make sure you gave us something
    var options = options || {},
      base,
      params = [];

    //set some defaults, just in case
    options = {
      size: options.size || "50",
      rating: options.rating || "g",
      secure: options.secure || (location.protocol === 'https:'),
      backup: options.backup || ""
    };

    //setup the email address
    email = email.trim().toLowerCase();

    //determine which base to use
    base = options.secure ? 'https://secure.gravatar.com/avatar/' : 'http://www.gravatar.com/avatar/';

    //add the params
    if (options.rating) {params.push("r=" + options.rating)};
    if (options.backup) {params.push("d=" + encodeURIComponent(options.backup))};
    if (options.size) {params.push("s=" + options.size)};

    //now throw it all together
    return base + md5(email) + "?" + params.join("&");
  }
}

