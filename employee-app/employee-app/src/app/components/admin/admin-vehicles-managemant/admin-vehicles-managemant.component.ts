import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTableModule } from '@angular/material/table';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

import { Car, EBike, EScooter, Vehicle } from '../../../model/vehicle.model';
import { VehicleService } from '../../../service/vehicle.service';
import { Observable } from 'rxjs';
import { AddVehicleDialogComponent } from '../add-vehicle-dialog/add-vehicle-dialog.component';
import { AddVehicleDialogData } from '../add-vehicle-dialog/add-vehicle-dialog.component';
import { Router } from '@angular/router';

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
    MatPaginator,
  ],
  templateUrl: './admin-vehicles-managemant.component.html',
  styleUrl: './admin-vehicles-managemant.component.css',
})
export class AdminVehiclesManagemantComponent {
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

  editVehicle(vehicle: Vehicle, type: string): void {
    console.log(`Edit ${type}:`, vehicle);
    this.router.navigate(['/admin/vehicles/edit', type, vehicle.id]);
  }

  deleteVehicle(id: string, type: 'car' | 'e-scooter' | 'e-bike'): void {
    console.log('Attempting to delete vehicle with ID:', id, 'Type:', type);

    // Važno: Koristi custom modal UI umesto confirm() za bolji UX
    // Za sada, zadržavamo confirm() za jednostavnost
    if (confirm(`Are you sure you want to delete ${type} with ID: ${id}?`)) {
      if (type === 'car') {
        this.vehicleService.deleteCar(id).subscribe({
          next: () => {
            console.log('Car deleted successfully.');
            this.loadVehicles(); // Ponovo učitaj listu nakon brisanja
          },
          error: (err) => {
            console.error('Error deleting car:', err);
            // Opcionalno: prikaži poruku o grešci korisniku
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
        if (type === 'car') {
          this.vehicleService.addCar(result as Car).subscribe({
            next: (newCar) => {
              console.log('Car added successfully:', newCar);
              this.loadVehicles();
            },
            error: (err) => console.error('Error adding car:', err),
          });
        } else if (type === 'e-bike') {
          this.vehicleService.addEBike(result as EBike).subscribe({
            next: (newBike) => {
              console.log('EBike added successfully:', newBike);
              this.loadVehicles();
            },
            error: (err) => console.error('Error adding ebike:', err),
          });
        } else if (type === 'e-scooter') {
          this.vehicleService.addEScooter(result as EScooter).subscribe({
            next: (newEScooter) => {
              console.log('Car added successfully:', newEScooter);
              this.loadVehicles();
            },
            error: (err) => console.error('Error adding escooter:'),
          });
        }
      } else {
        console.log('Dialog closed without saving.');
      }
    });
  }
}
