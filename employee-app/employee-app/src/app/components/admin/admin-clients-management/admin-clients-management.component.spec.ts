import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminClientsManagementComponent } from './admin-clients-management.component';

describe('AdminClientsManagementComponent', () => {
  let component: AdminClientsManagementComponent;
  let fixture: ComponentFixture<AdminClientsManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminClientsManagementComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminClientsManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
