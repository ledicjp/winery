/**
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Lukas Harzenetter, Niko Stadelmaier - initial API and implementation
 */
import { Component, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { InstanceService } from './instance.service';
import { WineryNotificationService } from '../wineryNotificationModule/wineryNotification.service';
import { backendBaseURL } from '../configuration';
import { RemoveWhiteSpacesPipe } from '../wineryPipes/removeWhiteSpaces.pipe';
import { ExistService } from '../wineryUtils/existService';
import { isNullOrUndefined } from 'util';
import { WineryInstance } from '../wineryInterfaces/wineryComponent';
import { ToscaTypes } from '../wineryInterfaces/enums';
import { ToscaComponent } from '../wineryInterfaces/toscaComponent';

@Component({
    templateUrl: 'instance.component.html',
    providers: [
        InstanceService,
        RemoveWhiteSpacesPipe,
    ]
})
export class InstanceComponent implements OnDestroy {

    availableTabs: string[];
    toscaComponent: ToscaComponent;
    typeUrl: string;
    typeId: string;
    typeOf: string;
    imageUrl: string;

    routeSub: Subscription;

    constructor(private route: ActivatedRoute,
                private router: Router,
                private service: InstanceService,
                private notify: WineryNotificationService, private existService: ExistService) {
        this.routeSub = this.route
            .data
            .subscribe(data => {
                    this.toscaComponent = data['resolveData'] ? data['resolveData'] : new ToscaComponent(ToscaTypes.Admin, '', '');

                    this.service.setSharedData(this.toscaComponent);

                    if (!isNullOrUndefined(this.toscaComponent)) {
                        if (this.toscaComponent.toscaType === ToscaTypes.NodeType) {
                            const img = backendBaseURL + this.service.path + '/visualappearance/50x50';
                            this.existService.check(img)
                                .subscribe(
                                    () => this.imageUrl = img,
                                    () => this.imageUrl = null,
                                );
                        }
                        this.service.getComponentData()
                            .subscribe(
                                compData => this.handleComponentData(compData)
                            );
                    }

                    this.availableTabs = this.service.getSubMenuByResource();

                    // redirect to first element in the menu
                    if (!this.router.url.includes('/admin') && this.router.url.split('/').length < 5) {
                        this.router.navigate([this.service.path + '/' + this.availableTabs[0].toLowerCase().replace(/ /g, '')]);
                    }
                },
                error => this.handleError(error)
            );
    }

    deleteComponent() {
        this.service.deleteComponent().subscribe(data => this.handleDelete(), error => this.handleError(error));
    }

    private handleComponentData(data: WineryInstance) {
        switch (this.toscaComponent.toscaType) {
            case ToscaTypes.NodeTypeImplementation:
                this.typeUrl = '/nodetypes';
                break;
            case ToscaTypes.RelationshipTypeImplementation:
                this.typeUrl = '/relationshiptypes';
                break;
            case ToscaTypes.PolicyTemplate:
                this.typeUrl = '/policytypes';
                break;
            case ToscaTypes.ArtifactTemplate:
                this.typeUrl = '/artifacttypes';
                break;
            default:
                this.typeUrl = null;
        }

        if (!isNullOrUndefined(this.typeUrl)) {
            const tempOrImpl = data.serviceTemplateOrNodeTypeOrNodeTypeImplementation[0];
            let qName: string[];

            if (!isNullOrUndefined(tempOrImpl.type)) {
                qName = tempOrImpl.type.slice(1).split('}');
                this.typeOf = 'Type: ';
            } else if (!isNullOrUndefined(tempOrImpl.nodeType)) {
                qName = tempOrImpl.nodeType.slice(1).split('}');
                this.typeOf = 'Implementation for ';
            } else if (!isNullOrUndefined(tempOrImpl.relationshipType)) {
                qName = tempOrImpl.relationshipType.slice(1).split('}');
                this.typeOf = 'Implementation for ';
            }

            if (qName.length === 2) {
                this.typeUrl += '/' + encodeURIComponent(encodeURIComponent(qName[0])) + '/' + qName[1];
                this.typeId = qName[1];
            } else {
                this.typeUrl = null;
            }
        }
    }

    private handleDelete() {
        this.notify.success('Successfully deleted ' + this.toscaComponent.localName);
        this.router.navigate(['/' + this.toscaComponent.toscaType]);
    }

    private handleError(error: any) {
        this.notify.error(error.toString(), 'Error');
    }

    ngOnDestroy(): void {
        this.routeSub.unsubscribe();
    }
}
