import {Component, OnInit} from '@angular/core';
import {IProfile} from "../../../profile";
import {ProfileService} from "../../../services/profile.service";
import {ActivatedRoute} from "@angular/router";
import {LoginService} from "../../../services/login.service";

@Component({
  selector: 'app-manage-account',
  templateUrl: './manage-account.component.html',
  styleUrls: ['./manage-account.component.css']
})
export class ManageAccountComponent implements OnInit {

  public isSavingChanges = false;
  public profile: IProfile;

  constructor(private profileService: ProfileService, private loginService:LoginService) {
  }

  ngOnInit() {
    this.isSavingChanges = true;
    const profileId = this.loginService.getLoginInfo().id;
    this.profileService
      .getProfile(profileId)
      .subscribe(data => {
        this.profile = data as IProfile;
        this.isSavingChanges = false;
      });
  }

}
