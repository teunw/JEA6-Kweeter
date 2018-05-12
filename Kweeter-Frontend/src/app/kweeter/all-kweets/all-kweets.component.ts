import {Component, OnInit} from '@angular/core';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';
import {LoginService} from "../../services/login.service";
import {LiveupdateService} from "../../services/liveupdate.service";

@Component({
  selector: 'app-all-kweets',
  templateUrl: './all-kweets.component.html',
  styleUrls: ['./all-kweets.component.css']
})
export class AllKweetsComponent implements OnInit {

  public kweets: IKweet[] = [];
  public liveKweets: IKweet[] = [];

  constructor(private kweetService: KweetService, public loginService: LoginService, private liveUpdate: LiveupdateService) {
  }

  ngOnInit() {
    this.kweetService.kweets
      .subscribe(kweetsList => {
        this.kweets = kweetsList;
      });
    this.liveUpdate.liveKweets.subscribe(data => this.liveKweets = data);
    this.kweetService.getKweets();
  }

  getAllKweets() {
    return this.kweets.concat(this.liveKweets);
  }

}
