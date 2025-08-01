import { Component, Inject, OnInit } from '@angular/core';
import { CommonModule, formatDate } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
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

export interface AddVehicleDialogData {
  type: 'car' | 'e-scooter' | 'e-bike';
}

@Component({
  selector: 'app-add-vehicle-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatIconModule,
    MatCardModule,
    MatToolbarModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
  ],
  templateUrl: './add-vehicle-dialog.component.html',
  styleUrl: './add-vehicle-dialog.component.css',
})
export class AddVehicleDialogComponent implements OnInit {
  showManualForm: boolean = true;
  vehicleForm!: FormGroup;
  vehicleType: 'car' | 'e-scooter' | 'e-bike';

  manufacturers$!: Observable<Manufacturer[]>;

  constructor(
    public dialogRef: MatDialogRef<AddVehicleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: AddVehicleDialogData,
    private fb: FormBuilder,
    private vehicleService: VehicleService
  ) {
    this.vehicleType = data.type;
  }

  ngOnInit(): void {
    this.initForm();
    this.getManufacturers();
  }

  getManufacturers(): void {
    this.manufacturers$ = this.vehicleService.getManufacturers();
  }

  initForm(): void {
    this.vehicleForm = this.fb.group({
      id: ['', Validators.required],
      manufacturer: ['', Validators.required],
      model: ['', Validators.required],
      purchasePrice: ['', [Validators.required, Validators.min(0)]],
      picture: [''],
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

  onSave(): void {
    if (this.vehicleForm.valid) {
      const formValue = this.vehicleForm.getRawValue();

      const manufacturerId = formValue.manufacturer;
      formValue.manufacturer = { id: manufacturerId };

      if (
        this.vehicleType === 'car' &&
        formValue.purchaseDate instanceof Date
      ) {
        formValue.purchaseDate = formValue.purchaseDate.toISOString();
      }

      let vehicleToSave: Car | EScooter | EBike;

      if (this.vehicleType === 'car') {
        vehicleToSave = formValue as Car;
      } else if (this.vehicleType === 'e-scooter') {
        vehicleToSave = formValue as EScooter;
      } else if (this.vehicleType === 'e-bike') {
        vehicleToSave = formValue as EBike;
      } else {
        console.error('Unknown vehicle type:', this.vehicleType);
        return;
      }

      console.log(
        'Final vehicle object for saving:',
        this.vehicleType,
        vehicleToSave
      );

      let saveObservable: Observable<Car | EBike | EScooter>;

      if (this.vehicleType === 'car') {
        saveObservable = this.vehicleService.addCar(vehicleToSave as Car);
      } else if (this.vehicleType === 'e-scooter') {
        saveObservable = this.vehicleService.addEScooter(
          vehicleToSave as EScooter
        );
      } else if (this.vehicleType === 'e-bike') {
        saveObservable = this.vehicleService.addEBike(vehicleToSave as EBike);
      } else {
        console.error('Unknown vehicle type during save:', this.vehicleType);
        return;
      }

      saveObservable.subscribe({
        next: (response) => {
          console.log('Vehicle saved successfully:', response);
          // Prosledi 'true' ili neki indikator da je uspešno sačuvano
          // Ovo će AdminVehiclesManagementComponent primiti kao rezultat dialogRef.afterClosed()
          this.dialogRef.close(true);
        },
        error: (err) => {
          console.error('Error saving vehicle:', err);
          // Opcionalno: prikaži poruku korisniku da je došlo do greške
          // Ne zatvaraj dijalog ako je došlo do greške, pusti korisnika da ispravi
        },
      });
    } else {
      this.vehicleForm.markAllAsTouched();
      console.warn('Form is invalid:', this.vehicleForm.errors);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
