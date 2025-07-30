import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

import { LoginDTO } from '../model/loginDTO.model';
import { Employee } from '../model/employee.model';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) {}

  login(loginDto: LoginDTO): Observable<Employee> {
    
    return this.http.post<Employee>(this.apiUrl, loginDto).pipe(
      map((employee) => {
        console.log('Sucessfull, employee:', employee);
        return employee;
      }),
      catchError((error) => {
        console.error('Error:', error);
        if (error.status === 404) {
          return throwError(() => new Error('Not valid username or pasword.'));
        }
        return throwError(
          () => new Error('Error occupied on server. Try later.')
        );
      })
    );
  }
}
