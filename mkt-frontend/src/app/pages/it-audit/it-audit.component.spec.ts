import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ItAuditComponent } from './it-audit.component';

describe('TablesComponent', () => {
  let component: ItAuditComponent;
  let fixture: ComponentFixture<ItAuditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ItAuditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ItAuditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
