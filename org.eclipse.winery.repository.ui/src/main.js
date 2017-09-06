"use strict";
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
 *     Niko Stadelmaier - add font-awesome, add ng2-toastr css
 */
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var platform_browser_dynamic_1 = require("@angular/platform-browser-dynamic");
var wineryRepository_module_1 = require("./app/wineryRepository.module");
require("ng2-toastr/ng2-toastr.css");
require("./css/bootstrap.min.css");
require("./css/wineryCommon.css");
require("./css/wineryRepository.css");
require('font-awesome/css/font-awesome.css');
if (process.env.ENV === 'production') {
    core_1.enableProdMode();
}
platform_browser_dynamic_1.platformBrowserDynamic().bootstrapModule(wineryRepository_module_1.WineryRepositoryModule);
//# sourceMappingURL=main.js.map