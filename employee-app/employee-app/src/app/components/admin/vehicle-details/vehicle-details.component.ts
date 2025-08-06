import { Component, OnInit, Inject } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogModule,
} from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatTableModule } from '@angular/material/table';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {
  MatDatepicker,
  MatDatepickerInput,
} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';

import { Observable } from 'rxjs';

import {
  Car,
  EBike,
  EScooter,
  Manufacturer,
  Vehicle,
} from '../../../model/vehicle.model';
import { Breakdown } from '../../../model/breakdown.model';
import { BreakdownService } from '../../../service/breakdown.service';
import { VehicleService } from '../../../service/vehicle.service';
import { MatDivider } from '@angular/material/divider';
import { RentalService } from '../../../service/rental.service';
import { Rental, RentalSimple } from '../../../model/rental.model';

export interface VehicleDetailsDialogData {
  id: string;
  type: 'car' | 'e-scooter' | 'e-bike';
}

@Component({
  selector: 'app-vehicle-details',
  standalone: true,
  imports: [
    CommonModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
    MatTableModule,
    MatProgressSpinnerModule,
    MatFormFieldModule,
    MatInputModule,

    MatNativeDateModule,
    FormsModule,
    MatSnackBarModule,
    MatSortModule,
    MatDivider,
  ],
  templateUrl: './vehicle-details.component.html',
  styleUrl: './vehicle-details.component.css',
})
export class VehicleDetailsComponent implements OnInit {
  vehicle: Car | EScooter | EBike | null = null;
  vehicleType: 'car' | 'e-scooter' | 'e-bike';
  vehicleId: string;
  vehicleRentals: RentalSimple[] = [];
  isLoading: boolean = true;
  isLoadingBreakdowns: boolean = false;
  isLoadingRentals: boolean = false;
  errorMessage: string = '';
  vehicleBreakdowns: Breakdown[] = [];

  showAddBreakdownDialog: boolean = false;
  newBreakdownDescription: string = '';
  newBreakdownDateTime: Date = new Date();
  isAddingBreakdown: boolean = false;

  isDeletingBreakdown: boolean = false;

  displayedBrokenColumns: string[] = [
    'id',
    'breakdownDateTime',
    'description',
    'actions',
  ];

  displayedRentalColumns: string[] = [
    'id',
    'dateTime',
    'clientName',
    'durationSeconds',
    'price',
  ];

  constructor(
    public dialogRef: MatDialogRef<VehicleDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: VehicleDetailsDialogData,
    private vehicleService: VehicleService,
    private breakdownService: BreakdownService,
    private rentalService: RentalService,
    private snackBar: MatSnackBar
  ) {
    this.vehicleId = data.id;
    this.vehicleType = data.type;
  }

  ngOnInit(): void {
    this.loadVehicleDetails();
  }

  loadVehicleDetails(): void {
    this.isLoading = true;
    this.errorMessage = '';
    let fetchObservable: Observable<Car | EScooter | EBike>;

    if (this.vehicleType === 'car') {
      fetchObservable = this.vehicleService.getCarById(this.vehicleId);
    } else if (this.vehicleType === 'e-scooter') {
      fetchObservable = this.vehicleService.getEScooterById(this.vehicleId);
    } else if (this.vehicleType === 'e-bike') {
      fetchObservable = this.vehicleService.getEBikeById(this.vehicleId);
    } else {
      this.errorMessage = 'Unknown vehicle type.';
      this.isLoading = false;
      return;
    }

    fetchObservable.subscribe({
      next: (vehicleData) => {
        if (vehicleData) {
          this.vehicle = vehicleData;
          console.log('Vehicle details loaded:', this.vehicle);
          this.loadVehicleBreakdowns();
          this.loadVehicleRentals();
          this.isLoading = false;
        } else {
          this.errorMessage = 'Vehicle not found.';
          this.isLoading = false;
        }
      },
      error: (err) => {
        this.errorMessage = 'Failed to load vehicle details. Please try again.';
        this.isLoading = false;
        console.error('Error loading vehicle details:', err);
      },
    });
  }

  loadVehicleBreakdowns(): void {
    this.isLoadingBreakdowns = true;
    this.breakdownService.getBreakdownsByVehicleId(this.vehicleId).subscribe({
      next: (breakdowns) => {
        this.vehicleBreakdowns = breakdowns;
        this.isLoadingBreakdowns = false;
        console.log('Vehicle breakdowns loaded:', this.vehicleBreakdowns);
      },
      error: (err) => {
        this.isLoadingBreakdowns = false;
        console.error('Error loading vehicle breakdowns:', err);
        this.snackBar.open('Failed to load faults', 'Close', {
          duration: 3000,
          panelClass: ['error-snackbar'],
        });
      },
    });
  }

  loadVehicleRentals(): void {
    this.isLoadingRentals = true;
    this.rentalService.getRentalsByVehicleId(this.vehicleId).subscribe({
      next: (rentals) => {
        this.vehicleRentals = rentals;
        this.isLoadingRentals = false;
        console.log('Vehicle rentals loaded:', this.vehicleRentals);
      },
      error: (err) => {
        this.isLoadingRentals = false;
        console.error('Error loading vehicle rentals:', err);
        this.snackBar.open('Failed to load rentals', 'Close', {
          duration: 3000,
          panelClass: ['error-snackbar'],
        });
      },
    });
  }

  openAddBreakdownDialog(): void {
    this.showAddBreakdownDialog = true;
    this.newBreakdownDescription = '';
    this.newBreakdownDateTime = new Date();
  }

  closeAddBreakdownDialog(): void {
    if (!this.isAddingBreakdown) {
      this.showAddBreakdownDialog = false;
      this.newBreakdownDescription = '';
      this.newBreakdownDateTime = new Date();
    }
  }

  canAddBreakdown(): boolean {
    return (
      this.newBreakdownDescription.trim().length > 0 &&
      this.newBreakdownDateTime !== null
    );
  }

  addBreakdown(): void {
    if (!this.canAddBreakdown()) {
      return;
    }

    this.isAddingBreakdown = true;

    const newBreakdown: Breakdown = {
      id: 0, // Backend će generisati ID
      description: this.newBreakdownDescription.trim(),
      breakdownDateTime: this.newBreakdownDateTime.toISOString(),
      vehicleId: this.vehicleId,
    };

    this.breakdownService.createBreakdown(newBreakdown).subscribe({
      next: (createdBreakdown) => {
        this.vehicleBreakdowns = [...this.vehicleBreakdowns, createdBreakdown];
        this.isAddingBreakdown = false;
        this.closeAddBreakdownDialog();

        this.snackBar.open('Fault added successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar'],
        });

        console.log('Breakdown added:', createdBreakdown);
      },
      error: (err) => {
        this.isAddingBreakdown = false;
        console.error('Error adding breakdown:', err);

        this.snackBar.open('Failed to add fault. Please try again.', 'Close', {
          duration: 4000,
          panelClass: ['error-snackbar'],
        });
      },
    });
  }

  deleteBreakdown(breakdownId: number, description: string): void {
    const confirmMessage = `Are you sure you want to delete this breakdown?\n\n"${description}"`;

    if (!confirm(confirmMessage)) {
      return;
    }

    this.isDeletingBreakdown = true;

    this.breakdownService.deleteBreakdownById(breakdownId).subscribe({
      next: () => {
        this.vehicleBreakdowns = this.vehicleBreakdowns.filter(
          (breakdown) => breakdown.id !== breakdownId
        );

        this.isDeletingBreakdown = false;

        this.snackBar.open('Fault deleted successfully!', 'Close', {
          duration: 3000,
          panelClass: ['success-snackbar'],
        });

        console.log('Breakdown deleted:', breakdownId);
      },
      error: (err) => {
        this.isDeletingBreakdown = false;
        console.error('Error deleting breakdown:', err);

        this.snackBar.open(
          'Failed to delete fault. Please try again.',
          'Close',
          {
            duration: 4000,
            panelClass: ['error-snackbar'],
          }
        );
      },
    });
  }

  onClose(): void {
    this.dialogRef.close();
  }

  getCarData(): Car {
    return this.vehicle as Car;
  }

  getEScooterData(): EScooter {
    return this.vehicle as EScooter;
  }

  getEBikeData(): EBike {
    return this.vehicle as EBike;
  }
  formatDuration(durationSeconds: number): string {
    const hours = Math.floor(durationSeconds / 3600);
    const minutes = Math.floor((durationSeconds % 3600) / 60);
    const seconds = durationSeconds % 60;

    if (hours > 0) {
      return `${hours}h ${minutes}m ${seconds}s`;
    } else if (minutes > 0) {
      return `${minutes}m ${seconds}s`;
    } else {
      return `${seconds}s`;
    }
  }
}
