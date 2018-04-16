import {Injectable} from '@angular/core';
import {ConfigService} from './config.service';
import {HttpClient} from '@angular/common/http';
import {IProfile} from '../classes/profile';

@Injectable()
export class ProfileService {

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  async getProfiles() {
    return await this.httpClient.get<IProfile[]>(`${this.configService.getKweeterEndpoint()}/profiles`).toPromise();
  }

  async getProfileByEmail(email: string) {
    return await this.httpClient.get<IProfile>(`${this.configService.getKweeterEndpoint()}/profiles/by-email/${email}`).toPromise();
  }

  async getProfile(id: number) {
    return await this.httpClient.get<IProfile>(`${this.configService.getKweeterEndpoint()}/profiles/${id}`).toPromise();
  }

  async getProfileByUrl(url: string) {
    return await this.httpClient.get<IProfile>(url).toPromise();
  }

  async createProfile(profile: IProfile) {
    return await this.httpClient.post<IProfile>(`${this.configService.getKweeterEndpoint()}/profiles`, profile).toPromise();
  }

  async updateProfile(profile: IProfile) {
    return await this.httpClient.put<IProfile>(`${this.configService.getKweeterEndpoint()}/profiles`, profile).toPromise();
  }
}
