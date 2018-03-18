import {Component, OnInit} from '@angular/core';
import {IProfile} from '../../../profile';
import {ProfileService} from '../../../services/profile.service';
import {Router} from '@angular/router';
import {LoginService} from '../../../services/login.service';
import 'materialize-css';

@Component({
  selector: 'app-manage-account',
  templateUrl: './manage-account.component.html',
  styleUrls: ['./manage-account.component.css']
})
export class ManageAccountComponent implements OnInit {

  public isSavingChanges = false;
  public profile: IProfile;

  constructor(private profileService: ProfileService, private loginService: LoginService, private router: Router) {
  }

  ngOnInit() {
    this.isSavingChanges = true;
    this.loginService.loginInfo
      .subscribe(loginInfo => {
        if (loginInfo == null) return;
        this.profileService
          .getProfile(loginInfo.id)
          .subscribe(data => {
            this.profile = data as IProfile;
            this.isSavingChanges = false;
          });
      });
  }

  public saveChanges() {
    this.isSavingChanges = true;
    this.profileService.updateProfile(this.profile)
      .subscribe(data => {
        M.toast({
          html: `Saved changes!`,
          inDuration: 4000
        });
        this.router.navigate(['/']);
      });
  }

}
