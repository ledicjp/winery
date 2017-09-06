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
 *     Tino Stadelmaier - initial API and implementation
 */
var core_1 = require("@angular/core");
var requirementsOrCapabilitesApiData_1 = require("./requirementsOrCapabilitesApiData");
var requirementsOrCapabilities_service_1 = require("./requirementsOrCapabilities.service");
var wineryNotification_service_1 = require("../../../../wineryNotificationModule/wineryNotification.service");
var util_1 = require("util");
var RequirementsOrCapabilitesComponent = (function () {
    function RequirementsOrCapabilitesComponent(service, notify) {
        this.service = service;
        this.notify = notify;
        this.singleItem = '';
        this.title = '';
        this.columns = [
            { title: 'Name', name: 'name', sort: true },
            { title: 'Reference', name: 'ref', sort: true },
        ];
        this.loading = true;
        this.edit = false;
        this.reqOrCapToBeAdded = new requirementsOrCapabilitesApiData_1.RequirementOrCapability();
    }
    RequirementsOrCapabilitesComponent.prototype.ngOnInit = function () {
        this.getRequirementsOrCapabilities();
    };
    // region ########## Template Callbacks ##########
    RequirementsOrCapabilitesComponent.prototype.onCellSelected = function (selectedItem) {
        this.currentSelected = selectedItem.row;
    };
    RequirementsOrCapabilitesComponent.prototype.onAddClick = function () {
        this.addOrChange = 'Add ';
        this.currentSelected = null;
        this.addModal.show();
    };
    RequirementsOrCapabilitesComponent.prototype.onEditClick = function (reqOrCap) {
        if (!util_1.isNullOrUndefined(reqOrCap)) {
            this.addOrChange = 'Change ';
            this.edit = true;
            this.reqOrCapToBeAdded.name = reqOrCap.name;
            this.reqOrCapToBeAdded.ref = reqOrCap.ref;
            this.currentSelected = reqOrCap;
            this.addModal.show();
        }
        else {
            return;
        }
    };
    RequirementsOrCapabilitesComponent.prototype.onDeleteClick = function () {
        if (!util_1.isNullOrUndefined(this.currentSelected)) {
            this.deleteReqOrCapModal.show();
        }
        else {
            return;
        }
    };
    RequirementsOrCapabilitesComponent.prototype.onRemoveClick = function (reqOrCap) {
        if (!util_1.isNullOrUndefined(reqOrCap)) {
            this.currentSelected = reqOrCap;
            this.deleteReqOrCapModal.show();
        }
        else {
            return;
        }
    };
    RequirementsOrCapabilitesComponent.prototype.cancelBtnClicked = function () {
        this.addModal.hide();
        this.edit = false;
    };
    RequirementsOrCapabilitesComponent.prototype.removeConfirmed = function () {
        this.edit = false;
        this.addModal.hide();
        this.deleteReqOrCapModal.hide();
        this.deleteReqOrCap(this.currentSelected.id);
    };
    RequirementsOrCapabilitesComponent.prototype.addConfirmed = function () {
        this.addModal.hide();
        this.addNewRequirementOrCapability();
    };
    RequirementsOrCapabilitesComponent.prototype.updateConfirmed = function () {
        this.addModal.hide();
        this.addNewRequirementOrCapability();
    };
    // endregion
    // region ########## Private Methods ##########
    RequirementsOrCapabilitesComponent.prototype.getRequirementsOrCapabilities = function () {
        var _this = this;
        this.loading = true;
        this.service.getRequirementsOrCapabilities().subscribe(function (data) { return _this.handleRequirementsOrCapabilitiesData(data); }, function (error) { return _this.handleError(error); });
    };
    RequirementsOrCapabilitesComponent.prototype.handleRequirementsOrCapabilitiesData = function (data) {
        this.requirementsOrCapabilitiesList = data;
        this.requirementsOrCapabilitiesList = this.requirementsOrCapabilitiesList.map(function (obj) {
            if (obj.ref.id === null) {
                obj.ref = '';
            }
            else {
                obj.ref = obj.ref.id;
            }
            return obj;
        });
        this.loading = false;
    };
    RequirementsOrCapabilitesComponent.prototype.handleError = function (error) {
        this.notify.error(error);
        this.loading = false;
    };
    RequirementsOrCapabilitesComponent.prototype.addNewRequirementOrCapability = function () {
        var _this = this;
        this.loading = false;
        this.service.sendPostRequest(this.reqOrCapToBeAdded).subscribe(function (data) { return _this.handlePostResponse(); }, function (error) { return _this.handleError(error); });
    };
    RequirementsOrCapabilitesComponent.prototype.handlePostResponse = function () {
        this.loading = false;
        if (this.edit) {
            this.deleteReqOrCap(this.currentSelected.id);
        }
        else {
            this.notify.success('new ' + this.singleItem + this.reqOrCapToBeAdded.name + ' added');
            this.getRequirementsOrCapabilities();
        }
    };
    RequirementsOrCapabilitesComponent.prototype.deleteReqOrCap = function (id) {
        var _this = this;
        this.service.deleteCapOrReqDef(id)
            .subscribe(function (data) { return _this.handleCapOrReqDelete(); }, function (error) { return _this.handleError(error); });
    };
    RequirementsOrCapabilitesComponent.prototype.handleCapOrReqDelete = function () {
        if (this.edit) {
            this.notify.success(this.singleItem + ' id: ' + this.currentSelected.id + ' updated');
        }
        else {
            this.notify.success(this.singleItem + ' id: ' + this.currentSelected.id + ' name: ' + this.currentSelected.name + ' deleted');
        }
        this.loading = false;
        this.currentSelected = null;
        this.edit = false;
        this.getRequirementsOrCapabilities();
    };
    return RequirementsOrCapabilitesComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", Object)
], RequirementsOrCapabilitesComponent.prototype, "singleItem", void 0);
__decorate([
    core_1.Input(),
    __metadata("design:type", Object)
], RequirementsOrCapabilitesComponent.prototype, "title", void 0);
__decorate([
    core_1.ViewChild('addReqOrCapModal'),
    __metadata("design:type", Object)
], RequirementsOrCapabilitesComponent.prototype, "addModal", void 0);
__decorate([
    core_1.ViewChild('confirmDeleteModal'),
    __metadata("design:type", Object)
], RequirementsOrCapabilitesComponent.prototype, "deleteReqOrCapModal", void 0);
RequirementsOrCapabilitesComponent = __decorate([
    core_1.Component({
        selector: 'winery-instance-requirements-or-capabilities',
        templateUrl: 'requirementsOrCapabilites.component.html',
        providers: [
            requirementsOrCapabilities_service_1.RequirementsOrCapabilitiesService
        ]
    }),
    __metadata("design:paramtypes", [requirementsOrCapabilities_service_1.RequirementsOrCapabilitiesService,
        wineryNotification_service_1.WineryNotificationService])
], RequirementsOrCapabilitesComponent);
exports.RequirementsOrCapabilitesComponent = RequirementsOrCapabilitesComponent;
//# sourceMappingURL=requirementsOrCapabilites.component.js.map