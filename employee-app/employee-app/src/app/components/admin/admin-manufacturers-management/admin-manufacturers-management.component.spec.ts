import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminManufacturersManagementComponent } from './admin-manufacturers-management.component';

describe('AdminManufacturersManagementComponent', () => {
  let component: AdminManufacturersManagementComponent;
  let fixture: ComponentFixture<AdminManufacturersManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminManufacturersManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminManufacturersManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
