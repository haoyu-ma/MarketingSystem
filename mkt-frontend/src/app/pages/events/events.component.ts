import {Component, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {debounceTime, distinctUntilChanged, first, map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {EventService} from '../../services/event.service';
import {ToastrService} from 'ngx-toastr';
import {Page} from '../../entities/Page';
import {User} from '../../entities/User';
import {Event} from '../../entities/Event';

const frequencyTypes = ['Year', 'Month', 'Week', 'Day'];

@Component({
  selector: 'app-events',
  templateUrl: './events.component.html',
  styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {
  createEventForm: FormGroup;
  currentUser: User;
  events = [];
  page = new Page();
  currentPageNum = 0;
  totalPageNum = 0;
  Arr = Array;
  viewEvent: Event = new Event();

  constructor(private modalService: NgbModal,
              private formBuilder: FormBuilder,
              private eventService: EventService,
              private toastr: ToastrService) {
  }

  ngOnInit() {
    this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
    this.setPage();
    this.createEventForm = this.formBuilder.group({
      eventName: [null, [Validators.required]],
      frequency: [null, [Validators.required]],
      frequencyType: [null, [Validators.required]]
    });
  }

  setPage() {
    this.page.page = this.currentPageNum;
    this.eventService.getAllPageable(this.page).subscribe(pagedData => {
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

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'}).result.then((result) => {
      console.log(`Closed with: ${result}`);
    }, (reason) => {
      console.log(`Dismissed ${this.getDismissReason(reason)}`);
    });
  }

  openView(content, name, frequency, frequencyType) {
    this.viewEvent.name = name;
    this.viewEvent.frequency = frequency;
    this.viewEvent.frequencyType = frequencyType;
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'lg'}).result.then((result) => {
      console.log(`Closed with: ${result}`);
    }, (reason) => {
      console.log(`Dismissed ${this.getDismissReason(reason)}`);
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 1 ? []
        : frequencyTypes.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    );

  saveEvent() {
    if (!this.createEventForm.valid) {
      return;
    }

    this.eventService.save(this.createForm.eventName.value, this.createForm.frequency.value, this.createForm.frequencyType.value, this.currentUser.id)
      .pipe(first())
      .subscribe(
        data => {
          this.toastr.success('Success');
          this.refresh();
        },
        error => {
          this.toastr.error('Error: ' + error);
        });
  }

  get createForm() {
    return this.createEventForm.controls;
  }
}
