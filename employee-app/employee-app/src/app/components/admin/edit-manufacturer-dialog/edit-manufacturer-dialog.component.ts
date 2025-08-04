import { Component, OnInit, Inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import {
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatDialogModule,
} from '@angular/material/dialog';
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
  selector: 'app-edit-manufacturer-dialog',
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
  templateUrl: './edit-manufacturer-dialog.component.html',
  styleUrl: './edit-manufacturer-dialog.component.css',
})
export class EditManufacturerDialogComponent implements OnInit {
  manufacturerForm: FormGroup;
  manufacturerId: number | undefined;

  constructor(
    private fb: FormBuilder,
    private manufacturerService: VehicleService,
    public dialogRef: MatDialogRef<EditManufacturerDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Manufacturer
  ) {
    this.manufacturerForm = this.fb.group({
      name: ['', Validators.required],
      state: ['', Validators.required],
      address: ['', Validators.required],
      phone: ['', Validators.required],
      fax: ['', Validators.required],
      mail: ['', [Validators.required, Validators.email]],
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.manufacturerId = this.data.id;

      this.manufacturerForm.patchValue({
        name: this.data.name,
        state: this.data.state,
        address: this.data.address,
        phone: this.data.phone,
        fax: this.data.fax,
        mail: this.data.mail,
      });
      console.log(
        'Forma popunjena sa vrijednostima (this.manufacturerForm.value):',
        this.manufacturerForm.value
      );
    } else {
      console.log(
        'Nema podataka (this.data je null/undefined) proslijeđenih dijalogu.'
      );
    }
    console.log('--- EditManufacturerDialog: ngOnInit Kraj ---');
  }

  onSubmit(): void {
    if (this.manufacturerForm.valid && this.manufacturerId !== undefined) {
      const updatedManufacturer: Manufacturer = {
        ...this.data,
        ...this.manufacturerForm.value,
        id: this.manufacturerId,
      };

      this.manufacturerService
        .updateManufacturer(this.manufacturerId, updatedManufacturer)
        .subscribe({
          next: (response) => {
            console.log('Proizvođač uspješno ažuriran:', response);
            this.dialogRef.close(response);
          },
          error: (error) => {
            console.error('Greška prilikom ažuriranja proizvođača:', error);
          },
        });
    } else {
      this.manufacturerForm.markAllAsTouched();
      console.log('Neispravna forma ili ID proizvođača nedostaje.');
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
