import {Routes} from '@angular/router';

import {DashboardComponent} from '../../pages/dashboard/dashboard.component';
import {IconsComponent} from '../../pages/icons/icons.component';
import {MapsComponent} from '../../pages/maps/maps.component';
import {UserProfileComponent} from '../../pages/user-profile/user-profile.component';
import {TablesComponent} from '../../pages/tables/tables.component';
import {EventsComponent} from '../../pages/events/events.component';
import {AuditComponent} from '../../pages/audit/audit.component';
import {ItAuditComponent} from '../../pages/it-audit/it-audit.component';

export const AdminLayoutRoutes: Routes = [
  {path: 'dashboard', component: DashboardComponent},
  {path: 'events', component: EventsComponent},
  {path: 'audit', component: AuditComponent},
  {path: 'itaudit', component: ItAuditComponent},
  {path: 'user-profile', component: UserProfileComponent},
  {path: 'tables', component: TablesComponent},
  {path: 'icons', component: IconsComponent},
  {path: 'maps', component: MapsComponent}
];
