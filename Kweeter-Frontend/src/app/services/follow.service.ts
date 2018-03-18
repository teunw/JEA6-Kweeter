import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {IProfile} from '../profile';

@Injectable()
export class FollowService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  public startFollowing(profileToFollow: IProfile) {
    return this.httpClient
      .post<IProfile>(`${this.configService.getKweeterEndpoint()}/follow/${profileToFollow.id}`, {})
      .subscribe((profile: IProfile) => {

      });
  }

}
