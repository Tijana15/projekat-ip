import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { OperatorDashboardComponent } from './components/operator-dashboard/operator-dashboard.component';
import { ManagementDashboardComponent } from './components/management-dashboard/management-dashboard.component';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'admin-dashboard', component: AdminDashboardComponent },
  { path: 'operator-dashboard', component: OperatorDashboardComponent },
  { path: 'management-dashboard', component: ManagementDashboardComponent },
  { path: '**', redirectTo: '/login' },
];
