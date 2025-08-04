import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Car, EBike, EScooter, Manufacturer } from '../model/vehicle.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private readonly apiUrl = 'http://localhost:8080/api/v1/';

  constructor(private http: HttpClient) {}

  getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(this.apiUrl + 'cars');
  }
  getEBikes(): Observable<EBike[]> {
    return this.http.get<EBike[]>(this.apiUrl + 'ebikes');
  }

  getEScooters(): Observable<EScooter[]> {
    return this.http.get<EScooter[]>(this.apiUrl + 'escooters');
  }

  addCar(car: Car): Observable<Car> {
    return this.http.post<Car>(this.apiUrl + 'cars', car);
  }
  getManufacturers(): Observable<Manufacturer[]> {
    return this.http.get<Manufacturer[]>(this.apiUrl + 'manufacturers');
  }
  addEBike(ebike: EBike): Observable<EBike> {
    return this.http.post<EBike>(this.apiUrl + 'ebikes', ebike);
  }
  addEScooter(escooter: EScooter): Observable<EScooter> {
    return this.http.post<EScooter>(this.apiUrl + 'escooters', escooter);
  }
  deleteCar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}cars/${id}`);
  }
  deleteEBike(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}ebikes/${id}`);
  }

  deleteEScooter(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}escooters/${id}`);
  }

  getCarById(id: string): Observable<Car> {
    return this.http.get<Car>(`${this.apiUrl}cars/${id}`);
  }

  getEBikeById(id: string): Observable<EBike> {
    return this.http.get<EBike>(`${this.apiUrl}ebikes/${id}`);
  }

  getEScooterById(id: string): Observable<EScooter> {
    return this.http.get<EScooter>(`${this.apiUrl}escooters/${id}`);
  }
  updateCar(id: string, car: Car): Observable<Car> {
    return this.http.put<Car>(`${this.apiUrl}cars/${id}`, car);
  }

  updateEBike(id: string, ebike: EBike): Observable<EBike> {
    return this.http.put<EBike>(`${this.apiUrl}ebikes/${id}`, ebike);
  }

  updateEScooter(id: string, escooter: EScooter): Observable<EScooter> {
    return this.http.put<EScooter>(`${this.apiUrl}escooters/${id}`, escooter);
  }
  deleteManufacturer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}manufacturers/${id}`);
  }
  addManufacturer(manufacturer: Manufacturer): Observable<Manufacturer> {
    return this.http.post<Manufacturer>(
      this.apiUrl + 'manufacturers',
      manufacturer
    );
  }
  updateManufacturer(
    id: number,
    manufacturer: Manufacturer
  ): Observable<Manufacturer> {
    return this.http.put<Manufacturer>(
      `${this.apiUrl}manufacturers/${id}`,
      manufacturer
    );
  }
}
