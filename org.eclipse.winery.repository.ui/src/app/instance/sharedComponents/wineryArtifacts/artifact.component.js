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
 *     Niko Stadelmaier, Tino Stadelmaier - initial API and implementation
 */
var core_1 = require("@angular/core");
var artifact_service_1 = require("./artifact.service");
var util_1 = require("util");
var wineryNotification_service_1 = require("../../../wineryNotificationModule/wineryNotification.service");
var instance_service_1 = require("../../instance.service");
var interfacesApiData_1 = require("../interfaces/interfacesApiData");
var generateArtifactApiData_1 = require("../interfaces/generateArtifactApiData");
var ngx_bootstrap_1 = require("ngx-bootstrap");
var configuration_1 = require("../../../configuration");
var artifact_files_service_1 = require("./artifact.files.service.");
var router_1 = require("@angular/router");
var WineryArtifactComponent = (function () {
    function WineryArtifactComponent(service, sharedData, notify, fileService, router) {
        this.service = service;
        this.sharedData = sharedData;
        this.notify = notify;
        this.fileService = fileService;
        this.router = router;
        this.columns = [];
        this.loading = true;
        this.newArtifact = new generateArtifactApiData_1.GenerateArtifactApiData();
        this.artifact = new GenerateData();
        this.artifactTypesList = { 'classes': null };
        this.artifactTemplatesList = { 'classes': null };
        this.selectedRadioButton = 'createArtifactTemplate';
        this.baseUrl = configuration_1.hostURL;
        this.noneSelected = true;
        this.isDeploymentArtifact = false;
        this.artifactresources = {};
        this.addClicked = new core_1.EventEmitter();
        this.removeClicked = new core_1.EventEmitter();
        this.commonColumns = [
            { title: 'Name', name: 'name' },
            { title: 'Artifact Template', name: 'artifactRefLocalName' },
            { title: 'Artifact Type', name: 'artifactTypeLocalName' },
            { title: 'Specific Content', name: 'anyText' }
        ];
        this.implementationArtifactColumns = [
            { title: 'Interface Name', name: 'interfaceName' },
            { title: 'Operation Name', name: 'operationName' }
        ];
    }
    WineryArtifactComponent.prototype.ngOnInit = function () {
        this.columns = this.columns.concat(this.commonColumns);
        this.getArtifacts();
        this.getArtifactTemplates();
        this.getArtifactTypes();
        this.newArtifact.artifactType = '';
        if (this.router.url.includes('deploymentartifacts')) {
            this.isDeploymentArtifact = true;
            this.columns.splice(1, 0, this.implementationArtifactColumns[0]);
            this.columns.splice(2, 0, this.implementationArtifactColumns[1]);
        }
        else {
            this.getInterfacesOfAssociatedType();
        }
    };
    WineryArtifactComponent.prototype.onAddClick = function () {
        if (this.sharedData.selectedNamespace.endsWith('/')) {
            this.artifact.namespace = this.sharedData.selectedNamespace.slice(0, this.sharedData.selectedNamespace.length - 1);
        }
        else {
            this.artifact.namespace = this.sharedData.selectedNamespace;
        }
        var deployment = this.isDeploymentArtifact ? 'Deployment' : '';
        this.artifact.name = this.sharedData.selectedComponentId + deployment + 'Artifact';
        this.artifact.selectedResource = 'Artifact';
        this.artifact.selectedResourceType = 'Template';
        this.addClicked.emit();
        this.addArtifactModal.show();
    };
    /**
     * handler for clicks on remove button
     * @param data
     */
    WineryArtifactComponent.prototype.onRemoveClick = function (data) {
        if (util_1.isNullOrUndefined(data)) {
            return;
        }
        else {
            this.elementToRemove = data;
            this.confirmDeleteModal.show();
        }
    };
    WineryArtifactComponent.prototype.onSelectedArtifactTypeChanged = function (value) {
        if (value === '(none)') {
            this.selectedArtifactType = '';
            this.newArtifact.artifactType = '';
            this.noneSelected = true;
        }
        else {
            this.noneSelected = false;
            this.selectedArtifactType = value;
            this.newArtifact.artifactType = value;
        }
    };
    WineryArtifactComponent.prototype.onSelectedArtifactTemplateChanged = function (value) {
        if (value === '(none)') {
            this.noneSelected = true;
            this.selectedArtifactTemplate = '';
            this.newArtifact.artifactTemplate = '';
        }
        else {
            this.noneSelected = false;
            this.selectedArtifactTemplate = value;
            this.newArtifact.artifactTemplate = value;
        }
    };
    WineryArtifactComponent.prototype.onCreateArtifactTemplateClicked = function () {
        this.newArtifact.autoCreateArtifactTemplate = 'true';
    };
    WineryArtifactComponent.prototype.cancelBtnClicked = function () {
        this.addArtifactModal.hide();
    };
    WineryArtifactComponent.prototype.addConfirmed = function () {
        if (this.selectedOperation === '(none)' || util_1.isNullOrUndefined(this.selectedOperation)) {
            this.selectedOperation = '';
            console.log('this.selectedOperation = ' + this.selectedOperation);
        }
        if (util_1.isNullOrUndefined(this.selectedInterface)) {
            console.log('selectedInterface is NULL or undefined');
            this.selectedInterface = new interfacesApiData_1.InterfacesApiData();
            this.selectedInterface.text = '';
            console.log('selectedInterface.text = ' + this.selectedInterface.text);
        }
        if (this.selectedRadioButton === 'createArtifactTemplate') {
            this.newArtifact.autoCreateArtifactTemplate = 'true';
            this.newArtifact.artifactTemplateName = this.artifact.name ? this.artifact.name : '';
            this.newArtifact.artifactTemplateNamespace = this.artifact.namespace ? this.artifact.namespace : '';
            this.makeArtifactUrl();
        }
        else if (this.selectedRadioButton === 'linkArtifactTemplate') {
            this.newArtifact.autoCreateArtifactTemplate = '';
        }
        else if (this.selectedRadioButton === 'skipArtifactTemplate') {
            this.newArtifact.autoCreateArtifactTemplate = '';
        }
        this.newArtifact.interfaceName = this.selectedInterface.text;
        this.newArtifact.operationName = this.selectedOperation;
        this.newArtifact.javaPackage = '';
        this.createNewImplementationArtifact();
        this.addArtifactModal.hide();
    };
    WineryArtifactComponent.prototype.createNewImplementationArtifact = function () {
        var _this = this;
        this.loading = true;
        this.service.createNewArtifact(this.newArtifact).subscribe(function (data) { return _this.handlePostResponse(); }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.getArtifacts = function () {
        var _this = this;
        this.service.getAllArtifacts().subscribe(function (data) {
            _this.handleData(data);
        }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.getInterfacesOfAssociatedType = function () {
        var _this = this;
        this.service.getInterfacesOfAssociatedType().subscribe(function (data) { return _this.handleInterfaceData(data); }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.getArtifactTypes = function () {
        var _this = this;
        this.service.getAllArtifactTypes().subscribe(function (data) { return _this.handleArtifactTypeData(data); }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.getArtifactTemplates = function () {
        var _this = this;
        this.service.getAllArtifactTemplates().subscribe(function (data) { return _this.handleArtifactTemplateData(data); }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.handleData = function (data) {
        var _this = this;
        this.artifactsData = data;
        this.artifactsData = this.artifactsData.map(function (obj) {
            obj.artifactTypeLocalName = _this.getLocalName(obj.artifactType);
            obj.artifactRefLocalName = _this.getLocalName(obj.artifactRef);
            if (!util_1.isNullOrUndefined(obj.any)) {
                obj.anyText = '';
            }
            if (util_1.isNullOrUndefined(obj.interfaceName)) {
                obj.interfaceName = '';
            }
            if (util_1.isNullOrUndefined(obj.operationName)) {
                obj.operationName = '';
            }
            return obj;
        });
        this.loading = false;
    };
    WineryArtifactComponent.prototype.removeConfirmed = function () {
        var _this = this;
        this.service.deleteArtifact(this.elementToRemove.name).subscribe(function (data) {
            _this.notify.success('Artifact deleted');
            _this.getArtifacts();
        }, function (error) { return _this.notify.error(error.toString()); });
    };
    WineryArtifactComponent.prototype.loadFiles = function (templateUrl) {
        var _this = this;
        this.fileService.getFiles(templateUrl)
            .subscribe(function (data) { return _this.filesList = data.files; }, function (error) { return _this.notify.error(error.toString() + 'error from loadfiles()'); });
    };
    WineryArtifactComponent.prototype.deleteFile = function (file) {
        this.fileToRemove = file;
        this.removeElementModal.show();
    };
    WineryArtifactComponent.prototype.onRemoveElement = function () {
        var _this = this;
        this.loading = true;
        this.fileService.delete(this.fileToRemove)
            .subscribe(function (data) { return _this.handleDelete(); }, function (error) { return _this.showError(error); });
    };
    WineryArtifactComponent.prototype.getLocalName = function (qName) {
        if (!util_1.isNullOrUndefined(qName)) {
            return qName.slice(qName.indexOf('}') + 1);
        }
        else {
            return '';
        }
    };
    WineryArtifactComponent.prototype.handleInterfaceData = function (data) {
        this.interfacesList = data;
    };
    WineryArtifactComponent.prototype.handleArtifactTypeData = function (data) {
        this.artifactTypesList.classes = data;
    };
    WineryArtifactComponent.prototype.handleArtifactTemplateData = function (data) {
        this.artifactTemplatesList.classes = data;
    };
    WineryArtifactComponent.prototype.handlePostResponse = function () {
        this.getArtifacts();
        this.loadFiles(this.uploadUrl);
        this.loading = false;
        if (this.selectedRadioButton === 'createArtifactTemplate') {
            this.uploadFileModal.show();
        }
    };
    WineryArtifactComponent.prototype.handleDelete = function () {
        this.notify.success('Successfully deleted ' + this.fileToRemove.name);
        this.fileToRemove = null;
        this.loadFiles(this.uploadUrl);
        this.loading = false;
    };
    WineryArtifactComponent.prototype.showError = function (error) {
        this.notify.error(error);
        this.loading = false;
    };
    WineryArtifactComponent.prototype.makeArtifactUrl = function () {
        this.artifactUrl = configuration_1.backendBaseURL + '/artifacttemplates/' + encodeURIComponent(encodeURIComponent(this.newArtifact.artifactTemplateNamespace)) + '/' + this.newArtifact.artifactTemplateName + '/';
        this.uploadUrl = this.artifactUrl + 'files/';
    };
    return WineryArtifactComponent;
}());
__decorate([
    core_1.Input(),
    __metadata("design:type", String)
], WineryArtifactComponent.prototype, "title", void 0);
__decorate([
    core_1.Input(),
    __metadata("design:type", Object)
], WineryArtifactComponent.prototype, "artifactresources", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", Object)
], WineryArtifactComponent.prototype, "addClicked", void 0);
__decorate([
    core_1.Output(),
    __metadata("design:type", Object)
], WineryArtifactComponent.prototype, "removeClicked", void 0);
__decorate([
    core_1.ViewChild('confirmDeleteModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], WineryArtifactComponent.prototype, "confirmDeleteModal", void 0);
__decorate([
    core_1.ViewChild('addArtifactModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], WineryArtifactComponent.prototype, "addArtifactModal", void 0);
__decorate([
    core_1.ViewChild('uploadFileModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], WineryArtifactComponent.prototype, "uploadFileModal", void 0);
__decorate([
    core_1.ViewChild('removeElementModal'),
    __metadata("design:type", ngx_bootstrap_1.ModalDirective)
], WineryArtifactComponent.prototype, "removeElementModal", void 0);
WineryArtifactComponent = __decorate([
    core_1.Component({
        selector: 'winery-artifact',
        templateUrl: 'artifact.component.html',
        providers: [artifact_service_1.WineryArtifactService, artifact_files_service_1.WineryArtifactFilesService]
    }),
    __metadata("design:paramtypes", [artifact_service_1.WineryArtifactService,
        instance_service_1.InstanceService,
        wineryNotification_service_1.WineryNotificationService,
        artifact_files_service_1.WineryArtifactFilesService,
        router_1.Router])
], WineryArtifactComponent);
exports.WineryArtifactComponent = WineryArtifactComponent;
var GenerateData = (function () {
    function GenerateData() {
    }
    return GenerateData;
}());
exports.GenerateData = GenerateData;
//# sourceMappingURL=artifact.component.js.map