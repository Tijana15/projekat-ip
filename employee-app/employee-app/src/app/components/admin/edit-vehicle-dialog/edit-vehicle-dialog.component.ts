import { Component, OnInit, Inject } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogModule,
} from '@angular/material/dialog';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatToolbarModule } from '@angular/material/toolbar';
import { Observable } from 'rxjs';

import {
  MatDialogTitle,
  MatDialogContent,
  MatDialogActions,
} from '@angular/material/dialog';

import {
  Car,
  EBike,
  EScooter,
  Manufacturer,
} from '../../../model/vehicle.model';

import { VehicleService } from '../../../service/vehicle.service';

export interface EditVehicleDialogData {
  id: string;
  type: 'car' | 'e-scooter' | 'e-bike';
}

@Component({
  selector: 'app-edit-vehicle-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatToolbarModule,
  ],
  templateUrl: './edit-vehicle-dialog.component.html',
  styleUrl: './edit-vehicle-dialog.component.css',
})
export class EditVehicleDialogComponent implements OnInit {
  vehicleForm!: FormGroup;
  vehicleType: 'car' | 'e-scooter' | 'e-bike';
  originalVehicleId: string;

  manufacturers$!: Observable<Manufacturer[]>;
  isLoading: boolean = true;
  errorMessage: string = '';

  constructor(
    public dialogRef: MatDialogRef<EditVehicleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: EditVehicleDialogData,
    private fb: FormBuilder,
    private vehicleService: VehicleService
  ) {
    this.vehicleType = data.type;
    this.originalVehicleId = data.id;

    this.vehicleForm = this.fb.group({
      id: ['', Validators.required],
      manufacturer: ['', Validators.required],
      model: ['', Validators.required],
      purchasePrice: ['', [Validators.required, Validators.min(0)]],
      picture: [''],
      vehicleState: ['', Validators.required],
    });

    if (this.vehicleType === 'car') {
      this.vehicleForm.addControl(
        'purchaseDate',
        this.fb.control('', Validators.required)
      );
      this.vehicleForm.addControl(
        'description',
        this.fb.control('', Validators.required)
      );
    } else if (this.vehicleType === 'e-scooter') {
      this.vehicleForm.addControl(
        'maxSpeed',
        this.fb.control('', [Validators.required, Validators.min(1)])
      );
    } else if (this.vehicleType === 'e-bike') {
      this.vehicleForm.addControl(
        'maxRange',
        this.fb.control('', [Validators.required, Validators.min(1)])
      );
    }
  }

  ngOnInit(): void {
    this.getManufacturers();
    this.loadVehicleData();
  }

  getManufacturers(): void {
    this.manufacturers$ = this.vehicleService.getManufacturers();
  }

  loadVehicleData(): void {
    this.isLoading = true;
    this.errorMessage = '';

    let fetchObservable: Observable<Car | EScooter | EBike>;

    if (this.vehicleType === 'car') {
      fetchObservable = this.vehicleService.getCarById(this.originalVehicleId);
    } else if (this.vehicleType === 'e-scooter') {
      fetchObservable = this.vehicleService.getEScooterById(
        this.originalVehicleId
      );
    } else if (this.vehicleType === 'e-bike') {
      fetchObservable = this.vehicleService.getEBikeById(
        this.originalVehicleId
      );
    } else {
      this.errorMessage = 'Unknown vehicle type for data loading.';
      this.isLoading = false;
      return;
    }

    fetchObservable.subscribe({
      next: (vehicle) => {
        if (vehicle) {
          this.populateForm(vehicle);
          this.isLoading = false;
        } else {
          this.errorMessage = 'Vehicle data not found.';
          this.isLoading = false;
        }
      },
      error: (err) => {
        this.errorMessage = 'Failed to load vehicle data. Please try again.';
        this.isLoading = false;
        console.error('Error loading vehicle:', err);
      },
    });
  }

  populateForm(vehicle: Car | EScooter | EBike): void {
    this.vehicleForm.patchValue({
      id: vehicle.id,
      manufacturer: vehicle.manufacturer?.id,
      model: vehicle.model,
      purchasePrice: vehicle.purchasePrice,
      picture: vehicle.picture,
    });

    if (this.vehicleType === 'car') {
      const car = vehicle as Car;

      if (car.purchaseDate) {
        this.vehicleForm.patchValue({
          purchaseDate: new Date(car.purchaseDate),
          description: car.description,
        });
      } else {
        this.vehicleForm.patchValue({ description: car.description });
      }
    } else if (this.vehicleType === 'e-scooter') {
      const scooter = vehicle as EScooter;
      this.vehicleForm.patchValue({
        maxSpeed: scooter.maxSpeed,
      });
    } else if (this.vehicleType === 'e-bike') {
      const bike = vehicle as EBike;
      this.vehicleForm.patchValue({
        maxRange: bike.maxRange,
      });
    }
  }

  onSave(): void {
    if (this.vehicleForm.valid) {
      const formValue = this.vehicleForm.getRawValue();

      const manufacturerId = formValue.manufacturer;
      formValue.manufacturer = { id: manufacturerId } as Manufacturer;
      if (
        this.vehicleType === 'car' &&
        formValue.purchaseDate instanceof Date
      ) {
        formValue.purchaseDate = formValue.purchaseDate.toISOString();
      }

      let vehicleToSave: Car | EScooter | EBike;

      const { id, ...restOfFormValue } = formValue;

      if (this.vehicleType === 'car') {
        vehicleToSave = restOfFormValue as Car;
      } else if (this.vehicleType === 'e-scooter') {
        vehicleToSave = restOfFormValue as EScooter;
      } else if (this.vehicleType === 'e-bike') {
        vehicleToSave = restOfFormValue as EBike;
      } else {
        console.error('Unknown vehicle type during save:', this.vehicleType);
        return;
      }

      console.log(
        'Final vehicle object for updating (excluding ID in body):',
        this.vehicleType,
        vehicleToSave
      );

      let updateObservable: Observable<Car | EBike | EScooter>;

      if (this.vehicleType === 'car') {
        updateObservable = this.vehicleService.updateCar(
          this.originalVehicleId,
          vehicleToSave as Car
        );
      } else if (this.vehicleType === 'e-scooter') {
        updateObservable = this.vehicleService.updateEScooter(
          this.originalVehicleId,
          vehicleToSave as EScooter
        );
      } else if (this.vehicleType === 'e-bike') {
        updateObservable = this.vehicleService.updateEBike(
          this.originalVehicleId,
          vehicleToSave as EBike
        );
      } else {
        console.error('Unknown vehicle type during update:', this.vehicleType);
        return;
      }

      updateObservable.subscribe({
        next: (response) => {
          console.log('Vehicle updated successfully:', response);
          this.dialogRef.close(true);
        },
        error: (err) => {
          console.error('Error updating vehicle:', err);
          this.errorMessage = 'Failed to update vehicle. Please check console.';
        },
      });
    } else {
      this.vehicleForm.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields.';
      console.warn('Form is invalid:', this.vehicleForm.errors);
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
