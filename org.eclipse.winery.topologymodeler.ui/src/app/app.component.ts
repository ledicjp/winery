import {Component, Inject, OnInit} from '@angular/core';

@Component({
  selector: 'app-topologyrenderer',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  topologyTemplate: any;
  visuals: any;

  testJson = {
    documentation: [],
    any: [],
    otherAttributes: {},
    nodeTemplates: [
      {
        documentation: [],
        any: [],
        otherAttributes: {
          location: 'undefined',
          x: 600,
          y: 49
        },
        id: 'plantage',
        type: '{http://winery.opentosca.org/test/nodetypes/fruits}plantage',
        name: 'plantage',
        minInstances: 1,
        maxInstances: 1
      },
      {
        documentation: [],
        any: [],
        otherAttributes: {
          location: 'undefined',
          x: 600,
          y: 267
        },
        id: 'tree',
        type: '{http://winery.opentosca.org/test/nodetypes/fruits}tree',
        name: 'tree',
        minInstances: 1,
        maxInstances: 1
      },
      {
        documentation: [],
        any: [],
        otherAttributes: {
          location: 'undefined',
          x: 600,
          y: 785
        },
        id: 'baobab',
        type: '{http://winery.opentosca.org/test/nodetypes/fruits}baobab',
        name: 'baobab',
        minInstances: 1,
        maxInstances: 1
      },
      {
        documentation: [],
        any: [],
        otherAttributes: {
          location: 'undefined',
          x: 958,
          y: 794
        },
        id: 'banana',
        type: '{http://winery.opentosca.org/test/nodetypes/fruits}banana',
        name: 'banana',
        minInstances: 1,
        maxInstances: 1
      },
      {
        documentation: [],
        any: [],
        otherAttributes: {
          location: 'undefined',
          x: 214,
          y: 764
        },
        id: 'mango',
        type: '{http://winery.opentosca.org/test/nodetypes/fruits}mango',
        name: 'mango',
        minInstances: 1,
        maxInstances: 1
      }
    ],
    relationshipTemplates: [
      {
        'sourceElement': 'baobab',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'banana',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'mango',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'banana',
        'targetElement': 'mango'
      },
      {
        'sourceElement': 'baobab',
        'targetElement': 'plantage'
      }
    ]
  };

  testVisuals = [
    {
      imageUrl: 'http://www.example.org/winery/test/nodetypes/' +
      'http%253A%252F%252Fwinery.opentosca.org%252Ftest%252Fnodetypes%252Ffruits/baobab/appearance/50x50',
      color: '#89ee01',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}baobab',
      localName: ''
    },
    {
      color: '#89ee01',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}grape',
      localName: ''
    },
    {
      color: '#89ee01',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}lemon',
      localName: ''
    },
    {
      color: '#89ee01',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}mango',
      localName: ''
    },
    {
      color: '#01ace2',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}oat',
      localName: ''
    },
    {
      color: '#FF7F50',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}orange',
      localName: ''
    },
    {
      color: '#cb1016',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}pasture',
      localName: ''
    },
    {
      color: '#6f02b4',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}plantage',
      localName: ''
    },
    {
      color: '#bb1c9a',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}shetland_pony',
      localName: ''
    },
    {
      color: '#8ac3a0',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}stall',
      localName: ''
    },
    {
      color: '#8b0227',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}straw',
      localName: ''
    },
    {
      color: '#36739e',
      nodeTypeId: '{http://winery.opentosca.org/test/nodetypes/fruits}tree',
      localName: ''
    },
    {
      color: '#458ac5',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}trough',
      localName: ''
    },
    {
      color: '#e47c98',
      nodeTypeId: '{http://winery.opentosca.org/test/ponyuniverse}banana',
      localName: ''
    }
  ];

  ngOnInit() {
    this.topologyTemplate = this.testJson;
    this.visuals = this.testVisuals;
  }
}
