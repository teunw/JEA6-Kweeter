import {Injectable} from '@angular/core';

@Injectable()
export class ConfigService {

  constructor() { }

  getKweeterEndpoint(): string {
    return 'http://localhost:8080/Kweeter-Backend/api';
  }

  getSocketEndpoint():string {
    return 'ws://localhost:8080/Kweeter-Backend/'
  }

  getSearchEndpoint(): string {
    return 'https://elasticsearch.teunwillems.nl/'
  }

}
