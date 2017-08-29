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
import { Action } from 'redux';
import {
  OpenSidebar,
  SaveNodeTemplateAction,
  SaveRelationshipAction,
  SendPaletteOpenedAction,
  WineryActions
} from '../actions/winery.actions';
import { TNodeTemplate, TRelationshipTemplate, TTopologyTemplate } from 'app/ttopology-template';

export interface WineryState {
  currentPaletteOpenedState: boolean;
  sidebarVisible: boolean;
  currentJsonTopology: TTopologyTemplate;
}

export const INITIAL_WINERY_STATE: WineryState = {
  currentPaletteOpenedState: false,
  sidebarVisible: false,
  currentJsonTopology: new TTopologyTemplate
};

export const WineryReducer =
  function (lastState: WineryState = INITIAL_WINERY_STATE, action: Action): WineryState {
    switch (action.type) {
      case WineryActions.SEND_PALETTE_OPENED:
        const paletteOpened: boolean = (<SendPaletteOpenedAction>action).paletteOpened;
        return {
          ...lastState,
          currentPaletteOpenedState: paletteOpened
        };
      case WineryActions.OPEN_SIDEBAR:
        const newSidebarData: any = (<OpenSidebar>action).sidebarContents;
        console.log('newSidebarData: ' + JSON.stringify(newSidebarData, null, 2));
        console.log('changedSidebarData: ' + JSON.stringify({
          ...lastState,
          sidebarContents: {
            sidebarVisible: lastState.sidebarVisible,
            nodeId: newSidebarData.nodeId,
            nameTextFieldValue: newSidebarData.nameTextFieldValue
          }}, null, 2));
        return {
          ...lastState,
          sidebarContents: {
            sidebarVisible: newSidebarData.sidebarContents.sidebarVisible,
            nodeId: newSidebarData.sidebarContents.nodeId,
            nameTextFieldValue: newSidebarData.sidebarContents.nameTextFieldValue
          }
        };
      case WineryActions.SAVE_NODE_TEMPLATE:
        const newNode: TNodeTemplate = (<SaveNodeTemplateAction>action).nodeTemplate;
        return {
          ...lastState,
          currentJsonTopology: {
            nodeTemplates: [...lastState.currentJsonTopology.nodeTemplates, newNode],
            relationshipTemplates: lastState.currentJsonTopology.relationshipTemplates
          }
        };
      case WineryActions.SAVE_RELATIONSHIP:
        const newRelationship: TRelationshipTemplate = (<SaveRelationshipAction>action).relationshipTemplate;
        return {
          ...lastState,
          currentJsonTopology: {
            nodeTemplates: lastState.currentJsonTopology.nodeTemplates,
            relationshipTemplates: [...lastState.currentJsonTopology.relationshipTemplates, newRelationship]
          }
        };
      default:
        return lastState;
    }
  };
