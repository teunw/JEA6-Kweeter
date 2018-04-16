import {Component, OnInit} from '@angular/core';
import {ProfileService} from '../../services/profile.service';
import {ActivatedRoute} from '@angular/router';
import {KweetService} from '../../services/kweet.service';
import {LoginService} from '../../services/login.service';
import {IKweet} from '../../classes/kweet';

@Component({
  selector: 'app-specifickweet',
  templateUrl: './specifickweet.component.html',
  styleUrls: ['./specifickweet.component.css']
})
export class SpecifickweetComponent implements OnInit {

  private kweet: IKweet;

  constructor(private profileService: ProfileService, private loginService: LoginService, private kweetService: KweetService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    console.log(this.route.params['id']);
    this.route.params.subscribe(param => {
      if (param.id !== undefined && param.id !== null) {
        this.kweetService.getKweet(param.id)
          .subscribe(kweetData => {
            this.kweet = kweetData;
          });
      }
    });
  }

}
