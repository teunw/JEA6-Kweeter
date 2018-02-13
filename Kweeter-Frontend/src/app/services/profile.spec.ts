import { TestBed, inject } from '@angular/core/testing';

import { ProfileService } from './profile.service.ts.service';

describe('ProfileService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProfileService.TsService]
    });
  });

  it('should be created', inject([ProfileService.TsService], (service: ProfileService.TsService) => {
    expect(service).toBeTruthy();
  }));
});
