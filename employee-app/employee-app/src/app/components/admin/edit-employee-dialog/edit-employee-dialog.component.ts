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
import { UserService } from '../../../service/user.service';
import { Employee } from '../../../model/employee.model';

@Component({
  selector: 'app-edit-employee-dialog',
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
  templateUrl: './edit-employee-dialog.component.html',
  styleUrl: './edit-employee-dialog.component.css',
})
export class EditEmployeeDialogComponent implements OnInit {
  employeeForm: FormGroup;
  employeeId: number | any;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    public dialogRef: MatDialogRef<EditEmployeeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Employee
  ) {
    this.employeeForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      password: [''],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    if (this.data) {
      this.employeeId = this.data.id;
      this.employeeForm.patchValue({
        firstname: this.data.firstname,
        lastname: this.data.lastname,
        username: this.data.username,
        password: this.data.password,
        role: this.data.role,
      });
    } else {
      console.log(
        'Nema podataka (this.data je null/undefined) proslijeđenih dijalogu.'
      );
    }
  }

  onSubmit(): void {
    if (this.employeeForm.valid && this.employeeId !== undefined) {
      const updatedEmployee: Employee = {
        ...this.data,
        ...this.employeeForm.value,
        id: this.employeeId,
      };

      this.userService
        .editEmployee(this.employeeId, updatedEmployee)
        .subscribe({
          next: (response) => {
            console.log('Zaposleni uspješno ažuriran:', response);
            this.dialogRef.close(response);
          },
          error: (error) => {
            console.error('Greška prilikom ažuriranja zaposlenog:', error);
          },
        });
    } else {
      this.employeeForm.markAllAsTouched();
      console.log('Neispravna forma ili ID zaposlenog nedostaje.');
    }
  }

  onCancel(): void {
    this.dialogRef.close(false);
  }
}
