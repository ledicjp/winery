import { Component, OnInit } from '@angular/core';
import { NgRedux } from '@angular-redux/store';
import { IWineryState } from '../redux/store/winery.store';
import { WineryActions } from '../redux/actions/winery.actions';

@Component({
  selector: 'winery-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  // ngRedux subscription
  subscription;
  sidebarState: boolean;

  constructor(private $ngRedux: NgRedux<IWineryState>,
              private actions: WineryActions) {
  }

  ngOnInit() {
    this.subscription = this.$ngRedux.select(state => state.wineryState.sidebarVisible)
      .subscribe(newValue => {
          console.log(newValue);
          this.sidebarState = newValue;
        }
      );
  }

}
