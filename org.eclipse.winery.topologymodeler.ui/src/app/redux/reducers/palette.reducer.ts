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
import {PaletteItemModel} from '../../models/paletteItem.model';
import {Action} from 'redux';
import {
  CreatePaletteItemAction, EnhanceGridAction,
  PaletteActions,
  SendPaletteOpenedAction,
} from '../actions/palette.actions';

export interface PaletteItemState {
  currentPaletteItemState: PaletteItemModel;
}

export const INITIAL_PALETTE_ITEM_STATE: PaletteItemState = {
  currentPaletteItemState: null
};

export interface PaletteOpenedState {
  currentPaletteOpenedState: boolean;
}

export const INITIAL_PALETTE_OPENED_STATE: PaletteOpenedState = {
  currentPaletteOpenedState: false
};

export interface EnhanceGridState {
  currentEnhanceGridState: boolean;
}

export const INITIAL_ENHANCE_GRID_STATE: EnhanceGridState = {
  currentEnhanceGridState: false
};

export const PaletteItemReducer =
  function (lastState: PaletteItemState = INITIAL_PALETTE_ITEM_STATE, action: Action): PaletteItemState {
    switch (action.type) {
      case PaletteActions.CREATE_PALETTEITEM:
        const paletteItem: PaletteItemModel = (<CreatePaletteItemAction>action).paletteItem;
        return {
          currentPaletteItemState: paletteItem
        };
      default:
        return lastState;
    }
  };

export const PaletteOpenedReducer =
  function (lastState: PaletteOpenedState = INITIAL_PALETTE_OPENED_STATE, action: Action): PaletteOpenedState {
    switch (action.type) {
      case PaletteActions.SEND_PALETTEOPENED:
        const paletteOpened: boolean = (<SendPaletteOpenedAction>action).paletteOpened;
        return {
          currentPaletteOpenedState: paletteOpened
    };
      default:
        return lastState;
    }
  };

export const EnhanceGridReducer =
  function (lastState: EnhanceGridState = INITIAL_ENHANCE_GRID_STATE, action: Action): EnhanceGridState {
    switch (action.type) {
      case PaletteActions.ENHANCE_GRID:
        const enhanceGrid: boolean = (<EnhanceGridAction>action).enhanceGrid;
        return {
          currentEnhanceGridState: enhanceGrid
        };
      default:
        return lastState;
    }
  };
