import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpecifickweetComponent } from './specifickweet.component';

describe('SpecifickweetComponent', () => {
  let component: SpecifickweetComponent;
  let fixture: ComponentFixture<SpecifickweetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpecifickweetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpecifickweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
