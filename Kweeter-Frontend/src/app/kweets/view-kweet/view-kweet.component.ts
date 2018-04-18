import {Component, Input, OnInit} from '@angular/core';
import {IKweet, Kweet} from '../../classes/kweet';
import {LoginService} from "../../services/login.service";
import {KweetActionService} from "../../services/kweetaction.service";
import {IProfile} from "../../classes/profile";
import {ProfileService} from "../../services/profile.service";

@Component({
  selector: 'app-view-kweet',
  templateUrl: './view-kweet.component.html',
  styleUrls: ['./view-kweet.component.css']
})
export class ViewKweetComponent implements OnInit {

  @Input()
  private ikweet: IKweet;

  public kweet: Kweet;
  public profile: IProfile;

  constructor(public loginService: LoginService, private kweetActionService: KweetActionService, private profileService: ProfileService) {

  }

  public getKweet() {
    return new Kweet(this.ikweet, this.profileService);
  }


  ngOnInit() {
    this.loginService
      .loginInfo
      .subscribe(data => {
        this.profile = data;
      });
    this.kweet = this.getKweet();
  }

  public toggleLike() {
    const liked = this.getKweet().toggleLike(this.profile);
    if (liked) {
      this.kweetActionService.likeKweet(this.getKweet().serverData);
    } else {
      this.kweetActionService.unlikeKweet(this.getKweet().serverData);
    }
  }

}
