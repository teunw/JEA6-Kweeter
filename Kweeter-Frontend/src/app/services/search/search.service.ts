import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../config.service';
import {Explanation, SearchResponse} from 'elasticsearch';
import {IProfile} from '../../profile';
import {IKweet} from '../../kweet';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';
import {Observable} from 'rxjs/Observable';

export interface SearchResult {
  _index: string;
  _type: string;
  _id: string;
  _score: IProfile | IKweet;
  _source: IProfile | IKweet;
  _version?: number;
  _explanation?: Explanation;
  fields?: any;
  highlight?: any;
  inner_hits?: any;
  sort?: string[];
}

export interface SearchResults extends Array<SearchResult> {
}

@Injectable()
export class SearchService {

  private _lastSearchResult = new BehaviorSubject([]);
  public lastSearchResult: Observable<SearchResults> = this._lastSearchResult.asObservable();
  public hasSearchResult = false;

  constructor(private httpClient: HttpClient, private configService: ConfigService) {
  }

  public clearResults() {
    this._lastSearchResult.next([]);
  }

  public getSearchResultsForQuery(query: string) {
    const search = {
      'query': {
        'query_string': {
          'query': query
        }
      }
    };
    this.httpClient
      .post(`${this.configService.getSearchEndpoint()}_search`, search)
      .subscribe(data => {
        const res = data as SearchResponse<IKweet[] | IProfile[]>;
        this.hasSearchResult = true;
        this._lastSearchResult.next(res.hits.hits);
      });
  }

}
