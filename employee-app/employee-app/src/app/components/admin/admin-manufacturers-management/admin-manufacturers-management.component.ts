import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormField, MatFormFieldModule } from '@angular/material/form-field';
import { MatLabel } from '@angular/material/form-field';
import { Observable } from 'rxjs';

import { Manufacturer } from '../../../model/vehicle.model';
import { VehicleService } from '../../../service/vehicle.service';
import { MatInputModule } from '@angular/material/input';
import { MatDialog } from '@angular/material/dialog';
import { AddManufacturerDialogComponent } from '../add-manufacturer-dialog/add-manufacturer-dialog.component';
import { EditManufacturerDialogComponent } from '../edit-manufacturer-dialog/edit-manufacturer-dialog.component';

@Component({
  selector: 'app-admin-manufacturers-management',
  standalone: true,
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormField,
    MatLabel,
    MatFormFieldModule,
    MatInputModule,
  ],
  templateUrl: './admin-manufacturers-management.component.html',
  styleUrl: './admin-manufacturers-management.component.css',
})
export class AdminManufacturersManagementComponent implements OnInit {
  displayedColumns: string[] = [
    'id',
    'name',
    'state',
    'address',
    'phone',
    'fax',
    'mail',
    'actions',
  ];

  dataSource = new MatTableDataSource<Manufacturer>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private vehicleService: VehicleService,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.loadManufacturers();
  }

  loadManufacturers(): void {
    this.vehicleService.getManufacturers().subscribe({
      next: (data) => {
        this.dataSource.data = data;
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      },
      error: (err) => {
        console.error('Error loading manufacturers:', err);
      },
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  addManufacturer(): void {
    const dialogRef = this.dialog.open(AddManufacturerDialogComponent, {
      width: '500px',
      disableClose: true,
    });
    this.loadManufacturers();
    dialogRef.afterClosed().subscribe((newManufacturerData) => {
      if (newManufacturerData) {
        console.log('New manufacturer to add:', newManufacturerData);
        this.loadManufacturers();
      } else {
        console.log('Add manufacturer dialog closed without saving.');
      }
    });
    this.loadManufacturers();
  }

  editManufacturer(manufacturer: Manufacturer): void {
    console.log('Edit manufacturer:', manufacturer);
    const dialogRef = this.dialog.open(EditManufacturerDialogComponent, {
      width: '600px',
      disableClose: true,
      data: manufacturer,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        console.log('Edited manufacturer:', result);
        this.loadManufacturers();
      } else {
        console.log('Edit manufacturer dialog closed without saving.');
      }
    });
  }

  deleteManufacturer(id: number): void {
    console.log('Delete manufacturer with ID:', id);
    if (confirm('Are you sure you want to delete this manufacturer?')) {
      this.vehicleService.deleteManufacturer(id).subscribe({
        next: () => {
          console.log('Manufacturer deleted successfully.');
          this.loadManufacturers();
        },
        error: (err) => console.error('Error deleting manufacturer:', err),
      });
    }
  }
}
