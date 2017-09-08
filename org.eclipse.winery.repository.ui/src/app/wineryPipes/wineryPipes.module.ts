/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Lukas Harzenetter - initial API and implementation
 */
import { NgModule } from '@angular/core';
import { UrlEncodePipe } from './urlEncode.pipe';
import { UrlDecodePipe } from './urlDecode.pipe';
import { RemoveWhiteSpacesPipe } from './removeWhiteSpaces.pipe';
import { ToscaTypeToCamelCase } from './toscaTypeToCamelCase.pipe';
import { ToscaTypeToReadableNamePipe } from './toscaTypeToReadableName.pipe';

@NgModule({
    imports: [],
    exports: [
        ToscaTypeToCamelCase,
        ToscaTypeToReadableNamePipe,
        RemoveWhiteSpacesPipe,
        UrlDecodePipe,
        UrlEncodePipe
    ],
    declarations: [
        ToscaTypeToCamelCase,
        ToscaTypeToReadableNamePipe,
        RemoveWhiteSpacesPipe,
        UrlDecodePipe,
        UrlEncodePipe,
    ],
    providers: [],
})
export class WineryPipesModule {
}
