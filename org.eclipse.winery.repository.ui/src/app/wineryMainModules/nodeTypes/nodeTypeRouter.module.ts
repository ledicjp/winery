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
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SectionComponent } from '../../section/section.component';
import { SectionResolver } from '../../section/section.resolver';
import { InstanceComponent } from '../../instance/instance.component';
import { InstanceResolver } from '../../instance/instance.resolver';
import { EditXMLComponent } from '../../instance/sharedComponents/editXML/editXML.component';
import { DocumentationComponent } from '../../instance/sharedComponents/documentation/documentation.component';
import { ToscaTypes } from '../../wineryInterfaces/enums';
import { VisualAppearanceComponent } from '../../instance/sharedComponents/visualAppearance/visualAppearance.component';
import { InstanceStatesComponent } from '../../instance/sharedComponents/instanceStates/instanceStates.component';
import { InterfacesComponent } from '../../instance/sharedComponents/interfaces/interfaces.component';
import { ImplementationsComponent } from '../../instance/sharedComponents/implementations/implementations.component';
import { InheritanceComponent } from '../../instance/sharedComponents/inheritance/inheritance.component';
import { PropertiesDefinitionComponent } from '../../instance/sharedComponents/propertiesDefinition/propertiesDefinition.component';
import { CapOrReqDefComponent } from '../../instance/nodeTypes/capabilityOrRequirementDefinitions/capOrReqDef.component';

const toscaType = ToscaTypes.NodeType;

const nodeTypeRoutes: Routes = [
    { path: toscaType, component: SectionComponent, resolve: { resolveData: SectionResolver } },
    { path: toscaType + '/:namespace', component: SectionComponent, resolve: { resolveData: SectionResolver } },
    {
        path: toscaType + '/:namespace/:localName',
        component: InstanceComponent,
        resolve: { resolveData: InstanceResolver },
        children: [
            { path: 'visualappearance', component: VisualAppearanceComponent },
            { path: 'instancestates', component: InstanceStatesComponent },
            { path: 'interfaces', component: InterfacesComponent },
            { path: 'implementations', component: ImplementationsComponent },
            { path: 'requirementdefinitions', component: CapOrReqDefComponent },
            { path: 'capabilitydefinitions', component: CapOrReqDefComponent },
            { path: 'propertiesdefinition', component: PropertiesDefinitionComponent },
            { path: 'inheritance', component: InheritanceComponent },
            { path: 'documentation', component: DocumentationComponent },
            { path: 'xml', component: EditXMLComponent }
        ]
    }
];

@NgModule({
    imports: [
        RouterModule.forChild(nodeTypeRoutes),
    ],
    exports: [
        RouterModule
    ],
    providers: [
        SectionResolver,
        InstanceResolver
    ],
})
export class NodeTypeRouterModule {
}
