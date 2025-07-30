import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminVehiclesManagemantComponent } from './admin-vehicles-managemant.component';

describe('AdminVehiclesManagemantComponent', () => {
  let component: AdminVehiclesManagemantComponent;
  let fixture: ComponentFixture<AdminVehiclesManagemantComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminVehiclesManagemantComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminVehiclesManagemantComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
