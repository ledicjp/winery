/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Thommy Zelenik - initial API and implementation
 */
import { Action, ActionCreator } from 'redux';
import { Injectable } from '@angular/core';
import { TNodeTemplate, TRelationshipTemplate } from '../../ttopology-template';

export interface SendPaletteOpenedAction extends Action {
  paletteOpened: boolean;
}

export interface SidebarStateAction extends Action {
  sidebarContents: {
    sidebarVisible: boolean,
    nodeId: string,
    nameTextFieldValue: string
  };
}

export interface SidebarNodeNamechange extends Action {
  nodeNames: {
    newNodeName: string,
    oldNodeName: string
  };
}

export interface SaveNodeTemplateAction extends Action {
  nodeTemplate: TNodeTemplate;
}

export interface SaveRelationshipAction extends Action {
  relationshipTemplate: TRelationshipTemplate;
}

export interface DeleteNodeAction extends Action {
  nodeTemplateId: string;
}

@Injectable()
export class WineryActions {
    static SEND_PALETTE_OPENED = 'SEND_PALETTE_OPENED';
    static SAVE_NODE_TEMPLATE = 'SAVE_NODE_TEMPLATE';
    static SAVE_RELATIONSHIP = 'SAVE_RELATIONSHIP';
    static DELETE_NODE_TEMPLATE = 'DELETE_NODE_TEMPLATE';
    static CHANGE_NODE_NAME = 'CHANGE_NODE_NAME';
    static OPEN_SIDEBAR = 'OPEN_SIDEBAR';

    sendPaletteOpened: ActionCreator<SendPaletteOpenedAction> =
      ((paletteOpened) => ({
        type: WineryActions.SEND_PALETTE_OPENED,
        paletteOpened: paletteOpened
      }));
    openSidebar: ActionCreator<SidebarStateAction> =
      ((newSidebarData) => ({
        type: WineryActions.OPEN_SIDEBAR,
        sidebarContents: newSidebarData.sidebarContents
      }));
    changeNodeName: ActionCreator<SidebarNodeNamechange> =
      ((nodeNames) => ({
        type: WineryActions.CHANGE_NODE_NAME,
        nodeNames: nodeNames.nodeNames
      }));
    saveNodeTemplate: ActionCreator<SaveNodeTemplateAction> =
      ((newNode) => ({
        type: WineryActions.SAVE_NODE_TEMPLATE,
        nodeTemplate: newNode
      }));
    saveRelationship: ActionCreator<SaveRelationshipAction> =
      ((newRelationship) => ({
        type: WineryActions.SAVE_RELATIONSHIP,
        relationshipTemplate: newRelationship
      }));
    deleteNodeTemplate: ActionCreator<DeleteNodeAction> =
      ((deletedNodeId) => ({
        type: WineryActions.DELETE_NODE_TEMPLATE,
        nodeTemplateId: deletedNodeId
      }));
}
