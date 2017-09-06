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
var http_1 = require("@angular/http");
var instance_service_1 = require("../../instance.service");
var configuration_1 = require("../../../configuration");
var PropertiesService = (function () {
    function PropertiesService(http, sharedData) {
        this.http = http;
        this.sharedData = sharedData;
        this.path = configuration_1.backendBaseURL + this.sharedData.path + '/properties/';
    }
    /**
     * We use `any` as return value because the backend delivers the json object containing the property as a key
     * and the value the value. Example: { "property": "this is my property" }.
     */
    PropertiesService.prototype.getProperties = function () {
        return this.http.get(this.path)
            .map(function (res) { return res.json(); });
    };
    PropertiesService.prototype.saveProperties = function (properties) {
        var headers = new http_1.Headers({ 'Content-Type': 'application/xml' });
        var options = new http_1.RequestOptions({ headers: headers });
        /* because the backend doesn't support saving json requests yet, we need to construct an xml file and
         * put it to the server
         */
        var keys = Object.keys(properties);
        var xml = '<?xml version="1.0" encoding="utf-8" ?>\n'
            + '<Properties xmlns="http://docs.oasis-open.org/tosca/ns/2011/12">\n\t'
            + '<Properties xmlns="http://opentosca.org/artifacttypes/propertiesdefinition/winery">\n\t';
        for (var _i = 0, keys_1 = keys; _i < keys_1.length; _i++) {
            var key = keys_1[_i];
            xml += '\t<' + key + '>';
            if (properties[key]) {
                xml += '<![CDATA[' + properties[key] + ']]>';
            }
            xml += '</' + key + '>\n\t';
        }
        xml += '</Properties>\n</Properties>';
        return this.http.put(this.path, xml, options);
    };
    return PropertiesService;
}());
PropertiesService = __decorate([
    core_1.Injectable(),
    __metadata("design:paramtypes", [http_1.Http,
        instance_service_1.InstanceService])
], PropertiesService);
exports.PropertiesService = PropertiesService;
//# sourceMappingURL=properties.service.js.map