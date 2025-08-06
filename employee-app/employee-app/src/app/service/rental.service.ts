import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RentalSimple, Rental } from '../model/rental.model';

@Injectable({
  providedIn: 'root',
})
export class RentalService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/rentals';
  constructor(private http: HttpClient) {}

  getRentals(): Observable<RentalSimple[]> {
    return this.http.get<RentalSimple[]>(this.apiUrl);
  }

  getRentalsByVehicleId(vehicleId: string): Observable<RentalSimple[]> {
    const url = `${this.apiUrl}/vehicle/${vehicleId}`;
    return this.http.get<RentalSimple[]>(url);
  }

  addRental(newRental: Omit<Rental, 'id'>): Observable<Rental> {
    return this.http.post<Rental>(this.apiUrl, newRental);
  }
}
