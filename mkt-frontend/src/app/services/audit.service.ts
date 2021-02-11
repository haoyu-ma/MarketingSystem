import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {map} from 'rxjs/operators';
import {HttpClient} from '@angular/common/http';
import {ApiService} from './general/api.service';

@Injectable({
  providedIn: 'root'
})
export class AuditService {

  constructor(private http: HttpClient, private apiService: ApiService) {
  }

  getAllPageableForAudit(page) {
    return this.apiService.getAllPageable('/event/audit/pagination', page).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }
    ));
  }

  getAllPageableForItAudit(page) {
    return this.apiService.getAllPageable('/event/itaudit/pagination', page).pipe(map(
      res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }
    ));
  }

  pass(id, auditor, itAuditor) {
    return this.http.post<any>(environment.API_BASE_PATH + '/event/update/auditor', {id, auditor, itAuditor})
      .pipe(map(res => {
        if (res) {
          return res;
        } else {
          return {};
        }
      }));
  }
}
