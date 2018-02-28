import {Component, Input, OnInit} from '@angular/core';
import {IKweet, Kweet} from '../../kweet';
import {LoginService} from "../../services/login.service";
import {KweetActionService} from "../../services/kweetaction.service";
import {IProfile} from "../../profile";

@Component({
  selector: 'app-view-kweet',
  templateUrl: './view-kweet.component.html',
  styleUrls: ['./view-kweet.component.css']
})
export class ViewKweetComponent implements OnInit {

  @Input()
  private ikweet: IKweet;

  public kweet: Kweet;
  public profile : IProfile;

  constructor(private loginService: LoginService, private kweetActionService: KweetActionService) {
  }

  ngOnInit() {
    this.kweet = new Kweet(this.ikweet)
    this.loginService
      .loginInfo
      .subscribe(data => {
        this.profile = data;
      })
  }

  public toggleLike() {
    this.kweet.toggleLike(this.profile);
    this.kweetActionService.likeKweet(this.kweet, this.profile);
  }

}
