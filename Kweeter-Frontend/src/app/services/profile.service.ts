import {Injectable} from '@angular/core';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {IProfile} from '../profile';

@Injectable()
export class ProfileService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  getProfiles() {
    return this.httpClient.get<IProfile[]>(`${this.configService.getKweeterEndpoint()}/profiles`);
  }

  getProfile(id: number) {
    return this.httpClient.get<IProfile>(`${this.configService.getKweeterEndpoint()}/profiles/${id}`);
  }
}
