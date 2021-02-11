import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}

export const ROUTES: RouteInfo[] = [
  {path: '/dashboard', title: 'Dashboard', icon: 'ni-tv-2 text-primary', class: ''},
  {path: '/events', title: 'Events', icon: 'ni-bullet-list-67 text-red', class: ''},
  {path: '/audit', title: 'Audit', icon: 'ni-bullet-list-67 text-red', class: ''},
  {path: '/itaudit', title: 'Audit', icon: 'ni-bullet-list-67 text-red', class: ''},
  /*{path: '/icons', title: 'Icons', icon: 'ni-planet text-blue', class: ''},
  {path: '/maps', title: 'Maps', icon: 'ni-pin-3 text-orange', class: ''},
  {path: '/user-profile', title: 'User profile', icon: 'ni-single-02 text-yellow', class: ''},
  {path: '/tables', title: 'Tables', icon: 'ni-bullet-list-67 text-red', class: ''},
  {path: '/login', title: 'Login', icon: 'ni-key-25 text-info', class: ''},
  {path: '/register', title: 'Register', icon: 'ni-circle-08 text-pink', class: ''}*/
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  public menuItems: any[];
  public isCollapsed = true;
  private userId = '1';
  private userPosition = 'Staff';
  private userDepartment = 'Marketing';

  constructor(private router: Router) {
  }

  ngOnInit() {
    this.userId = JSON.parse(localStorage.getItem('currentUser')).id;
    this.userPosition = JSON.parse(localStorage.getItem('currentUser')).position;
    this.userDepartment = JSON.parse(localStorage.getItem('currentUser')).department;
    this.menuItems = ROUTES.filter(menuItem =>  {
      if (this.userPosition === 'Manager' && this.userDepartment === 'IT' && menuItem.path === '/audit') {
        return;
      } else if (this.userPosition === 'Manager' && this.userDepartment === 'Marketing' && menuItem.path === '/itaudit') {
        return;
      } else if (this.userPosition === 'Staff' && menuItem.path === '/audit') {
        return;
      } else if (this.userPosition === 'Staff' && menuItem.path === '/itaudit') {
        return;
      } else {
        return menuItem;
      }
    });
    this.router.events.subscribe((event) => {
      this.isCollapsed = true;
    });
  }
}
