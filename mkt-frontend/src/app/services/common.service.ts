import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {Page} from '../entities/Page';
import {ApiService} from './general/api.service';

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private http: HttpClient) {
  }

  countWaitingReview() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/count/audit')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }

  countWaitingITReview() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/count/itaudit')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }

  countRunning() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/count/running')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }

  countFinished() {
    return this.http.get<any>(environment.API_BASE_PATH + '/event/count/finished')
      .pipe(map(result => {
        if (result) {
          return result;
        } else {
          return {};
        }
      }));
  }
}
