import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-load',
  templateUrl: './load.component.html',
  styleUrls: ['./load.component.scss']
})
export class LoadComponent implements OnInit {

  @Input()
  public loadSentences: string[];

  public currentSentence: string = "";

  constructor() {
  }

  ngOnInit() {
    const loadComp = this;
    if (this.hasValidSentences()) {
      setInterval(() => {
        loadComp.getRandomSentence();
      }, 2500);
    }
  }

  hasValidSentences(): boolean {
    return this.loadSentences !== undefined && this.loadSentences.length > 0;
  }

  getRandomSentence(): string {
    if (!this.hasValidSentences()) {
      return "";
    }
    this.currentSentence = this.loadSentences[Math.round(Math.random() * (this.loadSentences.length - 1))];
    return this.currentSentence;
  }
}
