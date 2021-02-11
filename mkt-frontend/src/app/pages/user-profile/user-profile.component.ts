import { Component, OnInit } from '@angular/core';
import {User} from '../../entities/User';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  private currentUser: User;

  constructor() { }

  ngOnInit() {
    this.generateUser();
  }

  generateUser() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

}
