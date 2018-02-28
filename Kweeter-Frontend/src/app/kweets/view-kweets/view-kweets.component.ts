import {Component, Input, OnInit} from '@angular/core';
import {IKweet} from '../../kweet';
import {KweetService} from '../../services/kweet.service';

@Component({
  selector: 'app-view-kweets',
  templateUrl: './view-kweets.component.html',
  styleUrls: ['./view-kweets.component.css']
})
export class ViewKweetsComponent implements OnInit {

  @Input()
  public kweets: IKweet[];

  constructor(private kweetService: KweetService) {
  }

  ngOnInit() {
  }
  reloadKweets() {
    this.kweetService.refreshKweets();
  }

}
