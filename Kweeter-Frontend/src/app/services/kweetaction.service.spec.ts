import { TestBed, inject } from '@angular/core/testing';

import { KweetActionService } from './kweetaction.service';

describe('KweetActionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KweetActionService]
    });
  });

  it('should be created', inject([KweetActionService], (service: KweetActionService) => {
    expect(service).toBeTruthy();
  }));
});
