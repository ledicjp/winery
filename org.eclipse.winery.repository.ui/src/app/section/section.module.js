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
 */
var common_1 = require("@angular/common");
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var platform_browser_1 = require("@angular/platform-browser");
var router_1 = require("@angular/router");
var ngx_pagination_1 = require("ngx-pagination");
var ng2_select_1 = require("ng2-select");
var entityContainer_component_1 = require("./entityContainer/entityContainer.component");
var wineryLoader_module_1 = require("../wineryLoader/wineryLoader.module");
var wineryNamespaceSelector_module_1 = require("../wineryNamespaceSelector/wineryNamespaceSelector.module");
var urlDecode_pipe_1 = require("../wineryPipes/urlDecode.pipe");
var urlEncode_pipe_1 = require("../wineryPipes/urlEncode.pipe");
var wineryDuplicateValidator_module_1 = require("../wineryValidators/wineryDuplicateValidator.module");
var winery_modal_module_1 = require("../wineryModalModule/winery.modal.module");
var section_component_1 = require("./section.component");
var section_pipe_1 = require("./section.pipe");
var wineryUploader_module_1 = require("../wineryUploader/wineryUploader.module");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var SectionModule = (function () {
    function SectionModule() {
    }
    return SectionModule;
}());
SectionModule = __decorate([
    core_1.NgModule({
        imports: [
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            common_1.CommonModule,
            ngx_pagination_1.NgxPaginationModule,
            wineryDuplicateValidator_module_1.WineryDuplicateValidatorModule,
            wineryNamespaceSelector_module_1.WineryNamespaceSelectorModule,
            wineryLoader_module_1.WineryLoaderModule,
            winery_modal_module_1.WineryModalModule,
            router_1.RouterModule,
            ng2_select_1.SelectModule,
            wineryUploader_module_1.WineryUploaderModule,
            ngx_bootstrap_1.TooltipModule
        ],
        exports: [section_component_1.SectionComponent],
        declarations: [
            section_component_1.SectionComponent,
            entityContainer_component_1.EntityContainerComponent,
            urlDecode_pipe_1.UrlDecodePipe,
            urlEncode_pipe_1.UrlEncodePipe,
            section_pipe_1.SectionPipe
        ],
        providers: [],
    })
], SectionModule);
exports.SectionModule = SectionModule;
//# sourceMappingURL=section.module.js.map