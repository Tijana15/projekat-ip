import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Breakdown } from '../model/breakdown.model';

@Injectable({
  providedIn: 'root',
})
export class BreakdownService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/breakdowns';

  constructor(private http: HttpClient) {}

  getAllBreakdowns(): Observable<Breakdown[]> {
    return this.http.get<Breakdown[]>(this.apiUrl);
  }

  getBreakdownById(id: number): Observable<Breakdown> {
    return this.http.get<Breakdown>(`${this.apiUrl}/${id}`);
  }

  getBreakdownsByVehicleId(vehicleId: string): Observable<Breakdown[]> {
    return this.http.get<Breakdown[]>(`${this.apiUrl}/by-vehicle/${vehicleId}`);
  }

  createBreakdown(breakdown: Breakdown): Observable<Breakdown> {
    return this.http.post<Breakdown>(this.apiUrl, breakdown);
  }

  deleteBreakdownById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
