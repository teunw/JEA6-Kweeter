import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../config.service';
import {IProfile, Profile} from '../../classes/profile';
import {IKweet, Kweet} from '../../classes/kweet';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';
import {ProfileService} from "../profile.service";

export class SearchResult {

  public linkTo: string;
  public asString: string;

  constructor(linkTo: string, asString: string) {
    this.linkTo = linkTo;
    this.asString = asString;
  }
}

@Injectable()
export class SearchService {

  private _lastSearchResult = new BehaviorSubject([]);
  public lastSearchResult: Observable<SearchResult[]> = this._lastSearchResult.asObservable();
  public hasSearchResult = false;

  constructor(private httpClient: HttpClient, private configService: ConfigService, private profileService: ProfileService) {
  }

  public clearResults() {
    this._lastSearchResult.next([]);
    this.hasSearchResult = false;
  }

  public async getSearchResultsForQuery(query: string) {
    const result = (await this.httpClient
      .get(`${this.configService.getKweeterEndpoint()}/search/all/${query}`)
      .toPromise()) as (IKweet | IProfile)[];
    const searchResults = result.map(async (value) => {
      const isKweet = value.hasOwnProperty('textContent');
      if (isKweet) {
        const kweet = new Kweet(value as IKweet, this.profileService);
        const author = await kweet.getAuthor();
        return new SearchResult(
          `kweets/${kweet.serverData.publicId}`,
          `Kweet by @${author.username}: ${kweet.serverData.textContent}`
        );
      } else {
        const profile = new Profile(value as IProfile);
        return new SearchResult(`profiles/${profile.serverData.id}`, `@${profile.serverData.id}'s profile: ${profile.serverData.bio}`);
      }
    });
    this._lastSearchResult.next(searchResults);
    this.hasSearchResult = true;
  }
}
