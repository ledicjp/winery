"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
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
 *     Niko Stadelmaier - add notifications module
 */
var common_1 = require("@angular/common");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var platform_browser_1 = require("@angular/platform-browser");
var ng2_toastr_1 = require("ng2-toastr/ng2-toastr");
var notFound_component_1 = require("./404/notFound.component");
var header_component_1 = require("./header/header.component");
var instance_module_1 = require("./instance/instance.module");
var wineryLoader_module_1 = require("./wineryLoader/wineryLoader.module");
var wineryNotification_module_1 = require("./wineryNotificationModule/wineryNotification.module");
var wineryNotificationOptions_1 = require("./wineryNotificationModule/wineryNotificationOptions");
var other_component_1 = require("./other/other.component");
var section_module_1 = require("./section/section.module");
var animations_1 = require("@angular/platform-browser/animations");
var winery_modal_module_1 = require("./wineryModalModule/winery.modal.module");
var wineryRepository_component_1 = require("./wineryRepository.component");
var wineryRepositoryRouting_module_1 = require("./wineryRepositoryRouting.module");
var existService_1 = require("./wineryUtils/existService");
var wineryOAuth_component_1 = require("./header/wineryOauth/wineryOAuth.component");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var WineryRepositoryModule = (function () {
    function WineryRepositoryModule() {
    }
    return WineryRepositoryModule;
}());
WineryRepositoryModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            common_1.CommonModule,
            animations_1.BrowserAnimationsModule,
            instance_module_1.InstanceModule,
            wineryLoader_module_1.WineryLoaderModule,
            winery_modal_module_1.WineryModalModule,
            ng2_toastr_1.ToastModule.forRoot(),
            wineryNotification_module_1.WineryNotificationModule.forRoot(),
            section_module_1.SectionModule,
            winery_modal_module_1.WineryModalModule,
            wineryRepositoryRouting_module_1.WineryRepositoryRoutingModule,
            ngx_bootstrap_1.TooltipModule.forRoot(),
        ],
        declarations: [
            header_component_1.HeaderComponent,
            notFound_component_1.NotFoundComponent,
            other_component_1.OtherComponent,
            wineryRepository_component_1.WineryRepositoryComponent,
            wineryOAuth_component_1.WineryOAuthComponent,
        ],
        providers: [
            { provide: ng2_toastr_1.ToastOptions, useClass: wineryNotificationOptions_1.WineryCustomOption },
            existService_1.ExistService
        ],
        bootstrap: [wineryRepository_component_1.WineryRepositoryComponent]
    })
], WineryRepositoryModule);
exports.WineryRepositoryModule = WineryRepositoryModule;
//# sourceMappingURL=wineryRepository.module.js.map