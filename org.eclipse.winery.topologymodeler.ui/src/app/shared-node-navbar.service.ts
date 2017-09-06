import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class SharedNodeNavbarService {

  /**
   * Navbar Button States
   * @type {Subject<any>}
   * @private
   */
  private _buttonStates = new Subject<any>();
  /**
   *  Title of Palette Item Instance
   * @type {string}
   */
  private _paletteItem = new Subject<any>();
  // Create Observable
  buttonStates$ = this._buttonStates.asObservable();
  paletteItem$ = this._paletteItem.asObservable();

  constructor() { }

  // Service message commands
  publishButtonState(buttonID: string, state: boolean) {
    this._buttonStates.next({
      buttonID: buttonID,
      state: state
    });
  }

  publishPaletteItemTitle(title: string, mousePositionX: number, mousePositionY: number) {
    this._paletteItem.next({
      title: title,
      mousePositionX: mousePositionX.toString().concat('px'),
      mousePositionY: mousePositionY.toString().concat('px')
    });
  }
}
