import {Component, OnInit} from '@angular/core';
import {KweetService} from '../../services/kweet.service';
import {IKweet, IKweetPost} from '../../kweet';
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  public kweet: IKweetPost | IKweet | any = {};

  constructor(public loginService: LoginService, private kweetService: KweetService) {
  }

  ngOnInit() {
    this.kweet.author = this.loginService.getLoginInfo();
  }

  postKweet() {
    this.kweetService.createKweet(this.kweet);
    this.kweetService.liveUpdateService.postKweetUpdates(this.kweet);
  }

  postKweetEnter(e: KeyboardEvent) {
    // 13 == enter
    if (e.keyCode == 13) {
      this.postKweet();
    }
  }
}
