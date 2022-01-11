import { Routes } from '@angular/router';


import { UserProfileComponent } from '../../user-profile/user-profile.component';
import { TableListComponent } from '../../table-list/table-list.component';
import { TaxesComponent } from '../../taxes/taxes.component';
import { NotificationsComponent } from '../../notifications/notifications.component';
import { LoginComponent } from '../../login/login.component';
import { LogoutComponent } from '../../logout/logout.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'user-profile',   component: UserProfileComponent },
    { path: 'all-users',     component: TableListComponent },
    { path: 'taxes',          component: TaxesComponent },
    { path: 'pending-accounts',  component: NotificationsComponent },
    { path: 'login', component: LoginComponent },
    { path: 'logout', component: LogoutComponent },
];
