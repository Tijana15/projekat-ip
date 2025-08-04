import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { Router } from '@angular/router';
import { CdkTableModule } from '@angular/cdk/table';
import { MatTableModule } from '@angular/material/table';

import { AdminVehiclesManagemantComponent } from '../admin/admin-vehicles-managemant/admin-vehicles-managemant.component';
import { AdminManufacturersManagementComponent } from '../admin/admin-manufacturers-management/admin-manufacturers-management.component';
import { AdminClientsManagementComponent } from '../admin/admin-clients-management/admin-clients-management.component';
import { AdminEmployeesManagementComponent } from '../admin/admin-employees-management/admin-employees-management.component';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    AdminManufacturersManagementComponent,
    AdminClientsManagementComponent,
    AdminEmployeesManagementComponent,
    AdminVehiclesManagemantComponent,
    CdkTableModule,
    MatTableModule,
  ],
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
})
export class AdminDashboardComponent {
  selectedSection: string = 'vehicles';
  constructor(private router: Router) {}

  selectSection(sectionId: string): void {
    this.selectedSection = sectionId;
  }
  logout(): void {
    this.router.navigate(['/login']);
  }
}
