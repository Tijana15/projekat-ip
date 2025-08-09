import {
  Component,
  OnInit,
  ViewChild,
  ElementRef,
  AfterViewInit,
} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { Chart, ChartConfiguration, ChartType, registerables } from 'chart.js';

import { StatisticsService } from '../../../service/statistics.service';
import {
  DailyRevenueData,
  VehicleBreakdownData,
  VehicleRevenueData,
} from '../../../model/statistics.model';

Chart.register(...registerables);

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatSelectModule,
    MatFormFieldModule,
  ],
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css'],
})
export class StatisticsComponent implements OnInit, AfterViewInit {
  @ViewChild('dailyRevenueCanvas', { static: false })
  dailyRevenueCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('breakdownCanvas', { static: false })
  breakdownCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('revenueCanvas', { static: false })
  revenueCanvas!: ElementRef<HTMLCanvasElement>;

  private dailyRevenueChart!: Chart;
  private breakdownChart!: Chart;
  private revenueChart!: Chart;

  selectedYear = new Date().getFullYear();
  selectedMonth = new Date().getMonth() + 1;

  years = [2023, 2024, 2025];
  months = [
    { value: 1, name: 'January' },
    { value: 2, name: 'February' },
    { value: 3, name: 'March' },
    { value: 4, name: 'April' },
    { value: 5, name: 'May' },
    { value: 6, name: 'June' },
    { value: 7, name: 'July' },
    { value: 8, name: 'August' },
    { value: 9, name: 'September' },
    { value: 10, name: 'October' },
    { value: 11, name: 'November' },
    { value: 12, name: 'December' },
  ];

  constructor(private statisticsService: StatisticsService) {}

  ngOnInit(): void {}

  ngAfterViewInit(): void {
    this.initializeCharts();
    this.loadAllStatistics();
  }

  onMonthYearChange(): void {
    this.loadDailyRevenue();
  }

  private initializeCharts(): void {
    this.initDailyRevenueChart();
    this.initBreakdownChart();
    this.initRevenueChart();
  }

  private initDailyRevenueChart(): void {
    const ctx = this.dailyRevenueCanvas.nativeElement.getContext('2d')!;

    this.dailyRevenueChart = new Chart(ctx, {
      type: 'line',
      data: {
        labels: [],
        datasets: [
          {
            data: [],
            label: 'Daily Revenue (€)',
            borderColor: '#42A5F5',
            backgroundColor: 'rgba(66, 165, 245, 0.2)',
            tension: 0.4,
            fill: true,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: true,
            position: 'top',
          },
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Revenue (€)',
            },
          },
          x: {
            title: {
              display: true,
              text: 'Day',
            },
          },
        },
      },
    });
  }

  private initBreakdownChart(): void {
    const ctx = this.breakdownCanvas.nativeElement.getContext('2d')!;

    this.breakdownChart = new Chart(ctx, {
      type: 'pie',
      data: {
        labels: [],
        datasets: [
          {
            data: [],
            backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0'],
            borderWidth: 2,
            borderColor: '#fff',
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'right',
          },
        },
      },
    });
  }

  private initRevenueChart(): void {
    const ctx = this.revenueCanvas.nativeElement.getContext('2d')!;

    this.revenueChart = new Chart(ctx, {
      type: 'bar',
      data: {
        labels: [],
        datasets: [
          {
            data: [],
            label: 'Total Revenue (€)',
            backgroundColor: [
              'rgba(255, 99, 132, 0.8)',
              'rgba(54, 162, 235, 0.8)',
              'rgba(255, 206, 86, 0.8)',
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
            ],
            borderWidth: 2,
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
          },
        },
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Revenue (€)',
            },
          },
          x: {
            title: {
              display: true,
              text: 'Vehicle Type',
            },
          },
        },
      },
    });
  }

  private loadAllStatistics(): void {
    this.loadDailyRevenue();
    this.loadVehicleBreakdowns();
    this.loadVehicleRevenue();
  }

  private loadDailyRevenue(): void {
    this.statisticsService
      .getDailyRevenue(this.selectedYear, this.selectedMonth)
      .subscribe({
        next: (data: DailyRevenueData[]) => {
          const labels = data.map((item) => {
            const date = new Date(item.date);
            return `${date.getDate()}.${date.getMonth() + 1}.`;
          });
          const values = data.map((item) => item.revenue);

          this.dailyRevenueChart.data.labels = labels;
          this.dailyRevenueChart.data.datasets[0].data = values;
          this.dailyRevenueChart.update();
        },
        error: (err) => console.error('Error loading daily revenue:', err),
      });
  }

  private loadVehicleBreakdowns(): void {
    this.statisticsService.getBreakdownsByVehicle().subscribe({
      next: (data: VehicleBreakdownData[]) => {
        const labels = data.map((item) =>
          this.translateVehicleType(item.vehicleType)
        );
        const values = data.map((item) => item.breakdownCount);

        this.breakdownChart.data.labels = labels;
        this.breakdownChart.data.datasets[0].data = values;
        this.breakdownChart.update();
      },
      error: (err) => console.error('Error loading breakdowns:', err),
    });
  }

  private loadVehicleRevenue(): void {
    this.statisticsService.getRevenueByVehicle().subscribe({
      next: (data: VehicleRevenueData[]) => {
        const labels = data.map((item) =>
          this.translateVehicleType(item.vehicleType)
        );
        const values = data.map((item) => item.totalRevenue);

        this.revenueChart.data.labels = labels;
        this.revenueChart.data.datasets[0].data = values;
        this.revenueChart.update();
      },
      error: (err) => console.error('Error loading vehicle revenue:', err),
    });
  }

  private translateVehicleType(type: string): string {
    switch (type) {
      case 'CAR':
        return 'Car';
      case 'EBIKE':
        return 'E-bike';
      case 'ESCOOTER':
        return 'E-scooter';
      default:
        return type;
    }
  }
}
