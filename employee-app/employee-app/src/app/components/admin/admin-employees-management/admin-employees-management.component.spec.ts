import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEmployeesManagementComponent } from './admin-employees-management.component';

describe('AdminEmployeesManagementComponent', () => {
  let component: AdminEmployeesManagementComponent;
  let fixture: ComponentFixture<AdminEmployeesManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminEmployeesManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminEmployeesManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
