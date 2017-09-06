"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var AppComponent = (function () {
    function AppComponent() {
        this.title = 'Tour of Heroes';
        this.hero = 'Windstorm';
    }
    return AppComponent;
}());
AppComponent = __decorate([
    core_1.Component({
        selector: 'topbar',
        template: "<div id=\"topbar\">\n\t\t            <button class=\"btn btn-success topbutton\" id=\"saveBtn\">Save</button>\n                <div class=\"btn-group\">\n                  <button class=\"btn btn-default\" onclick=\"doLayout();\">Layout</button>\n                  <button class=\"btn btn-default\" onclick=\"horizontalAlignment();\">Align-h (|)</button>\n                  <button class=\"btn btn-default\" onclick=\"verticalAlignment();\">Align-v (-)</button>\n                </div>\n\n                <div class=\"btn-group\" id=\"toggleButtons\">\n                  <button class=\"btn btn-default\" id=\"toggleIdVisibility\">Ids</button>\n                  <button class=\"btn active\" id=\"toggleTypeVisibility\">Types</button>\n                  <button class=\"btn btn-default\" id=\"togglePropertiesVisibility\">Properties</button>\n                  <button class=\"btn btn-default\" id=\"toggleDeploymentArtifactsVisibility\">Deployment Artifacts</button>\n                  <button class=\"btn btn-default\" id=\"toggleReqCapsVisibility\">Requirements &amp; Capabilities</button>\n                  <button class=\"btn btn-default\" id=\"PoliciesVisibility\">Policies</button>\n                  <button class=\"btn btn-default\" id=\"TargetLocationsVisibility\">Target Locations</button>\n                </div>\n                \n\t\t            <button data-toggle=\"button\" class=\"btn btn-default\">Print View</button>\n\n\t\t            <button class=\"btn btn-default\" id=\"splitBtn\">Split</button>\n\n\t\t            <button class=\"btn btn-default topbutton\" id=\"importBtn\">Import Topology</button>\n\n                <div class=\"btn-group\">\n                  <button type=\"button\" class=\"btn btn-default dropdown-toggle\">Other <span class=\"caret\"></span></button>\n            \n                  <ul class=\"dropdown-menu\" role=\"menu\">\n                    <li><a href=\"#\">Complete Topology</a></li>\n                    <li><a id=\"exportCSARbtn\" href=\"http://dev.winery.opentosca.org/winery/servicetemplates/http%253A%252F%252Fopentosca.org%252Fservicetemplates/FIWARE-Orion_Bare_Docker/topologytemplate/../?csar\" target=\"_blank\" data-original-title=\"\" title=\"\">Export CSAR</a></li>\n                    <li><a href=\"#\">about</a></li>\n                  </ul>\n                  \n                </div>\n            </div>\n            ",
    })
], AppComponent);
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map