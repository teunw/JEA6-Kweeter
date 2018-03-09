import {Injectable} from '@angular/core';
import {ConfigService} from "./config.service";
import {IKweet} from "../kweet";
import {HttpClient} from "@angular/common/http";
import {IProfile} from "../profile";
import {KweetService} from "./kweet.service";

interface KweetLikeResponse {
  liked : boolean;
}

@Injectable()
export class KweetActionService {

  constructor(private configService: ConfigService, private httpClient: HttpClient, private kweetService:KweetService) {
  }

  public likeKweet(kweet: IKweet, profile:IProfile) {
    return this.httpClient.put(`${this.configService.getKweeterEndpoint()}/kweets/actions/${kweet.internalId}/like`, {})
      .subscribe(data => {});
  }

}
