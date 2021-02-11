import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  constructor(private http: HttpClient) {
  }

  countYearlyNumber() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/number/yearly')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }

  countMonthlyNumber() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/number/monthly')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }

  countTodayNumber() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/number/today')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }
}
