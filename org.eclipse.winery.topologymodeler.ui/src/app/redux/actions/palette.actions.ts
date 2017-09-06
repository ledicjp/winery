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
import {Action, ActionCreator} from 'redux';
import {Injectable} from '@angular/core';

export interface CreatePaletteItemAction extends Action {
  paletteItem: PaletteItemModel;
}

export interface SendPaletteOpenedAction extends Action {
  paletteOpened: boolean;
}

export interface EnhanceGridAction extends Action {
  enhanceGrid: boolean;
}

@Injectable()
export class PaletteActions {
    static CREATE_PALETTEITEM = 'CREATE_PALETTEITEM';
    static SEND_PALETTEOPENED = 'SEND_PALETTEOPENED';
    static ENHANCE_GRID = 'ENHANCE_GRID';

    createPaletteItem: ActionCreator<CreatePaletteItemAction> =
      (paletteItem) => ({
        type: PaletteActions.CREATE_PALETTEITEM,
        paletteItem: paletteItem
      })
    sendPaletteOpened: ActionCreator<SendPaletteOpenedAction> =
      (paletteOpened) => ({
        type: PaletteActions.SEND_PALETTEOPENED,
        paletteOpened: paletteOpened
      })
    enhanceGrid: ActionCreator<EnhanceGridAction> =
      (enhanceGrid) => ({
        type: PaletteActions.ENHANCE_GRID,
        enhanceGrid: enhanceGrid
      })
}
