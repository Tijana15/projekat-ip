import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  DailyRevenueData,
  VehicleBreakdownData,
  VehicleRevenueData,
} from '../model/statistics.model';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  private baseUrl = `http://localhost:8080/api/v1/statistics`;

  constructor(private http: HttpClient) {}

  getDailyRevenue(year: number, month: number): Observable<DailyRevenueData[]> {
    const params = new HttpParams()
      .set('year', year.toString())
      .set('month', month.toString());

    return this.http.get<DailyRevenueData[]>(`${this.baseUrl}/daily-revenue`, {
      params,
    });
  }

  getBreakdownsByVehicle(): Observable<VehicleBreakdownData[]> {
    return this.http.get<VehicleBreakdownData[]>(
      `${this.baseUrl}/breakdowns-by-vehicle`
    );
  }

  getRevenueByVehicle(): Observable<VehicleRevenueData[]> {
    return this.http.get<VehicleRevenueData[]>(
      `${this.baseUrl}/revenue-by-vehicle`
    );
  }
}
