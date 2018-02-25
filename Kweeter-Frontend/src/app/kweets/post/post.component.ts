import {Component, OnInit} from '@angular/core';
import {KweetService} from '../../services/kweet.service';
import {IKweet} from '../../kweet';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  public kweet: IKweet | any = {};

  constructor(private kweetService: KweetService) {
  }

  ngOnInit() {
  }

  postKweet() {
    this.kweetService.createKweet(this.kweet);
  }

}
