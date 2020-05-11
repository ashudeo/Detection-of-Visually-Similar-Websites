import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { PhishingModal } from './phishing-modal';

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http : HttpClient) { }

  postPhishingDataForm (phishingModal : PhishingModal) : Observable<any> {
     var array = this.http.post('http://localhost:8080/topics', phishingModal);
     console.log(array);
     return array;
  }
}
