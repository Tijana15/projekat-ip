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
import { AdminClientsManagementComponent } from '../admin/admin-clients-management/admin-clients-management.component';
import { AddVehicleBreakdownComponent } from '../operator/add-vehicle-breakdown/add-vehicle-breakdown.component';
import { RentalsOverviewComponent } from '../operator/rentals-overview/rentals-overview.component';
@Component({
  selector: 'app-operator-dashboard',
  imports: [
    CommonModule,
    MatCardModule,
    MatSidenavModule,
    MatToolbarModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    AdminClientsManagementComponent,
    AddVehicleBreakdownComponent,
    CdkTableModule,
    MatTableModule,
    RentalsOverviewComponent
  ],
  templateUrl: './operator-dashboard.component.html',
  styleUrl: './operator-dashboard.component.css',
})
export class OperatorDashboardComponent {
  selectedSection: string = 'rentals';
  constructor(private router: Router) {}

  selectSection(sectionId: string): void {
    this.selectedSection = sectionId;
  }
  logout(): void {
    this.router.navigate(['/login']);
  }
}
