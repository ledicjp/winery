import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {TRelationshipTemplate} from '../trelationship-template';

@Component({
  selector: 'app-relationship',
  templateUrl: './relationship.component.html',
  styleUrls: ['./relationship.component.css']
})
export class RelationshipComponent implements OnInit, AfterViewInit {

  @Input() relationship: TRelationshipTemplate;
  constructor() { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
  }


}
