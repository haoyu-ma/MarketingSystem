import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Page} from '../entities/Page';
import {ApiService} from './general/api.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  constructor(private http: HttpClient, private apiService: ApiService) {
  }

  save(eventName: string, frequency: string, frequencyType: string, creator: string) {
    return this.http.post<any>(environment.API_BASE_PATH + '/event/save', {eventName, frequency, frequencyType, creator})
      .pipe(map(user => {
        if (user && user.token) {
          localStorage.setItem('currentUser', JSON.stringify(user));
        }
        return user;
      }));
  }

  getAllPageable(page) {
    return this.apiService.getAllPageable('/event/pagination', page).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }
    ));
  }
}
