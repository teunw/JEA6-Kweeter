import {Component, OnInit} from '@angular/core';
import {SearchResult, SearchService} from '../../services/search/search.service';
import {IProfile} from '../../profile';
import {IKweet} from '../../kweet';

@Component({
  selector: 'app-searchbar',
  templateUrl: './searchbar.component.html',
  styleUrls: ['./searchbar.component.css']
})
export class SearchbarComponent implements OnInit {

  public searchQuery: string;
  public currentTimeout:number = null;

  constructor(public searchService: SearchService) {
  }

  ngOnInit() {
  }

  refreshSearchCountdown() {
    if (this.currentTimeout != null) {
      clearTimeout(this.currentTimeout);
    }
    this.currentTimeout = setTimeout(() => {
      this.commitSearch()
    }, 500);

  }

  public commitSearch() {
    console.log(`Searching ${this.searchQuery}`);
    this.searchService.getSearchResultsForQuery(this.searchQuery);
  }

  public getSearchResultLink(searchResults: SearchResult) {
    if (searchResults._type === 'kweet') {
      return `kweets/${(searchResults._source as IKweet).publicId}`;
    } else if (searchResults._type === 'profile') {
      return `profiles/${(searchResults._source as IProfile).id}`;
    }
  }

}
