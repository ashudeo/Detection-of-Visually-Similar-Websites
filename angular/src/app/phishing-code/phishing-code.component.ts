import { Component, OnInit } from '@angular/core';
import { PhishingModal } from '../data/phishing-modal';
import { NgForm ,NgModel } from '@angular/forms';
import { DataService } from '../data/data.service';

@Component({
  selector: 'app-phishing-code',
  templateUrl: './phishing-code.component.html',
  styleUrls: ['./phishing-code.component.css']
})
export class PhishingCodeComponent implements OnInit {

  phishingModal : PhishingModal = {
    url:null
  }

  isMessageReceived = false;
  messageData = "";

  constructor(private dataService : DataService) { }

  ngOnInit() {
  }

  onBlur(field : NgModel){
    console.log('In onBlur : ', field.valid);
  }

  onSubmit(form : NgForm) {
    console.log('In onSubmit : ', form.valid);
    console.log('In onSubmit : ', form.value);
    if(form.valid){
        this.dataService.postPhishingDataForm(this.phishingModal).subscribe (
          result => {
            console.log("Success :",result)
            this.isMessageReceived = true;
            this.messageData = result
          },
          error => console.log("error :",error)
        );
    }
  }
}
