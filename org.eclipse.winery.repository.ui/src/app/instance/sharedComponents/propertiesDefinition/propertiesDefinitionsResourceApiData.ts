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

export enum PropertiesDefinitionEnum {
    Custom = 'Custom',
    Element = 'Element',
    Type = 'Type',
    None = 'None'
}

export class PropertiesDefinitionKVList {
    key: string = null;
    type: string = null;
}

export class PropertiesDefinition {
    element: string = null;
    type: string = null;
}

export class WinerysPropertiesDefinition {
    namespace: string = null;
    elementName: string = null;
    propertyDefinitionKVList: PropertiesDefinitionKVList[] = [];
    isDerivedFromXSD = false;
}

export interface PropertiesDefinitionsResourceApiData {
    propertiesDefinition: PropertiesDefinition;
    winerysPropertiesDefinition: WinerysPropertiesDefinition;
    selectedValue: PropertiesDefinitionEnum;
}
