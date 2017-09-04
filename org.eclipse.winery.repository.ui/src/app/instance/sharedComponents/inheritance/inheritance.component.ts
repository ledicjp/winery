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
import { Component, OnInit } from '@angular/core';
import { isNullOrUndefined } from 'util';
import { WineryNotificationService } from '../../../wineryNotificationModule/wineryNotification.service';
import { InstanceService } from '../../instance.service';
import { InheritanceService } from './inheritance.service';
import { InheritanceApiData } from './inheritanceApiData';
import { NameAndQNameApiDataList } from '../../../wineryQNameSelector/wineryNameAndQNameApiData';
import { ToscaTypes } from '../../../wineryInterfaces/enums';


@Component({
    selector: 'winery-instance-inheritance',
    templateUrl: 'inheritance.component.html',
    providers: [InheritanceService],
})
export class InheritanceComponent implements OnInit {

    inheritanceApiData: InheritanceApiData;
    availableSuperClasses: NameAndQNameApiDataList;
    toscaType: ToscaTypes;
    loading = true;

    constructor(private sharedData: InstanceService,
                private service: InheritanceService,
                private notify: WineryNotificationService) {
    }

    ngOnInit() {
        this.service.getInheritanceData()
            .subscribe(
                data => this.handleInheritanceData(data),
                error => this.handleError(error)
            );
        this.service.getAvailableSuperClasses()
            .subscribe(
                data => this.handleSuperClassData(data),
                error => this.handleError(error)
            );
        this.toscaType = this.sharedData.toscaComponent.toscaType;
    }

    onSelectedValueChanged(value: string) {
        this.inheritanceApiData.derivedFrom = value;
    }

    public saveToServer(): void {
        this.loading = true;
        this.service.saveInheritanceData(this.inheritanceApiData)
            .subscribe(
                data => this.handlePutResponse(data),
                error => this.handleError(error)
            );
    }

    private handleInheritanceData(inheritance: InheritanceApiData) {
        this.inheritanceApiData = inheritance;

        if (!isNullOrUndefined(this.availableSuperClasses)) {
            this.loading = false;
        }
    }

    private handleSuperClassData(superClasses: NameAndQNameApiDataList) {
        this.availableSuperClasses = superClasses;

        if (!isNullOrUndefined(this.inheritanceApiData)) {
            this.loading = false;
        }
    }

    private handlePutResponse(response: any) {
        this.loading = false;
        this.notify.success('Saved changes', 'Success');
    }

    private handleError(error: any): void {
        this.loading = false;
        this.notify.error(error.toString(), 'Error');
    }

}
