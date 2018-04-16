import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from './config.service';
import {IProfile} from '../classes/profile';
import {IProfileFollower} from '../IProfileFollower';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class FollowService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  public startFollowing(profileToFollow: IProfile): Observable<IProfileFollower> {
    return this.httpClient
      .post<IProfileFollower>(`${this.configService.getKweeterEndpoint()}/followers/follower/${profileToFollow.id}`, {});
  }

  public stopFollowing(profileToStopFollow: IProfile): Observable<IProfileFollower> {
    return this.httpClient
      .delete<IProfileFollower>(`${this.configService.getKweeterEndpoint()}/followers/follower/${profileToStopFollow.id}`, {});
  }

  public getFollowers(profileId: number = -1): Observable<IProfileFollower> {
    if (profileId > 0) {
      return this.httpClient.get<IProfileFollower>(`${this.configService.getKweeterEndpoint()}/followers/${profileId}`);
    }
    return this.httpClient.get<IProfileFollower>(`${this.configService.getKweeterEndpoint()}/followers`);
  }
}
