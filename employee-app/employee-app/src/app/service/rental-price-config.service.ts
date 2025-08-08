import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UpdateRentalPriceDTO } from '../model/rentalPrice.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class RentalPriceConfigService {
  private baseUrl = 'http://localhost:8080/api/rental-prices';

  constructor(private http: HttpClient) {}

  updatePrices(dto: UpdateRentalPriceDTO): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, dto);
  }

  getPrices(): Observable<any> {
    return this.http.get(this.baseUrl);
  }
}
