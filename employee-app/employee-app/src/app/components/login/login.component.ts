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
    // Inicijalizujemo loginForm u ngOnInit lifecycle hooku
    // 'username' i 'password' su FormControl-i unutar FormGroup-a
    // Validators.required osigurava da polja ne mogu biti prazna
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
          console.log('Prijava uspešna! Prijavljen zaposlenik:', employee);
          this.invalidCredentials = false;

          //this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error('Greška pri prijavi:', error);

          this.invalidCredentials = true;
        },
      });
    } else {
      console.log('Forma nije validna. Molimo popunite sva obavezna polja.');
      this.loginForm.markAllAsTouched();
    }
  }
}
