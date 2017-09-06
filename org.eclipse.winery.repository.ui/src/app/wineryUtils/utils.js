"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
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
var Utils = (function () {
    function Utils() {
    }
    /**
     * Generates a random alphanumeric string of the given length.
     *
     * @param length The length of the generated string. Defaults to 64.
     * @returns A random, alphanumeric string.
     */
    Utils.generateRandomString = function (length) {
        if (length === void 0) { length = 64; }
        var elements = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        var state = '';
        for (var iterator = 0; iterator < length; iterator++) {
            state += elements.charAt(Math.floor(Math.random() * elements.length));
        }
        return state;
    };
    return Utils;
}());
exports.Utils = Utils;
//# sourceMappingURL=utils.js.map