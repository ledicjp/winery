import { Component, OnInit, Input } from '@angular/core';
import { NgRedux } from '@angular-redux/store';
import { IWineryState } from '../redux/store/winery.store';
import { WineryActions } from '../redux/actions/winery.actions';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';

@Component({
  selector: 'winery-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css'],
  animations: [
    trigger('sidebarAnimationStatus', [
      state('in', style({transform: 'translateX(0)'})),
      transition('void => *', [
        style({transform: 'translateX(100%)'}),
        animate('100ms cubic-bezier(0.86, 0, 0.07, 1)')
      ]),
      transition('* => void', [
        animate('200ms cubic-bezier(0.86, 0, 0.07, 1)', style({
          opacity: 0,
          transform: 'translateX(100%)'
        }))
      ])
    ])
  ]
})
export class SidebarComponent implements OnInit {
  // ngRedux subscription
  subscription;
  sidebarState: any;
  sidebarAnimationStatus: string;

  constructor(private $ngRedux: NgRedux<IWineryState>,
              private actions: WineryActions) {
  }

  closeSidebar() {
    this.$ngRedux.dispatch(this.actions.openSidebar({
      sidebarContents: {
        sidebarVisible: false,
        nodeId: '',
        nameTextFieldValue: ''
      }
    }));
  };

  ngOnInit() {
    this.subscription = this.$ngRedux.select(state => state.wineryState.sidebarContents)
      .subscribe(newValue => {
          this.sidebarState = newValue;
          if (newValue.sidebarVisible) { this.sidebarAnimationStatus = 'in'; }
        }
      );
  }

  minInstancesChanged() {}

  maxInstancesChanged() {}
}
