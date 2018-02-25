import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ViewKweetComponent} from './view-kweet.component';

describe('ViewKweetComponent', () => {
  let component: ViewKweetComponent;
  let fixture: ComponentFixture<ViewKweetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ViewKweetComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewKweetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
