import {Injectable} from '@angular/core';

@Injectable()
export class ConfigService {

  constructor() { }

  getKweeterEndpoint(): string {
    return 'http://localhost:8080/Kweeter-Backend/api';
  }

  getSearchEndpoint(): string {
    return 'https://elasticsearch.teunwillems.nl/'
  }

}
