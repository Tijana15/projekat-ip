export interface Breakdown {
  id: number;
  breakdownDateTime: string;
  description: string;
  vehicleId: string;
}

export interface BreakdownCreateRequest {
  vehicleId: string;
  description: string;
  breakdownDateTime: string;
}
