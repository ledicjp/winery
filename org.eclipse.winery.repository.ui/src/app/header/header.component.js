"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
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
var core_1 = require("@angular/core");
var router_1 = require("@angular/router");
var configuration_1 = require("../configuration");
var util_1 = require("util");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var HeaderComponent = (function () {
    function HeaderComponent(router) {
        this.router = router;
        this.selectedOtherComponent = '';
    }
    HeaderComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.router.events.subscribe(function (data) {
            if (!(data instanceof router_1.NavigationEnd)) {
                return;
            }
            var others = data.url.slice(1);
            if (others.includes('/')) {
                others = others.split('/')[0];
            }
            if (!util_1.isNullOrUndefined(configuration_1.sections[others]) && !(others.includes('servicetemplates') ||
                others.includes('nodetypes') ||
                others.includes('relationshiptypes') ||
                others.includes('other') ||
                others.includes('admin'))) {
                _this.selectedOtherComponent = ': '
                    + configuration_1.sections[others]
                        .replace(/([A-Z])/g, ' $1')
                        .replace(/^./, function (str) { return str.toUpperCase(); })
                    + 's';
            }
            else {
                _this.selectedOtherComponent = '';
            }
        });
    };
    return HeaderComponent;
}());
__decorate([
    core_1.ViewChild('aboutModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], HeaderComponent.prototype, "aboutModal", void 0);
HeaderComponent = __decorate([
    core_1.Component({
        selector: 'winery-header',
        templateUrl: './header.component.html',
        styleUrls: ['./header.style.css']
    }),
    __metadata("design:paramtypes", [router_1.Router])
], HeaderComponent);
exports.HeaderComponent = HeaderComponent;
//# sourceMappingURL=header.component.js.map