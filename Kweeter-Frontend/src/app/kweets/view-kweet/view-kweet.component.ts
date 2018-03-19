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
  public profile : IProfile;

  constructor(public loginService: LoginService, private kweetActionService: KweetActionService) {
  }

  public getKweet() {
    return new Kweet(this.ikweet);
  }


  ngOnInit() {
    this.loginService
      .loginInfo
      .subscribe(data => {
        this.profile = data;
      })
  }

  public toggleLike() {
    const liked = this.getKweet().toggleLike(this.profile);
    if (liked) {
      this.kweetActionService.likeKweet(this.getKweet());
    } else {
      this.kweetActionService.unlikeKweet(this.getKweet());
    }
  }

}
