import {Component, OnDestroy, OnInit} from '@angular/core';
import Chart from 'chart.js';

// core components
import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2
} from '../../variables/charts';
import {DashboardService} from '../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  public datasets: any;
  public eventsChart;
  public todayChart;
  public clicked: boolean = true;
  public clicked1: boolean = false;
  private todayChartTimer;

  constructor(private dashboardService: DashboardService) { }

  ngOnInit() {
    parseOptions(Chart, chartOptions());

    const chartTodayEvents = document.getElementById('chart-events-today');
    this.todayChart = new Chart(chartTodayEvents, {type: 'bar', options: chartExample2.options, data: chartExample2.data});
    this.todayChartTimer = setInterval(() => {
      this.updateTodayChart();
    }, 2000);

    const chartEvents = document.getElementById('chart-events');
    this.eventsChart = new Chart(chartEvents, {type: 'line', options: chartExample1.options, data: chartExample1.data});
    this.updateOptions('Year');
  }

  public updateOptions(type) {
    if (type === 'Year') {
      this.eventsChart.data.labels = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
      this.dashboardService.countYearlyNumber().subscribe(res => {
        this.eventsChart.data.datasets[0].data = res;
        this.eventsChart.update();
      });
    }

    if (type === 'Month') {
      this.eventsChart.data.labels = ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24', '25', '26', '27', '28', '29', '30', '31'];
      this.dashboardService.countMonthlyNumber().subscribe(res => {
        this.eventsChart.data.datasets[0].data = res;
        this.eventsChart.update();
      });
    }
  }

  public updateTodayChart() {
    this.dashboardService.countTodayNumber().subscribe(res => {
      this.todayChart.data.datasets[0].data = res;
      this.todayChart.update();
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.todayChartTimer);
  }

}
