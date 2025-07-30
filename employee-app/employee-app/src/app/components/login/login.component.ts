import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { AuthService } from '../../service/auth.service';
import { LoginDTO } from '../../model/loginDTO.model';
import { Employee } from '../../model/employee.model';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  invalidCredentials: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  login(): void {
    this.invalidCredentials = false;

    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      const loginDto: LoginDTO = { username, password };

      this.authService.login(loginDto).subscribe({
        next: (employee: Employee) => {
          console.log('Successfull. Logged in employee: ', employee);
          this.invalidCredentials = false;
          this.navigateBasedOnRole(employee.role);
        },
        error: (error) => {
          console.error('Error:', error);
          this.invalidCredentials = true;
        },
      });
    } else {
      console.log('Invalid form. Please fill all required fields.');
      this.loginForm.markAllAsTouched();
    }
  }

  private navigateBasedOnRole(role: string): void {
    switch (role) {
      case 'ADMIN':
        this.router.navigate(['/admin-dashboard']);
        break;
      case 'OPERATOR':
        this.router.navigate(['/operator-dashboard']);
        break;
      case 'MANAGEMENT':
        this.router.navigate(['/management-dashboard']);
        break;
      default:
        console.error('Client cannot login on employee app.');
        this.router.navigate(['/login']);
        break;
    }
  }
}
