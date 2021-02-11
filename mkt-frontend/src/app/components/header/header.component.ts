import {Component, OnDestroy, OnInit} from '@angular/core';
import {CommonService} from '../../services/common.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  reviewNum;
  itReviewNum;
  runningNum;
  finishedNum;

  timer;

  constructor(private commonService: CommonService) {
  }

  ngOnInit() {
    this.refreshCount();
    this.timer = setInterval(() => {
      this.refreshCount();
    }, 2000);
  }

  refreshCount() {
    this.commonService.countWaitingReview().subscribe(res => {
      this.reviewNum = res;
    });
    this.commonService.countWaitingITReview().subscribe(res => {
      this.itReviewNum = res;
    });
    this.commonService.countRunning().subscribe(res => {
      this.runningNum = res;
    });
    this.commonService.countFinished().subscribe(res => {
      this.finishedNum = res;
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.timer);
  }


}
