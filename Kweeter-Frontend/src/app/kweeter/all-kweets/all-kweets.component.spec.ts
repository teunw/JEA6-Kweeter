import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {AllKweetsComponent} from './all-kweets.component';

describe('AllKweetsComponent', () => {
  let component: AllKweetsComponent;
  let fixture: ComponentFixture<AllKweetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AllKweetsComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AllKweetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
