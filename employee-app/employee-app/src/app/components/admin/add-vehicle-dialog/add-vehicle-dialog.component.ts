import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
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
import Papa, { ParseResult } from 'papaparse';

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

    FormsModule,
  ],
  templateUrl: './add-vehicle-dialog.component.html',
  styleUrl: './add-vehicle-dialog.component.css',
})
export class AddVehicleDialogComponent implements OnInit {
  getRandomOffset = () => (Math.random() - 0.5) * 0.02;

  showManualForm: boolean = true;
  showCsvUpload: boolean = false;
  vehicleForm!: FormGroup;
  vehicleType: 'car' | 'e-scooter' | 'e-bike';
  manufacturers$!: Observable<Manufacturer[]>;
  csvSelectedVehicleType: 'car' | 'e-scooter' | 'e-bike' | null = null;
  selectedFile: File | null = null;
  selectedFileName: string = '';
  uploadMessage: string = '';
  uploadSuccess: boolean = false;

  @ViewChild('fileInput') fileInput: any;

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

      const formData = new FormData();
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

          this.dialogRef.close(true);
        },
        error: (err) => {
          console.error('Error saving vehicle:', err);
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

  selectImportOption(): void {
    this.showCsvUpload = true;
    this.showManualForm = false;
    this.resetUploadState();
  }

  selectManualOption(): void {
    this.showManualForm = true;
    this.showCsvUpload = false;
    this.resetUploadState();
  }

  resetOptions(): void {
    this.showManualForm = false;
    this.showCsvUpload = false;
    this.resetUploadState();
    this.vehicleForm.reset();
  }

  resetUploadState(): void {
    this.selectedFile = null;
    this.selectedFileName = '';
    this.uploadMessage = '';
    this.uploadSuccess = false;
    if (this.fileInput) {
      this.fileInput.nativeElement.value = '';
    }
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const fileName = file.name;

      this.vehicleForm.patchValue({ picture: fileName });
    }
  }

  private parseCsvGeneric(csvContent: string): any[] {
    const parsed: ParseResult<any> = Papa.parse(csvContent as any, {
      header: true,
      skipEmptyLines: true,
      //trimHeaders: true,
    }) as unknown as ParseResult<any>;

    if (parsed.errors.length > 0) {
      console.error('PapaParse errors:', parsed.errors);
      throw new Error(
        'CSV parsing errors detected. Check console for details.'
      );
    }
    return parsed.data;
  }

  private parseCsvForEBike(csvContent: string): EBike[] {
    const parsedRows = this.parseCsvGeneric(csvContent);
    const bikes: EBike[] = [];
    const centerLat = 44.7722;
    const centerLng = 17.191;
    const mapY = centerLat + this.getRandomOffset(); // latitude
    const mapX = centerLng + this.getRandomOffset();

    if (parsedRows.length > 0) {
      const row = parsedRows[0];
      try {
        const manufacturerId = parseInt(row['manufacturerId']?.trim(), 10);
        const bike: EBike = {
          id: row['id']?.trim(),
          manufacturer: { id: manufacturerId } as Manufacturer,
          model: row['model']?.trim(),
          purchasePrice: parseFloat(row['purchasePrice']),

          picture: row['picture']?.trim() || '',
          maxRange: parseFloat(row['maxRange']),
          mapX: (
            row['mapX']?.trim() || centerLng + this.getRandomOffset()
          ).toString(),
          mapY: (
            row['mapY']?.trim() || centerLat + this.getRandomOffset()
          ).toString(),
        };

        if (
          !bike.id ||
          isNaN(manufacturerId) ||
          !bike.model ||
          isNaN(bike.purchasePrice) ||
          isNaN(bike.maxRange)
        ) {
          console.warn(
            `Skipping invalid e-bike row (missing required data): ${JSON.stringify(
              row
            )}`
          );
          return [];
        }
        bikes.push(bike);
      } catch (e) {
        console.warn(
          `Error parsing e-bike row: ${JSON.stringify(row)}, Error: ${e}`
        );
      }
    }
    return bikes;
  }

  private parseCsvForCar(csvContent: string): Car[] {
    const parsedRows = this.parseCsvGeneric(csvContent);
    const cars: Car[] = [];

    if (parsedRows.length > 0) {
      const row = parsedRows[0];
      const centerLat = 44.7722;
      const centerLng = 17.191;
      const mapY = centerLat + this.getRandomOffset(); // latitude
      const mapX = centerLng + this.getRandomOffset();
      try {
        const manufacturerId = parseInt(row['manufacturerId']?.trim(), 10);
        const car: Car = {
          id: row['id']?.trim(),
          manufacturer: { id: manufacturerId } as Manufacturer,
          model: row['model']?.trim(),
          purchasePrice: parseFloat(row['purchasePrice']),

          picture: row['picture']?.trim() || '',
          purchaseDate: row['purchaseDate']?.trim(),
          description: row['description']?.trim() || '',
          mapX: (
            row['mapX']?.trim() || centerLng + this.getRandomOffset()
          ).toString(),
          mapY: (
            row['mapY']?.trim() || centerLat + this.getRandomOffset()
          ).toString(),
        };

        if (
          !car.id ||
          isNaN(manufacturerId) ||
          !car.model ||
          isNaN(car.purchasePrice) ||
          !car.purchaseDate
        ) {
          console.warn(
            `Skipping invalid car row (missing required data): ${JSON.stringify(
              row
            )}`
          );
          return [];
        }
        cars.push(car);
      } catch (e) {
        console.warn(
          `Error parsing car row: ${JSON.stringify(row)}, Error: ${e}`
        );
      }
    }
    return cars;
  }

  private parseCsvForEScooter(csvContent: string): EScooter[] {
    const parsedRows = this.parseCsvGeneric(csvContent);
    const scooters: EScooter[] = [];
    const centerLat = 44.7722;
    const centerLng = 17.191;
    const mapY = centerLat + this.getRandomOffset(); // latitude
    const mapX = centerLng + this.getRandomOffset();

    if (parsedRows.length > 0) {
      const row = parsedRows[0];
      try {
        const manufacturerId = parseInt(row['manufacturerId']?.trim(), 10);
        const scooter: EScooter = {
          id: row['id']?.trim(),
          manufacturer: { id: manufacturerId } as Manufacturer,
          model: row['model']?.trim(),
          purchasePrice: parseFloat(row['purchasePrice']),

          picture: row['picture']?.trim() || '',
          maxSpeed: parseFloat(row['maxSpeed']),
          mapX: (
            row['mapX']?.trim() || centerLng + this.getRandomOffset()
          ).toString(),
          mapY: (
            row['mapY']?.trim() || centerLat + this.getRandomOffset()
          ).toString(),
        };

        if (
          !scooter.id ||
          isNaN(manufacturerId) ||
          !scooter.model ||
          isNaN(scooter.purchasePrice) ||
          isNaN(scooter.maxSpeed)
        ) {
          console.warn(
            `Skipping invalid e-scooter row (missing required data): ${JSON.stringify(
              row
            )}`
          );
          return [];
        }
        scooters.push(scooter);
      } catch (e) {
        console.warn(
          `Error parsing e-scooter row: ${JSON.stringify(row)}, Error: ${e}`
        );
      }
    }
    return scooters;
  }

  uploadCsv(): void {
    if (!this.selectedFile || !this.csvSelectedVehicleType) {
      this.uploadMessage = 'Please select a CSV file and vehicle type.';
      this.uploadSuccess = false;
      return;
    }

    const reader = new FileReader();
    reader.onload = (e: any) => {
      const csvContent = e.target.result;
      try {
        let vehicleToUpload: Car | EScooter | EBike | null = null;

        if (this.csvSelectedVehicleType === 'car') {
          const cars = this.parseCsvForCar(csvContent);
          if (cars.length > 0) vehicleToUpload = cars[0];
        } else if (this.csvSelectedVehicleType === 'e-scooter') {
          const scooters = this.parseCsvForEScooter(csvContent);
          if (scooters.length > 0) vehicleToUpload = scooters[0];
        } else if (this.csvSelectedVehicleType === 'e-bike') {
          const bikes = this.parseCsvForEBike(csvContent);
          if (bikes.length > 0) vehicleToUpload = bikes[0];
        }

        console.log(
          `Parsed ${this.csvSelectedVehicleType} from CSV:`,
          vehicleToUpload
        );

        if (vehicleToUpload) {
          let uploadObservable: Observable<any>;

          if (this.csvSelectedVehicleType === 'car') {
            uploadObservable = this.vehicleService.addCar(
              vehicleToUpload as Car
            );
          } else if (this.csvSelectedVehicleType === 'e-scooter') {
            uploadObservable = this.vehicleService.addEScooter(
              vehicleToUpload as EScooter
            );
          } else {
            uploadObservable = this.vehicleService.addEBike(
              vehicleToUpload as EBike
            );
          }

          uploadObservable.subscribe({
            next: (response) => {
              this.uploadMessage = `Successfully uploaded one ${this.csvSelectedVehicleType}.`;
              this.uploadSuccess = true;
              this.dialogRef.close(true);
            },
            error: (error) => {
              this.uploadMessage = `Error uploading ${
                this.csvSelectedVehicleType
              }: ${error.message || 'Unknown error'}`;
              this.uploadSuccess = false;
              console.error('Upload error:', error);
            },
          });
        } else {
          this.uploadMessage = `No valid ${this.csvSelectedVehicleType} data found in CSV.`;
          this.uploadSuccess = false;
        }
      } catch (error: any) {
        this.uploadMessage = `Error parsing CSV: ${
          error.message || 'Invalid CSV format'
        }`;
        this.uploadSuccess = false;
        console.error('CSV parsing error:', error);
      }
    };
    reader.readAsText(this.selectedFile);
  }
  onManualPictureSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0];
      const fileName = file.name; // Uzimamo samo ime fajla kao string

      this.vehicleForm.patchValue({ picture: fileName });

      this.selectedFileName = fileName;
    }
  }
}
