import {Component, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {debounceTime, distinctUntilChanged, first, map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EventService} from '../../services/event.service';
import {ToastrService} from 'ngx-toastr';
import {Page} from '../../entities/Page';
import {AuditService} from '../../services/audit.service';
import {User} from '../../entities/User';

const frequencyTypes = ['Year', 'Month', 'Week', 'Day'];

@Component({
  selector: 'app-audit',
  templateUrl: './audit.component.html',
  styleUrls: ['./audit.component.scss']
})
export class AuditComponent implements OnInit {
  currentUser: User;
  events = [];
  page = new Page();
  currentPageNum = 0;
  totalPageNum = 0;
  Arr = Array;

  constructor(private modalService: NgbModal,
              private formBuilder: FormBuilder,
              private auditService: AuditService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.setPage();
  }

  setPage() {
    this.page.page = this.currentPageNum;
    this.auditService.getAllPageableForAudit(this.page).subscribe(pagedData => {
      this.page.size = pagedData.size;
      this.page.page = pagedData.page;
      this.page.totalElements = pagedData.totalElements;
      this.events = pagedData.content;
      this.totalPageNum = pagedData.totalPages;
    });
  }

  gotoPage(num: number) {
    this.currentPageNum = num;
    this.setPage();
  }

  refresh() {
    this.setPage();
  }

  prePage() {
    this.currentPageNum = this.currentPageNum - 1;
    this.setPage();
  }

  nextPage() {
    this.currentPageNum = this.currentPageNum + 1;
    this.setPage();
  }

  pass(eventId) {
    this.auditService.pass(eventId, this.currentUser.id, null).subscribe(res => {
      if (res) {
        this.toastr.success('Success');
        this.refresh();
      } else {
        this.toastr.error('Error');
      }
    });
  }
}
