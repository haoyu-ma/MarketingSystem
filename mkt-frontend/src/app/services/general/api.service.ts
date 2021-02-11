import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/internal/operators';
@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {
  }

  private httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json'
    })
  };

  private static formatError(error: any) {
    return Observable.call(environment.API_BASE_PATH + error.error);
  }

  getAll(path: string): Observable<any> {
    return this.http.get<any>(environment.API_BASE_PATH + path).pipe(catchError(ApiService.formatError));
  }

  getAllPageable(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get<any>(environment.API_BASE_PATH + path, {params}).pipe(catchError(ApiService.formatError));
  }

  getById(path: string): Observable<any> {
    return this.http.get<any>(environment.API_BASE_PATH + path).pipe(catchError(ApiService.formatError));
  }
  getByName(path: string): Observable<any> {
    return this.http.get<any>(environment.API_BASE_PATH + path).pipe(catchError(ApiService.formatError));
  }
  findAllByName(path: string): Observable<any> {
    return this.http.get<any>(environment.API_BASE_PATH + path).pipe(catchError(ApiService.formatError));
  }
  post(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.post(environment.API_BASE_PATH + path, params).pipe(catchError(ApiService.formatError));
  }

  put(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.put(environment.API_BASE_PATH + path, JSON.stringify(params), this.httpOptions).pipe(catchError(ApiService.formatError));
  }
  patch(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.patch(environment.API_BASE_PATH + path, params).pipe(catchError(ApiService.formatError));
  }

  delete(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.delete(environment.API_BASE_PATH + path).pipe(catchError(ApiService.formatError));
  }
}
