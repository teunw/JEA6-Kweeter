import {Component, OnInit} from '@angular/core';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';

@Component({
  selector: 'app-all-kweets',
  templateUrl: './all-kweets.component.html',
  styleUrls: ['./all-kweets.component.css']
})
export class AllKweetsComponent implements OnInit {

  private kweets: IKweet[];

  constructor(private kweetService: KweetService) {
  }

  ngOnInit() {
    this.kweetService.getKweets()
      .subscribe(data => {
        this.kweets = data;
      });
  }

}
