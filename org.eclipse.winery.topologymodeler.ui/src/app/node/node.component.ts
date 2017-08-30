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
  IterableDiffers, KeyValueDiffers,
  OnInit,
  Output
} from '@angular/core';
import {ButtonsStateModel} from '../models/buttonsState.model';

@Component({
  selector: 'winery-node',
  templateUrl: './node.component.html',
  styleUrls: ['./node.component.css'],
})
export class NodeComponent implements OnInit, AfterViewInit, DoCheck {
  public items: string[] = ['Item 1', 'Item 2', 'Item 3'];
  public accordionGroupPanel = 'accordionGroupPanel';
  public customClass = 'customClass';
  @Input() connectorEndpointVisible;
  startTime;
  endTime;
  longpress = false;
  makeSelectionVisible = false;
  @Input() title: string;
  @Input() left: number;
  @Input() top: number;
  @Output() sendId: EventEmitter<string>;
  @Output() askForRepaint: EventEmitter<string>;
  @Input() nodeColor: string;
  @Input() nodeImageUrl: string;
  @Input() dragSource: string;
  @Output() addNodeToDragSelection: EventEmitter<string>;
  @Output() checkIfNodeInSelection: EventEmitter<string>;
  @Input() selectedNodes: string[] = [];
  @Input() navbarButtonsState: ButtonsStateModel;
  @Input() endpointContainer: any;
  @Output() setDragSource: EventEmitter<any>;
  @Output() closedEndpoint: EventEmitter<string>;
  differSelectedNodes: any;
  differDragSource: any;

  public addItem(): void {
    this.items.push(`Items ${this.items.length + 1}`);
  }

  constructor(differsSelectedNodes: IterableDiffers, differsDragSource: KeyValueDiffers) {
    this.sendId = new EventEmitter();
    this.askForRepaint = new EventEmitter();
    this.addNodeToDragSelection = new EventEmitter();
    this.checkIfNodeInSelection = new EventEmitter();
    this.setDragSource = new EventEmitter();
    this.closedEndpoint = new EventEmitter();
    this.differSelectedNodes = differsSelectedNodes.find([]).create(null);
    this.differDragSource = differsDragSource.find([]).create(null);
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    this.sendId.emit(this.title);
  }

  ngDoCheck(): void {
    const selectedNodes = this.differSelectedNodes.diff(this.selectedNodes);
    const dragSourceChanges = this.differDragSource.diff(this.endpointContainer);

    if (selectedNodes) {
      selectedNodes.forEachAddedItem(r => {
          if (this.title === r.item) {
            this.makeSelectionVisible = true;
          }
        }
      );
      selectedNodes.forEachRemovedItem(r => {
          if (this.title === r.item) {
            this.makeSelectionVisible = false;
          }
        }
      );
    } else if (dragSourceChanges){
      if (dragSourceChanges._appendAfter.currentValue === this.title) {
        this.connectorEndpointVisible = false;
        console.log(dragSourceChanges);
      }
    }
  }

  private repaint($event) {
    $event.stopPropagation();
    setTimeout(() => this.askForRepaint.emit(), 1);
  }

  trackTimeOfMouseDown(): void {
    this.startTime = new Date().getTime();
  }

  trackTimeOfMouseUp($event): void {
    this.endTime = new Date().getTime();
    this.testTimeDifference($event);
  }

  private testTimeDifference($event): void {
    if ((this.endTime - this.startTime) < 250) {
      this.longpress = false;
    } else if (this.endTime - this.startTime >= 300) {
      this.longpress = true;
      const srcElement = $event.srcElement.className;
      /*
      if (srcElement !== 'connectorLabel' && srcElement !== 'connectorBox' &&
        srcElement !== 'connectorEndpoint' && srcElement !== 'endpointContainer') {
        this.connectorEndpointVisible = false;
      }
      */
    }
  }

  makeSource($event): void {
    const dragSourceInfo = {
      open: true,
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
          this.connectorEndpointVisible = !this.connectorEndpointVisible;
          if (this.connectorEndpointVisible === true) {
            this.closedEndpoint.emit('set no more drag sources');
          }
        }
        this.checkIfNodeInSelection.emit(this.title);
      }
    }
  }
}
