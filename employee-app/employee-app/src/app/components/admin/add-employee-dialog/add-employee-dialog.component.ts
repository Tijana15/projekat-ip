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
import { UserService } from '../../../service/user.service';
import { Employee } from '../../../model/employee.model';

@Component({
  selector: 'app-add-employee-dialog',
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
  templateUrl: './add-employee-dialog.component.html',
  styleUrl: './add-employee-dialog.component.css',
})
export class AddEmployeeDialogComponent implements OnInit {
  employeeForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    public dialogRef: MatDialogRef<AddEmployeeDialogComponent>
  ) {
    this.employeeForm = this.fb.group({
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.employeeForm.valid) {
      const newEmployee: Employee = this.employeeForm.value;
      console.log('New employee:', newEmployee);

      this.userService.addEmployee(newEmployee).subscribe({
        next: (response) => {
          console.log('Employee added succesfully:', response);
          this.employeeForm.reset();
        },
        error: (error) => {
          console.error('Error while adding new employee. ', error);
        },
      });
      this.employeeForm.reset();
      alert('Sucessfully');
    } else {
      this.employeeForm.markAllAsTouched();
      console.log('Invalid form. Check data.');
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
