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
import { VehicleMapComponent } from '../operator/vehicle-map/vehicle-map.component';
import { RentalsOverviewComponent } from '../operator/rentals-overview/rentals-overview.component';
import { AddVehicleBreakdownComponent } from '../operator/add-vehicle-breakdown/add-vehicle-breakdown.component';
import { RentalPriceConfigComponent } from '../management/rental-price-config/rental-price-config.component';
import { StatisticsComponent } from '../management/statistics/statistics.component';
@Component({
  selector: 'app-management-dashboard',
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
    VehicleMapComponent,
    RentalsOverviewComponent,
    AddVehicleBreakdownComponent,
    RentalPriceConfigComponent,
    StatisticsComponent,
  ],
  templateUrl: './management-dashboard.component.html',
  styleUrl: './management-dashboard.component.css',
})
export class ManagementDashboardComponent {
  selectedSection: string = 'vehicles';
  constructor(private router: Router) {}

  selectSection(sectionId: string): void {
    this.selectedSection = sectionId;
  }
  logout(): void {
    this.router.navigate(['/login']);
  }
}
