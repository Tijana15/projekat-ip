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
import { CdkColumnDef } from '@angular/cdk/table';

import { UserService } from '../../../service/user.service';
import { Client } from '../../../model/client.model';

@Component({
  selector: 'app-admin-clients-management',
  standalone: true,
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
  templateUrl: './admin-clients-management.component.html',
  styleUrl: './admin-clients-management.component.css',
})
export class AdminClientsManagementComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = [
    'id',
    'firstname',
    'lastname',
    'idDocument',
    'email',
    'phone',
    'actions',
  ];

  dataSource = new MatTableDataSource<Client>();

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(private userService: UserService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadClients();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  loadClients(): void {
    this.userService.getClients().subscribe({
      next: (data) => {
        console.log('Clients data received from service:', data);
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
        console.error('Error loading clients:', err);
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

  editClient(client: Client): void {
    console.log('Edit client:', client);
  }

  deleteClient(id: number): void {}
}
