import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';
import { LeafletModule } from '@bluehalo/ngx-leaflet';
import { VehicleService } from '../../../service/vehicle.service';

@Component({
  selector: 'app-vehicle-map',
  standalone: true,
  imports: [LeafletModule],
  templateUrl: './vehicle-map.component.html',
  styleUrl: './vehicle-map.component.css',
})
export class VehicleMapComponent implements OnInit {
  private map!: L.Map;
  private markersLayer = L.layerGroup();

  constructor(private vehicleService: VehicleService) {}

  private availableIcon = L.icon({
    iconUrl: 'assets/images/available-marker.png',
    iconSize: [50, 50],
    iconAnchor: [25, 50],
    popupAnchor: [0, -50],
  });

  private unavailableIcon = L.icon({
    iconUrl: 'assets/images/nonavailable-marker.png',
    iconSize: [40, 40],
    iconAnchor: [20, 40],
    popupAnchor: [0, -40],
  });

  options = {
    layers: [
      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© OpenStreetMap contributors',
      }),
    ],
    zoom: 15,
    center: L.latLng(44.772182, 17.191),
  };

  ngOnInit(): void {
    this.vehicleService.getAllVehicles().subscribe({
      next: (vehicles) => {
        console.log('Primljena vozila:', vehicles);
        this.markersLayer.clearLayers();

        vehicles.forEach((vehicle: any) => {
          const x = Number(vehicle.mapX ?? vehicle.positionX);
          const y = Number(vehicle.mapY ?? vehicle.positionY);

          if (!isNaN(x) && !isNaN(y)) {
            const marker = L.marker([x, y], {
              icon:
                vehicle.vehicleState === 'AVAILABLE'
                  ? this.availableIcon
                  : this.unavailableIcon,
            });

            marker
              .bindPopup(
                `${vehicle.id} - ${vehicle.manufacturer?.name ?? ''} ${
                  vehicle.model ?? ''
                }`
              )
              .addTo(this.markersLayer);
          } else {
            console.warn('Preskačem vozilo bez validnih koordinata:', vehicle);
          }
        });

        if (this.map) {
          this.markersLayer.addTo(this.map);
        }
      },
      error: (err) => {
        console.error('Greška prilikom dohvata vozila:', err);
      },
    });
  }

  onMapReady(map: L.Map) {
    this.map = map;
    this.markersLayer.addTo(this.map);
  }
}
