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
 *     Niko Stadelmaier - add admin component
 */
var core_1 = require("@angular/core");
var forms_1 = require("@angular/forms");
var http_1 = require("@angular/http");
var platform_browser_1 = require("@angular/platform-browser");
var ng2_select_1 = require("ng2-select");
var instance_component_1 = require("./instance.component");
var instanceHeader_component_1 = require("./instanceHeader/instanceHeader.component");
var instanceRouter_module_1 = require("./instanceRouter.module");
var documentation_component_1 = require("./sharedComponents/documentation/documentation.component");
var inheritance_component_1 = require("./sharedComponents/inheritance/inheritance.component");
var wineryLoader_module_1 = require("../wineryLoader/wineryLoader.module");
var wineryNamespaceSelector_module_1 = require("../wineryNamespaceSelector/wineryNamespaceSelector.module");
var removeWhiteSpaces_pipe_1 = require("../wineryPipes/removeWhiteSpaces.pipe");
var wineryDuplicateValidator_module_1 = require("../wineryValidators/wineryDuplicateValidator.module");
var winery_modal_module_1 = require("../wineryModalModule/winery.modal.module");
var interfaces_module_1 = require("./sharedComponents/interfaces/interfaces.module");
var visualAppearance_module_1 = require("./sharedComponents/visualAppearance/visualAppearance.module");
var wineryTable_module_1 = require("../wineryTableModule/wineryTable.module");
var appliesTo_component_1 = require("./policyTypes/appliesTo/appliesTo.component");
var language_component_1 = require("./policyTypes/language/language.component");
var topologyTemplate_component_1 = require("./serviceTemplates/topologyTemplate/topologyTemplate.component");
var plans_component_1 = require("./serviceTemplates/plans/plans.component");
var boundaryDefinitions_module_1 = require("./serviceTemplates/boundaryDefinitions/boundaryDefinitions.module");
var validSourcesAndTargets_component_1 = require("./relationshipTypes/validSourcesAndTargets/validSourcesAndTargets.component");
var files_component_1 = require("./artifactTemplates/filesTag/files.component");
var requiredCapabilityType_component_1 = require("./requirementTypes/requiredCapabilityType/requiredCapabilityType.component");
var implementations_module_1 = require("./sharedComponents/implementations/implementations.module");
var instanceStates_component_1 = require("./sharedComponents/instanceStates/instanceStates.component");
var logger_component_1 = require("./admin/logger/logger.component");
var namespaces_component_1 = require("./admin/namespaces/namespaces.component");
var properties_component_1 = require("./sharedComponents/properties/properties.component");
var propertiesDefinition_module_1 = require("./sharedComponents/propertiesDefinition/propertiesDefinition.module");
var repository_module_1 = require("./admin/repository/repository.module");
var tag_module_1 = require("./serviceTemplates/tag/tag.module");
var typeWithShortName_component_1 = require("./admin/typesWithShortName/typeWithShortName.component");
var winerySpinnerWithInfinity_module_1 = require("../winerySpinnerWithInfinityModule/winerySpinnerWithInfinity.module");
var wineryEditor_module_1 = require("../wineryEditorModul/wineryEditor.module");
var capOrReqDef_component_1 = require("./nodeTypes/capabilityOrRequirementDefinitions/capOrReqDef.component");
var selfServicePortal_module_1 = require("./serviceTemplates/selfServicePortal/selfServicePortal.module");
var wineryUploader_module_1 = require("../wineryUploader/wineryUploader.module");
var wineryIoParameters_module_1 = require("../wineryIoParameter/wineryIoParameters.module");
var wineryQNameSelector_module_1 = require("../wineryQNameSelector/wineryQNameSelector.module");
var artifact_module_1 = require("./sharedComponents/wineryArtifacts/artifact.module");
var editXML_module_1 = require("./sharedComponents/editXML/editXML.module");
var InstanceModule = (function () {
    function InstanceModule() {
    }
    return InstanceModule;
}());
InstanceModule = __decorate([
    core_1.NgModule({
        imports: [
            http_1.HttpModule,
            ng2_select_1.SelectModule,
            platform_browser_1.BrowserModule,
            forms_1.FormsModule,
            wineryLoader_module_1.WineryLoaderModule,
            propertiesDefinition_module_1.PropertiesDefinitionModule,
            selfServicePortal_module_1.SelfServicePortalModule,
            instanceRouter_module_1.InstanceRouterModule,
            winerySpinnerWithInfinity_module_1.SpinnerWithInfinityModule,
            winery_modal_module_1.WineryModalModule,
            interfaces_module_1.InterfacesModule,
            wineryEditor_module_1.WineryEditorModule,
            visualAppearance_module_1.VisualAppearanceModule,
            implementations_module_1.ImplementationsModule,
            wineryTable_module_1.WineryTableModule,
            wineryDuplicateValidator_module_1.WineryDuplicateValidatorModule,
            wineryNamespaceSelector_module_1.WineryNamespaceSelectorModule,
            repository_module_1.RepositoryModule,
            tag_module_1.TagModule,
            wineryQNameSelector_module_1.WineryQNameSelectorModule,
            wineryUploader_module_1.WineryUploaderModule,
            wineryIoParameters_module_1.WineryIoParameterModule,
            boundaryDefinitions_module_1.BoundaryDefinitionsModule,
            editXML_module_1.WineryEditXMLModule,
            boundaryDefinitions_module_1.BoundaryDefinitionsModule,
            artifact_module_1.WineryArtifactModule,
        ],
        exports: [],
        declarations: [
            appliesTo_component_1.AppliesToComponent,
            documentation_component_1.DocumentationComponent,
            inheritance_component_1.InheritanceComponent,
            instance_component_1.InstanceComponent,
            instanceHeader_component_1.InstanceHeaderComponent,
            language_component_1.LanguageComponent,
            removeWhiteSpaces_pipe_1.RemoveWhiteSpacesPipe,
            topologyTemplate_component_1.TopologyTemplateComponent,
            plans_component_1.PlansComponent,
            validSourcesAndTargets_component_1.ValidSourcesAndTargetsComponent,
            files_component_1.FilesComponent,
            properties_component_1.PropertiesComponent,
            requiredCapabilityType_component_1.RequiredCapabilityTypeComponent,
            instanceStates_component_1.InstanceStatesComponent,
            logger_component_1.LoggerComponent,
            namespaces_component_1.NamespacesComponent,
            typeWithShortName_component_1.TypeWithShortNameComponent,
            capOrReqDef_component_1.CapOrReqDefComponent,
        ],
        providers: [],
    })
], InstanceModule);
exports.InstanceModule = InstanceModule;
//# sourceMappingURL=instance.module.js.map