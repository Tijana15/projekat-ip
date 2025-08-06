import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

import { Car, EBike, EScooter, Vehicle } from '../../../model/vehicle.model';
import { VehicleService } from '../../../service/vehicle.service';
import { Observable } from 'rxjs';
import {
  AddVehicleDialogComponent,
  AddVehicleDialogData,
} from '../add-vehicle-dialog/add-vehicle-dialog.component';
import {
  EditVehicleDialogComponent,
  EditVehicleDialogData,
} from '../edit-vehicle-dialog/edit-vehicle-dialog.component';

import { Router } from '@angular/router';
import {
  VehicleDetailsComponent,
  VehicleDetailsDialogData,
} from '../vehicle-details/vehicle-details.component';

@Component({
  selector: 'app-admin-vehicles-managemant',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatTabsModule,
    MatTableModule,
    MatIconModule,
    MatButtonModule,
    MatSortModule,
    MatDialogModule,
    MatPaginatorModule,
  ],
  templateUrl: './admin-vehicles-managemant.component.html',
  styleUrl: './admin-vehicles-managemant.component.css',
})
export class AdminVehiclesManagemantComponent implements OnInit {
  dialog: MatDialog;
  vehicleService: VehicleService;
  constructor(
    vehicleService: VehicleService,
    dialog: MatDialog,
    private router: Router
  ) {
    this.vehicleService = vehicleService;
    this.dialog = dialog;
  }
  carDataSource!: Observable<Car[]>;
  eBikeDataSource!: Observable<EBike[]>;
  eScooterDataSource!: Observable<EScooter[]>;

  displayedCarColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'purchasePrice',
    'purchaseDate',
    'description',
    'actions',
  ];

  displayedEBikeColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'purchasePrice',
    'maxRange',
    'actions',
  ];

  displayedEScooterColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'purchasePrice',
    'maxSpeed',
    'actions',
  ];

  ngOnInit(): void {
    this.loadVehicles();
  }

  loadVehicles(): void {
    this.carDataSource = this.vehicleService.getCars();
    this.eBikeDataSource = this.vehicleService.getEBikes();
    this.eScooterDataSource = this.vehicleService.getEScooters();
  }

  editVehicle(vehicle: Vehicle, type: 'car' | 'e-scooter' | 'e-bike'): void {
    console.log(`Edit ${type}:`, vehicle);
    this.openEditVehicleDialog(vehicle.id, type);
  }

  openEditVehicleDialog(
    id: string,
    type: 'car' | 'e-scooter' | 'e-bike'
  ): void {
    console.log('Opening edit dialog for ID:', id, 'Type:', type);

    const dialogRef = this.dialog.open(EditVehicleDialogComponent, {
      width: '600px',
      disableClose: true,
      data: { id: id, type: type } as EditVehicleDialogData,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        console.log('Vehicle edited successfully.');
        this.loadVehicles();
      } else {
        console.log('Edit vehicle dialog closed without saving.');
      }
    });
  }

  deleteVehicle(id: string, type: 'car' | 'e-scooter' | 'e-bike'): void {
    console.log('Attempting to delete vehicle with ID:', id, 'Type:', type);

    if (confirm(`Are you sure you want to delete ${type} with ID: ${id}?`)) {
      if (type === 'car') {
        this.vehicleService.deleteCar(id).subscribe({
          next: () => {
            console.log('Car deleted successfully.');
            this.loadVehicles();
          },
          error: (err) => {
            console.error('Error deleting car:', err);
          },
        });
      } else if (type === 'e-bike') {
        this.vehicleService.deleteEBike(id).subscribe({
          next: () => {
            console.log('E-Bike deleted successfully.');
            this.loadVehicles();
          },
          error: (err) => {
            console.error('Error deleting e-bike:', err);
          },
        });
      } else if (type === 'e-scooter') {
        this.vehicleService.deleteEScooter(id).subscribe({
          next: () => {
            console.log('E-Scooter deleted successfully.');
            this.loadVehicles();
          },
          error: (err) => {
            console.error('Error deleting e-scooter:', err);
          },
        });
      }
    }
  }

  addVehicle(type: 'car' | 'e-scooter' | 'e-bike'): void {
    const dialogRef = this.dialog.open(AddVehicleDialogComponent, {
      width: '600px',
      data: { type: type } as AddVehicleDialogData,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        console.log('Dialog result (new vehicle data):', result);
        this.loadVehicles();
      } else {
        console.log('Dialog closed without saving.');
      }
    });
  }

  openVehicleDetailsDialog(
    id: string,
    type: 'car' | 'e-scooter' | 'e-bike'
  ): void {
    console.log('Opening details dialog for ID:', id, 'Type:', type);

    const dialogRef = this.dialog.open(VehicleDetailsComponent, {
      width: '95vw', // Povećana širina
      maxWidth: '1000px', // Maksimalna širina
      height: '90vh', // Visina
      maxHeight: '800px', // Maksimalna visina
      disableClose: false,
      data: { id: id, type: type } as VehicleDetailsDialogData,
      panelClass: 'vehicle-details-dialog', // Dodajte custom CSS klasu
    });
  }
}
