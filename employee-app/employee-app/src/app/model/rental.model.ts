export interface Rental {
  id: number;
  dateTime: Date;
  startX: number;
  startY: number;
  endX: number;
  endY: number;
  durationSeconds: number;
  price: number;
  active: boolean;
  clientId: number;
  vehicleId: string;
}

export interface RentalSimple {
  id: number;
  dateTime: Date;
  durationSeconds: number;
  clientName: string;
  vehicleId: string;
  price: number;
}
