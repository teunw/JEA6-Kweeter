import {Component, Input, OnInit} from '@angular/core';
import {IKweet} from '../../kweet';

@Component({
  selector: 'app-view-kweet',
  templateUrl: './view-kweet.component.html',
  styleUrls: ['./view-kweet.component.css']
})
export class ViewKweetComponent implements OnInit {

  @Input()
  public kweet: IKweet;

  constructor() {
  }

  ngOnInit() {
  }

}
