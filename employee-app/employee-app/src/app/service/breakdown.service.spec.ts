import { TestBed } from '@angular/core/testing';

import { BreakdownService } from './breakdown.service';

describe('BreakdownService', () => {
  let service: BreakdownService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BreakdownService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
