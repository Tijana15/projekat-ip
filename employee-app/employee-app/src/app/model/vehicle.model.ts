export interface Vehicle {
  id: string;
  manufacturer: Manufacturer;
  model: String;
  purchasePrice: number;
  picture?: String;
  mapX: number;
  mapY: number;
  vehicleState: String;
}

export interface Car extends Vehicle {
  purchaseDate: Date;
  description: String;
}

export interface EScooter extends Vehicle {
  maxSpeed: number;
}

export interface EBike extends Vehicle {
  maxRange: number;
}

export interface Manufacturer {
  id: number;
  name?: String;
  state: String;
  address: String;
  phone: String;
  fax: String;
  mail: String;
}
