import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CdkTableModule } from '@angular/cdk/table';

import { UserService } from '../../../service/user.service';
import { Employee } from '../../../model/employee.model';
import { AddEmployeeDialogComponent } from '../add-employee-dialog/add-employee-dialog.component';
import { EditEmployeeDialogComponent } from '../edit-employee-dialog/edit-employee-dialog.component';
@Component({
  selector: 'app-admin-employees-management',
  imports: [
    CommonModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    MatDialogModule,
    CdkTableModule,
  ],
  templateUrl: './admin-employees-management.component.html',
  styleUrl: './admin-employees-management.component.css',
})
export class AdminEmployeesManagementComponent
  implements OnInit, AfterViewInit
{
  displayedColumns: string[] = [
    'id',
    'firstname',
    'lastname',
    'username',
    'role',
    'actions',
  ];

  dataSource = new MatTableDataSource<Employee>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadEmployees(): void {
    this.userService.getEmployees().subscribe({
      next: (data) => {
        console.log('Employees data received from service:', data);
        if (Array.isArray(data)) {
          this.dataSource.data = data;
        } else {
          console.error('Data received is not an array:', data);
        }
        if (this.dataSource.paginator) {
          this.dataSource.paginator.firstPage();
        }
      },
      error: (err) => {
        console.error('Error loading employees:', err);
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

  editEmployee(employee: Employee): void {
    console.log('Edit employee:', employee);
    const dialogRef = this.dialog.open(EditEmployeeDialogComponent, {
      width: '500px',
      disableClose: true,
      data: employee,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        console.log('Employee edited successfully:', result);
        this.loadEmployees();
      } else {
        console.log('Edit employee dialog closed without saving.');
      }
    });
  }

  deleteEmployee(id: number): void {
    this.userService.deleteEmployee(id).subscribe({
      next: (data) => {
        console.log(data);
      },
      error: (err) => {
        console.error('Error deleting employee:', err);
      },
    });
  }
  addEmployee(): void {
    const dialogRef = this.dialog.open(AddEmployeeDialogComponent, {
      width: '500px',
      disableClose: true,
    });
    this.loadEmployees();
    dialogRef.afterClosed().subscribe((newManufacturerData) => {
      if (newManufacturerData) {
        console.log('New manufacturer to add:', newManufacturerData);
        this.loadEmployees();
      } else {
        console.log('Add manufacturer dialog closed without saving.');
      }
    });
    this.loadEmployees();
  }
}
