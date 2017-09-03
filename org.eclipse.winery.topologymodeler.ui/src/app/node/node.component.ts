/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Josip Ledic - initial API and implementation, Refactoring to use Redux instead
 *     Thommy Zelenik - implementation, Refactoring
 */
import {
  AfterViewInit,
  Component,
  DoCheck,
  EventEmitter,
  Input,
  IterableDiffers, NgZone,
  OnInit,
  Output,
} from '@angular/core';
import {ButtonsStateModel} from '../models/buttonsState.model';

@Component({
  selector: 'winery-node',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.css'],
})
export class NodeComponent implements OnInit, AfterViewInit {
  public items: string[] = ['Item 1', 'Item 2', 'Item 3'];
  public accordionGroupPanel = 'accordionGroupPanel';
  public customClass = 'customClass';
  connectorEndpointVisible;
  startTime;
  endTime;
  longpress = false;
  @Input() makeSelectionVisible = false;
  @Input() title: string;
  @Input() left: number;
  @Input() top: number;
  @Output() sendId: EventEmitter<string>;
  @Output() askForRepaint: EventEmitter<string>;
  @Input() nodeColor: string;
  @Input() nodeImageUrl: string;
  @Input() dragSource: string;
  @Output() addNodeToDragSelection: EventEmitter<string>;
  @Input() navbarButtonsState: ButtonsStateModel;
  @Output() setDragSource: EventEmitter<any>;
  @Output() closedEndpoint: EventEmitter<string>;
  @Output() checkFocusNode: EventEmitter<string>;

  public addItem(): void {
    this.items.push(`Items ${this.items.length + 1}`);
  }

  constructor(private zone: NgZone) {
    this.sendId = new EventEmitter();
    this.askForRepaint = new EventEmitter();
    this.addNodeToDragSelection = new EventEmitter();
    this.setDragSource = new EventEmitter();
    this.closedEndpoint = new EventEmitter();
    this.checkFocusNode = new EventEmitter();
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.sendId.emit(this.title);
  }

  private repaint($event) {
    $event.stopPropagation();
    setTimeout(() => this.askForRepaint.emit(), 1);
  }

  bindMouseMove = (ev) => {
    this.mouseMove(ev);
  }

  trackTimeOfMouseDown($event): void {
    if (!$event.ctrlKey) {
      this.startTime = new Date().getTime();
      this.makeSelectionVisible = true;
      this.checkFocusNode.emit(this.title);
      this.connectorEndpointVisible = !this.connectorEndpointVisible;
      this.zone.runOutsideAngular(() => {
        document.getElementById(this.title).addEventListener('mousemove', this.bindMouseMove);
      });
    }
  }

  mouseMove($event): void {
    this.connectorEndpointVisible = false;
  }

  trackTimeOfMouseUp($event): void {
    document.getElementById(this.title).removeEventListener('mousemove', this.bindMouseMove);
    if (!$event.ctrlKey) {
      this.endTime = new Date().getTime();
      this.testTimeDifference($event);
    }
  }

  private testTimeDifference($event): void {
    if ((this.endTime - this.startTime) < 250) {
      this.longpress = false;
    } else if (this.endTime - this.startTime >= 300) {
      this.longpress = true;
    }
  }

  makeSource($event): void {
    const dragSourceInfo = {
      dragSource: this.dragSource,
      nodeId: this.title
    };
    this.setDragSource.emit(dragSourceInfo);
  }

  showConnectorEndpoint($event): void {
    $event.stopPropagation();
    const srcElement = $event.srcElement.className;
    if (srcElement !== 'connectorLabel' && srcElement !== 'connectorBox' &&
      srcElement !== 'connectorEndpoint' && srcElement !== 'endpointContainer') {
      if ($event.ctrlKey) {
        this.addNodeToDragSelection.emit(this.title);
        this.makeSelectionVisible = !this.makeSelectionVisible;
      } else {
        if (!this.longpress) {
          // this.connectorEndpointVisible = !this.connectorEndpointVisible;
          if (this.connectorEndpointVisible === true) {
            this.closedEndpoint.emit('set no more drag sources');
          }
        }
      }
    }
  }
}
