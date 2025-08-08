import { TestBed } from '@angular/core/testing';

import { RentalPriceConfigService } from './rental-price-config.service';

describe('RentalPriceConfigService', () => {
  let service: RentalPriceConfigService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RentalPriceConfigService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
