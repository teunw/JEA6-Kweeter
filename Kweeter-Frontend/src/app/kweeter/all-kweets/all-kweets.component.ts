import {Component, OnInit} from '@angular/core';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-all-kweets',
  templateUrl: './all-kweets.component.html',
  styleUrls: ['./all-kweets.component.css']
})
export class AllKweetsComponent implements OnInit {

  private kweets: IKweet[];

  constructor(private kweetService: KweetService, private loginService: LoginService) {
  }

  ngOnInit() {
    this.kweetService.kweets
      .subscribe(kweetsList => {
        this.kweets = kweetsList;
      });
    this.kweetService.getKweets();
  }

}
