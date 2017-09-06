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
var configuration_1 = require("../../configuration");
var existService_1 = require("../../wineryUtils/existService");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var router_1 = require("@angular/router");
var entityContainter_service_1 = require("./entityContainter.service");
var EntityContainerComponent = (function () {
    function EntityContainerComponent(existService, router, service) {
        this.existService = existService;
        this.router = router;
        this.service = service;
        this.deleted = new core_1.EventEmitter();
        this.editButtonToolTip = 'Edit.';
    }
    EntityContainerComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.backendLink = configuration_1.backendBaseURL + '/' + this.resourceType.toLowerCase() + 's/'
            + encodeURIComponent(encodeURIComponent(this.data.namespace)) + '/' + this.data.id;
        if (this.resourceType === 'nodeType' && this.data.id) {
            var img_1 = this.backendLink + '/visualappearance/50x50';
            this.existService.check(img_1)
                .subscribe(function () {
                _this.imageUrl = img_1;
            }, function () {
                _this.imageUrl = null;
            });
        }
        if (this.resourceType === 'serviceTemplate') {
            this.editButtonToolTip += ' Hold CTRL to directly edit the topology template.';
        }
    };
    EntityContainerComponent.prototype.onClick = function () {
        var url = '/' + this.resourceType.toLocaleLowerCase() + 's/' +
            encodeURIComponent(encodeURIComponent(encodeURIComponent(this.data.namespace)));
        if (this.data.id) {
            url += '/' + this.data.id;
        }
        this.router.navigateByUrl(url);
    };
    EntityContainerComponent.prototype.exportComponent = function (event) {
        event.stopPropagation();
        if (event.ctrlKey) {
            window.open(this.backendLink + '?definitions', '_blank');
        }
        else {
            window.open(this.backendLink + '?csar', '_blank');
        }
    };
    EntityContainerComponent.prototype.editComponent = function (event) {
        event.stopPropagation();
        if (this.resourceType === 'serviceTemplate' && event.ctrlKey) {
            var topologyModeler = configuration_1.backendBaseURL + '-topologymodeler/'
                + '?repositoryURL=' + encodeURIComponent(configuration_1.backendBaseURL)
                + '&uiURL=' + encodeURIComponent(window.location.origin)
                + '&ns=' + encodeURIComponent(this.data.namespace)
                + '&id=' + this.data.id;
            window.open(topologyModeler, '_blank');
        }
        else {
            this.router.navigateByUrl('/' + this.resourceType.toLocaleLowerCase() + 's/' +
                encodeURIComponent(encodeURIComponent(encodeURIComponent(this.data.namespace))) + '/'
                + this.data.id);
        }
    };
    EntityContainerComponent.prototype.showRemoveDialog = function (event) {
        this.confirmDeleteModal.show();
        event.stopPropagation();
    };
    EntityContainerComponent.prototype.deleteConfirmed = function () {
        this.service.deleteComponent(this.backendLink, this.data.id);
        this.deleted.emit(this.data.id);
    };
    return EntityContainerComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", Object)
], EntityContainerComponent.prototype, "data", void 0);
__decorate([
    core_1.Input(),
    __metadata("design:type", String)
], EntityContainerComponent.prototype, "resourceType", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", Object)
], EntityContainerComponent.prototype, "deleted", void 0);
__decorate([
    core_1.ViewChild('confirmDeleteModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], EntityContainerComponent.prototype, "confirmDeleteModal", void 0);
EntityContainerComponent = __decorate([
    core_1.Component({
        selector: 'winery-entity-container',
        templateUrl: './entityContainer.component.html',
        styleUrls: ['./entityContainer.component.css'],
        providers: [
            entityContainter_service_1.EntityContainterService
        ]
    }),
    __metadata("design:paramtypes", [existService_1.ExistService, router_1.Router, entityContainter_service_1.EntityContainterService])
], EntityContainerComponent);
exports.EntityContainerComponent = EntityContainerComponent;
//# sourceMappingURL=entityContainer.component.js.map