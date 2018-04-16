import {Component, OnInit} from '@angular/core';
import {IProfile} from '../../../classes/profile';
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

  async ngOnInit() {
    this.isSavingChanges = true;
    this.loginService.loginInfo
      .subscribe(async (loginInfo) => {
        if (loginInfo == null) {
          return;
        }
        this.profile = <IProfile>(await this.profileService.getProfile(loginInfo.id));
      });
  }

  public async saveChanges() {
    this.isSavingChanges = true;
    await this.profileService.updateProfile(this.profile);
    M.toast({
      html: `Saved changes!`,
      inDuration: 4000
    });
    this.router.navigate(['/']);
  }
}
