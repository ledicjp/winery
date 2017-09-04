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
import { CapOrReqDefComponent } from '../../instance/nodeTypes/capabilityOrRequirementDefinitions/capOrReqDef.component';
import { CommonModule } from '@angular/common';
import { NodeTypeRouterModule } from './nodeTypeRouter.module';
import { ImplementationsModule } from '../../instance/sharedComponents/implementations/implementations.module';
import { WineryModalModule } from '../../wineryModalModule/winery.modal.module';
import { SpinnerWithInfinityModule } from '../../winerySpinnerWithInfinityModule/winerySpinnerWithInfinity.module';
import { WineryEditorModule } from '../../wineryEditorModule/wineryEditor.module';
import { SelectModule } from 'ng2-select';
import { WineryLoaderModule } from '../../wineryLoader/wineryLoader.module';
import { WineryQNameSelectorModule } from '../../wineryQNameSelector/wineryQNameSelector.module';
import { WineryTableModule } from '../../wineryTableModule/wineryTable.module';
import { FormsModule } from '@angular/forms';
import { VisualAppearanceModule } from '../../instance/sharedComponents/visualAppearance/visualAppearance.module';
import { InstanceStatesModule } from '../../instance/sharedComponents/instanceStates/instanceStates.module';
import { PropertiesDefinitionModule } from '../../instance/sharedComponents/propertiesDefinition/propertiesDefinition.module';
import { InheritanceModule } from '../../instance/sharedComponents/inheritance/inheritance.module';
import { WineryArtifactModule } from '../../instance/sharedComponents/wineryArtifacts/artifact.module';

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        VisualAppearanceModule,
        InstanceStatesModule,
        PropertiesDefinitionModule,
        InheritanceModule,
        ImplementationsModule,
        SelectModule,
        SpinnerWithInfinityModule,
        WineryLoaderModule,
        WineryQNameSelectorModule,
        WineryTableModule,
        WineryModalModule,
        WineryEditorModule,
        NodeTypeRouterModule
    ],
    exports: [],
    declarations: [CapOrReqDefComponent],
    providers: [],
})
export class NodeTypeModule {
}
