import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RentalPriceConfigComponent } from './rental-price-config.component';

describe('RentalPriceConfigComponent', () => {
  let component: RentalPriceConfigComponent;
  let fixture: ComponentFixture<RentalPriceConfigComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RentalPriceConfigComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RentalPriceConfigComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
