import { Injectable } from '@angular/core';

@Injectable()
export class JsonService {
  visuals = [
    {
      'imageUrl': 'http://www.example.org/winery/test/nodetypes/' +
          'http%253A%252F%252Fwinery.opentosca.org%252Ftest%252Fnodetypes%252Ffruits/baobab/appearance/50x50',
      'color': '#89ee01',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}baobab'
    },
    {
      'color': '#89ee01',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}grape'
    },
    {
      'color': '#89ee01',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}lemon'
    },
    {
      'color': '#89ee01',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}mango'
    },
    {
      'color': '#01ace2',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}oat'
    },
    {
      'color': '#89ee01',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}orange'
    },
    {
      'color': '#cb1016',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}pasture'
    },
    {
      'color': '#6f02b4',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}plantage'
    },
    {
      'color': '#bb1c9a',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}shetland_pony'
    },
    {
      'color': '#8ac3a0',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}stall'
    },
    {
      'color': '#8b0227',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}straw'
    },
    {
      'color': '#36739e',
      'nodeTypeId': '{http://winery.opentosca.org/test/nodetypes/fruits}tree'
    },
    {
      'color': '#458ac5',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}trough'
    },
    {
      'color': '#e47c98',
      'nodeTypeId': '{http://winery.opentosca.org/test/ponyuniverse}unicorn'
    }
  ];

  testJson = {
    'nodeTemplates': [
      {
        'id': 'plantage',
      },
      {
        'id': 'tree',
      },
      {
        'id': 'tree_2',
      },
      {
        'id': 'baobab',
      },
      {
        'id': 'orange',
      },
      {
        'id': 'orange_2',
      },
      {
        'id': 'baobab_2',
      },
      {
        'id': 'baobab_3',
      }
    ],
    'relationshipTemplates': [
      {
        'sourceElement': 'baobab',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'baobab_2',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'baobab_3',
        'targetElement': 'tree'
      },
      {
        'sourceElement': 'orange',
        'targetElement': 'tree_2'
      },
      {
        'sourceElement': 'orange_2',
        'targetElement': 'tree_2'
      },
      {
        'sourceElement': 'tree_2',
        'targetElement': 'plantage'
      },
      {
        'sourceElement': 'tree',
        'targetElement': 'plantage'
      }
    ]
  };

  getRelationships(): any {
    return this.testJson.relationshipTemplates;
  }

  getNodes(): any {
    return this.testJson.nodeTemplates;
  }

  getVisuals(): any {
    return this.visuals;
  }

  constructor() {
  }

}
