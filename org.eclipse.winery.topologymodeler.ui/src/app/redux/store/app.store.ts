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
import {combineReducers, Reducer} from 'redux';
import {
  EnhanceGridReducer,
  EnhanceGridState, INITIAL_ENHANCE_GRID_STATE,
  INITIAL_PALETTE_ITEM_STATE,
  INITIAL_PALETTE_OPENED_STATE,
  PaletteItemReducer,
  PaletteItemState,
  PaletteOpenedReducer,
  PaletteOpenedState,
} from '../reducers/palette.reducer';
import {ButtonsState, ButtonsStateReducer, INITIAL_BUTTONS_STATE} from '../reducers/topologyRenderer.reducer';

export interface AppState {
  paletteItem: PaletteItemState;
  paletteOpened: PaletteOpenedState;
  enhanceGrid: EnhanceGridState;
  buttonsState: ButtonsState;
}

export const INITIAL_APP_STATE: AppState = {
  paletteItem: INITIAL_PALETTE_ITEM_STATE,
  paletteOpened: INITIAL_PALETTE_OPENED_STATE,
  enhanceGrid: INITIAL_ENHANCE_GRID_STATE,
  buttonsState: INITIAL_BUTTONS_STATE
};

export const paletteRootReducer: Reducer<AppState> = combineReducers<AppState>({
  paletteItem: PaletteItemReducer,
  paletteOpened: PaletteOpenedReducer,
  enhanceGrid: EnhanceGridReducer,
  buttonsState: ButtonsStateReducer
});
