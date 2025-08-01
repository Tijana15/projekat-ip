// src/app/add-manufacturer-dialog/add-manufacturer-dialog.component.ts

import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCard, MatCardTitle } from '@angular/material/card';
import {
  MatCardActions,
  MatCardContent,
  MatCardHeader,
} from '@angular/material/card';
import { CommonModule } from '@angular/common';

import { Manufacturer } from '../../../model/vehicle.model';
import { VehicleService } from '../../../service/vehicle.service';

@Component({
  selector: 'app-add-manufacturer-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCard,
    MatCardActions,
    MatCardContent,
    MatCardHeader,
    MatCardTitle,
  ],
  templateUrl: './add-manufacturer-dialog.component.html',
  styleUrls: ['./add-manufacturer-dialog.component.css'],
})
export class AddManufacturerDialogComponent implements OnInit {
  manufacturerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private vehicleService: VehicleService,
    public dialogRef: MatDialogRef<AddManufacturerDialogComponent>
  ) {
    this.manufacturerForm = this.fb.group({
      name: ['', Validators.required],
      state: ['', Validators.required],
      address: ['', Validators.required],
      phone: ['', [Validators.required]],
      fax: [''],
      mail: ['', [Validators.required, Validators.email]],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.manufacturerForm.valid) {
      const newManufacturer: Manufacturer = this.manufacturerForm.value;
      console.log('Novi proizvođač:', newManufacturer);

      this.vehicleService.addManufacturer(newManufacturer).subscribe({
        next: (response) => {
          console.log('Proizvođač uspešno dodan:', response);
          this.manufacturerForm.reset();
        },
        error: (error) => {
          console.error('Greška pri dodavanju proizvođača:', error);
        },
      });
      this.manufacturerForm.reset();
      alert('Proizvođač je uspešno dodat (konzolni ispis)!');
    } else {
      this.manufacturerForm.markAllAsTouched();
      console.log('Forma nije validna. Proverite unete podatke.');
    }
  }

  onCancel(): void {
    this.dialogRef.close(); // Zatvori dijalog bez vraćanja podataka
  }
}
