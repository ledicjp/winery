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
 *     Niko Stadelmaier - add admin component
 */
var core_1 = require("@angular/core");
var http_1 = require("@angular/http");
var util_1 = require("util");
var configuration_1 = require("../configuration");
var InstanceService = (function () {
    function InstanceService(http) {
        this.http = http;
        this.topologyTemplate = null;
    }
    /**
     * Get the submenu for the given resource type for displaying a component instance.
     *
     * @param type specifies the resource type for this particular instance.
     * @returns string[] containing all menus for each resource type.
     */
    InstanceService.prototype.getSubMenuByResource = function (type) {
        if (util_1.isNullOrUndefined(type)) {
            type = this.selectedResource;
        }
        var subMenu;
        switch (type.toLowerCase()) {
            case 'nodetype':
                subMenu = ['Visual Appearance', 'Instance States', 'Interfaces', 'Implementations',
                    'Requirement Definitions', 'Capability Definitions', 'Properties Definition',
                    'Inheritance', 'Documentation', 'XML'];
                break;
            case 'servicetemplate':
                subMenu = ['Topology Template', 'Plans', 'Selfservice Portal',
                    'Boundary Definitions', 'Tags', 'Documentation', 'XML'];
                break;
            case 'relationshiptype':
                subMenu = ['Visual Appearance', 'Instance States', 'Source Interfaces', 'Target Interfaces',
                    'Valid Sources and Targets', 'Implementations', 'Properties Definition',
                    'Inheritance', 'Documentation', 'XML'];
                break;
            case 'artifacttype':
                subMenu = ['Properties Definition', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'artifacttemplate':
                subMenu = ['Files', 'Properties', 'Documentation', 'XML'];
                break;
            case 'requirementtype':
                subMenu = ['Required Capability Type', 'Properties Definition', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'capabilitytype':
                subMenu = ['Properties Definition', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'nodetypeimplementation':
                subMenu = ['Implementation Artifacts', 'Deployment Artifacts', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'relationshiptypeimplementation':
                subMenu = ['Implementation Artifacts', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'policytype':
                subMenu = ['Language', 'Applies To', 'Properties Definition', 'Inheritance', 'Documentation', 'XML'];
                break;
            case 'policytemplate':
                subMenu = ['Properties', 'Documentation', 'XML'];
                break;
            case 'xsdimport':
                subMenu = [''];
                break;
            case 'wsdlimport':
                subMenu = [''];
                break;
            case 'admin':
                subMenu = ['Namespaces', 'Repository', 'Plan Languages', 'Plan Types', 'Constraint Types', 'Log'];
                break;
            default:
                subMenu = [''];
        }
        return subMenu;
    };
    /**
     * Set the shared data for the children. The path to the actual component is also generated.
     *
     * @param selectedResource
     * @param selectedNamespace
     * @param selectedComponentId
     */
    InstanceService.prototype.setSharedData = function (selectedResource, selectedNamespace, selectedComponentId) {
        var _this = this;
        this.selectedNamespace = selectedNamespace;
        this.selectedComponentId = selectedComponentId;
        this.selectedResource = selectedResource;
        // In order to have always the base path of this instance, create the path here
        // instead of getting it from the router, because there might be some child routes included.
        this.path = '/' + this.selectedResource.toLowerCase() + 's/'
            + encodeURIComponent(encodeURIComponent(this.selectedNamespace)) + '/'
            + this.selectedComponentId;
        if (this.selectedResource === 'serviceTemplate') {
            this.getTopologyTemplate()
                .subscribe(function (data) { return _this.topologyTemplate = data; }, function (error) { return _this.topologyTemplate = null; });
        }
    };
    InstanceService.prototype.deleteComponent = function () {
        return this.http.delete(configuration_1.backendBaseURL + this.path + '/');
    };
    InstanceService.prototype.getComponentData = function () {
        var headers = new http_1.Headers({ 'Content-Type': 'application/xml' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http.get(configuration_1.backendBaseURL + this.path + '/', options)
            .map(function (res) { return res.json(); });
    };
    InstanceService.prototype.getTopologyTemplate = function () {
        var headers = new http_1.Headers({ 'Content-Type': 'application/json' });
        var options = new http_1.RequestOptions({ headers: headers });
        return this.http.get(configuration_1.backendBaseURL + this.path + '/topologytemplate/', options)
            .map(function (res) { return res.json(); });
    };
    return InstanceService;
}());
InstanceService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http])
], InstanceService);
exports.InstanceService = InstanceService;
//# sourceMappingURL=instance.service.js.map