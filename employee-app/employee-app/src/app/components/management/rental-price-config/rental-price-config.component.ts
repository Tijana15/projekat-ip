import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

import { RentalPriceConfigService } from '../../../service/rental-price-config.service';
import { UpdateRentalPriceDTO } from '../../../model/rentalPrice.model';

@Component({
  selector: 'app-rental-price-config',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
  ],
  templateUrl: './rental-price-config.component.html',
  styleUrls: ['./rental-price-config.component.css'],
})
export class RentalPriceConfigComponent implements OnInit {
  priceForm: FormGroup;
  isChanged: boolean = false;
  isLoading: boolean = false;
  originalValues: any = {};

  constructor(
    private fb: FormBuilder,
    private rentalPriceService: RentalPriceConfigService
  ) {
    this.priceForm = this.fb.group({
      carPrice: [0, [Validators.required, Validators.min(0)]],
      ebikePrice: [0, [Validators.required, Validators.min(0)]],
      escooterPrice: [0, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit(): void {
    this.loadPrices();
  }

  private loadPrices(): void {
    this.isLoading = true;
    this.rentalPriceService.getPrices().subscribe({
      next: (data) => {
        this.priceForm.patchValue(data);
        this.originalValues = { ...data };
        this.isChanged = false;
        this.isLoading = false;
        console.log('Prices loaded:', data);
      },
      error: (err) => {
        console.error('Error loading prices:', err);
        this.isLoading = false;
        // Postaviti default vrijednosti ako load ne uspije
        this.priceForm.patchValue({
          carPrice: 0,
          ebikePrice: 0,
          escooterPrice: 0
        });
      }
    });
  }

  onInputChange(): void {
    // Provjeri da li su se vrijednosti promijenile u odnosu na originalne
    const currentValues = this.priceForm.value;
    this.isChanged = JSON.stringify(currentValues) !== JSON.stringify(this.originalValues);
  }

  save(): void {
    if (this.priceForm.valid && this.isChanged) {
      this.isLoading = true;
      const dto: UpdateRentalPriceDTO = this.priceForm.value;

      this.rentalPriceService.updatePrices(dto).subscribe({
        next: () => {
          console.log('Prices updated successfully');
          alert('Cijene uspješno sačuvane!');
          this.originalValues = { ...dto };
          this.isChanged = false;
          this.isLoading = false;
        },
        error: (err) => {
          console.error('Error updating prices:', err);
          alert('Greška prilikom čuvanja cijena.');
          this.isLoading = false;
        },
      });
    }
  }

  resetForm(): void {
    if (confirm('Da li ste sigurni da želite da resetujete promjene?')) {
      this.priceForm.patchValue(this.originalValues);
      this.isChanged = false;
    }
  }

  get carPrice() {
    return this.priceForm.get('carPrice');
  }

  get ebikePrice() {
    return this.priceForm.get('ebikePrice');
  }

  get escooterPrice() {
    return this.priceForm.get('escooterPrice');
  }
}
