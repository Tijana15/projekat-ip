import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormsModule,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

import { VehicleService } from '../../../service/vehicle.service';
import { BreakdownService } from '../../../service/breakdown.service';
import { Vehicle } from '../../../model/vehicle.model';


export interface Breakdown {
  id: number;
  breakdownDateTime: string;
  description: string;
  vehicleId: string;
}

@Component({
  selector: 'app-add-vehicle-breakdown',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule, // Dodano za ngModel
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatIconModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './add-vehicle-breakdown.component.html',
  styleUrl: './add-vehicle-breakdown.component.css',
})
export class AddVehicleBreakdownComponent implements OnInit {
  breakdownForm: FormGroup;
  vehicles: Vehicle[] = [];
  isLoading = false;
  isSubmitting = false;
  newBreakdownDateTime: string = '';

  constructor(
    private fb: FormBuilder,
    private vehicleService: VehicleService,
    private breakdownService: BreakdownService,
    private snackBar: MatSnackBar
  ) {
    this.breakdownForm = this.fb.group({
      vehicleId: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.minLength(10)]],
    });
  }

  ngOnInit(): void {
    this.loadVehicles();
    this.setDefaultDateTime();
  }

  setDefaultDateTime(): void {
    const now = new Date();
    // Format za datetime-local input (YYYY-MM-DDTHH:MM)
    const year = now.getFullYear();
    const month = (now.getMonth() + 1).toString().padStart(2, '0');
    const day = now.getDate().toString().padStart(2, '0');
    const hours = now.getHours().toString().padStart(2, '0');
    const minutes = now.getMinutes().toString().padStart(2, '0');

    this.newBreakdownDateTime = `${year}-${month}-${day}T${hours}:${minutes}`;
  }

  loadVehicles(): void {
    this.isLoading = true;
    this.vehicleService.getAllVehicles().subscribe({
      next: (vehicles) => {
        this.vehicles = vehicles;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading vehicles:', error);
        this.snackBar.open(
          'Failed to load vehicles. Please try again.',
          'Close',
          {
            duration: 5000,
            panelClass: ['error-snackbar'],
          }
        );
        this.isLoading = false;
      },
    });
  }

  onSubmit(): void {
    if (
      this.breakdownForm.valid &&
      this.newBreakdownDateTime &&
      !this.isSubmitting
    ) {
      this.isSubmitting = true;

      const formValue = this.breakdownForm.value;

      const dateTime = new Date(this.newBreakdownDateTime);

      const breakdownRequest: Breakdown = {
        id: 0,
        vehicleId: formValue.vehicleId,
        description: formValue.description,
        breakdownDateTime: dateTime.toISOString(),
      };

      this.breakdownService.createBreakdown(breakdownRequest).subscribe({
        next: (response) => {
          this.snackBar.open('Breakdown reported successfully!', 'Close', {
            duration: 3000,
            panelClass: ['success-snackbar'],
          });
          this.resetForm();
          this.isSubmitting = false;
        },
        error: (error) => {
          console.error('Error creating breakdown:', error);
          this.snackBar.open(
            'Failed to report breakdown. Please try again.',
            'Close',
            {
              duration: 5000,
              panelClass: ['error-snackbar'],
            }
          );
          this.isSubmitting = false;
        },
      });
    } else {
      this.markFormGroupTouched();
      if (!this.newBreakdownDateTime) {
        this.snackBar.open('Please select date and time', 'Close', {
          duration: 3000,
          panelClass: ['error-snackbar'],
        });
      }
    }
  }

  resetForm(): void {
    this.breakdownForm.reset();
    this.setDefaultDateTime();
  }

  private markFormGroupTouched(): void {
    Object.keys(this.breakdownForm.controls).forEach((key) => {
      const control = this.breakdownForm.get(key);
      control?.markAsTouched();
    });
  }

  getVehicleDisplayName(vehicle: Vehicle): string {
    return `${vehicle.id} - ${vehicle.manufacturer?.name || 'N/A'} ${
      vehicle.model
    }`;
  }

  get vehicleId() {
    return this.breakdownForm.get('vehicleId');
  }
  get description() {
    return this.breakdownForm.get('description');
  }
}
