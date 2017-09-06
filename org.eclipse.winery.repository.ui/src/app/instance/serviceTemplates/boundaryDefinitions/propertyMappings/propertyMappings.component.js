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
 *     Niko Stadelmaier - initial API and implementation
 */
var core_1 = require("@angular/core");
var propertyMappings_service_1 = require("./propertyMappings.service");
var util_1 = require("util");
var wineryNotification_service_1 = require("../../../../wineryNotificationModule/wineryNotification.service");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var forms_1 = require("@angular/forms");
var PropertyMappingsComponent = (function () {
    function PropertyMappingsComponent(service, notify) {
        this.service = service;
        this.notify = notify;
        this.loading = true;
        this.columns = [
            { title: 'Service Template Property', name: 'serviceTemplatePropertyRef', sort: true },
            { title: 'Target', name: 'targetObjectRef', sort: true },
            { title: 'Target Property', name: 'targetPropertyRef', sort: true }
        ];
        this.addOrUpdateBtnTxt = 'Add';
    }
    PropertyMappingsComponent.prototype.ngOnInit = function () {
        this.getMappings();
    };
    PropertyMappingsComponent.prototype.getMappings = function () {
        var _this = this;
        this.service.getPropertyMappings().subscribe(function (data) {
            _this.handleData(data);
        }, function (error) { return _this.notify.error(error.toString()); });
    };
    PropertyMappingsComponent.prototype.handleData = function (data) {
        this.apiData = data;
        this.apiData.propertyMappings.propertyMapping = this.apiData.propertyMappings.propertyMapping.map(function (obj) {
            if (obj.targetObjectRef === null) {
                obj.targetObjectRef = '';
            }
            else {
                obj.targetObjectRef = obj.targetObjectRef.id;
            }
            return obj;
        });
        this.loading = false;
    };
    PropertyMappingsComponent.prototype.onCellSelected = function (selectedItem) {
        this.currentSelectedItem = selectedItem.row;
    };
    PropertyMappingsComponent.prototype.removeConfirmed = function () {
        var _this = this;
        this.service.removePropertyMapping(this.currentSelectedItem.serviceTemplatePropertyRef).subscribe(function (data) { return _this.handleSuccess('Deleted property mapping'); }, function (error) { return _this.handleError(error); });
    };
    PropertyMappingsComponent.prototype.onRemoveClick = function (elementToRemove) {
        if (!util_1.isNullOrUndefined(elementToRemove) && !util_1.isNullOrUndefined(this.currentSelectedItem)) {
            this.confirmDeleteModal.show();
        }
        else {
            this.notify.warning('No Element was selected!');
        }
    };
    PropertyMappingsComponent.prototype.onAddClick = function () {
        this.addOrUpdateBtnTxt = 'Add';
        this.propertyMappingForm.reset();
        this.currentSelectedItem = null;
        this.addPropertyMappingModal.show();
    };
    PropertyMappingsComponent.prototype.onEditClick = function () {
        this.addOrUpdateBtnTxt = 'Update';
        this.addPropertyMappingModal.show();
    };
    PropertyMappingsComponent.prototype.addPropertyMapping = function (serviceTemplateProp, targetObj, targetProp) {
        var _this = this;
        this.service.addPropertyMapping({
            serviceTemplatePropertyRef: serviceTemplateProp,
            targetObjectRef: targetObj,
            targetPropertyRef: targetProp
        }).subscribe(function (data) { return _this.handleSuccess('Added new property mapping'); }, function (error) { return _this.handleError(error); });
        this.addPropertyMappingModal.hide();
    };
    PropertyMappingsComponent.prototype.handleSuccess = function (message) {
        this.getMappings();
        this.notify.success(message);
    };
    PropertyMappingsComponent.prototype.handleError = function (error) {
        this.notify.error(error.toString());
    };
    return PropertyMappingsComponent;
}());
__decorate([
    core_1.ViewChild('addPropertyMappingModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], PropertyMappingsComponent.prototype, "addPropertyMappingModal", void 0);
__decorate([
    core_1.ViewChild('confirmDeleteModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], PropertyMappingsComponent.prototype, "confirmDeleteModal", void 0);
__decorate([
    core_1.ViewChild('browseForServiceTemplatePropertyDiag'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], PropertyMappingsComponent.prototype, "browseForServiceTemplatePropertyDiag", void 0);
__decorate([
    core_1.ViewChild('propertyMappingForm'),
    __metadata("design:type", forms_1.NgForm)
], PropertyMappingsComponent.prototype, "propertyMappingForm", void 0);
PropertyMappingsComponent = __decorate([
    core_1.Component({
        selector: 'winery-instance-boundary-property-mappings',
        templateUrl: 'propertyMappings.component.html',
        providers: [propertyMappings_service_1.PropertyMappingService]
    }),
    __metadata("design:paramtypes", [propertyMappings_service_1.PropertyMappingService,
        wineryNotification_service_1.WineryNotificationService])
], PropertyMappingsComponent);
exports.PropertyMappingsComponent = PropertyMappingsComponent;
//# sourceMappingURL=propertyMappings.component.js.map