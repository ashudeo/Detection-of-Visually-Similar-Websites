import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PhishingCodeComponent } from './phishing-code.component';

describe('PhishingCodeComponent', () => {
  let component: PhishingCodeComponent;
  let fixture: ComponentFixture<PhishingCodeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PhishingCodeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PhishingCodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
