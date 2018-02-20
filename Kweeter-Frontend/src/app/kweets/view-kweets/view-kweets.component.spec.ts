import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewKweetsComponent } from './view-kweets.component';

describe('ViewKweetsComponent', () => {
  let component: ViewKweetsComponent;
  let fixture: ComponentFixture<ViewKweetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewKweetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewKweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
