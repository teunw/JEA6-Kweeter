import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Kweet} from '../../kweet';
import {KweetService} from '../../services/kweet.service';

@Component({
  selector: 'app-view-kweets',
  templateUrl: './view-kweets.component.html',
  styleUrls: ['./view-kweets.component.css']
})
export class ViewKweetsComponent implements OnInit {

  public kweets: Observable<Kweet[]>;

  constructor(private kweetService: KweetService) {
  }

  ngOnInit() {
    this.kweets = this.kweetService.getKweets();
  }

}
