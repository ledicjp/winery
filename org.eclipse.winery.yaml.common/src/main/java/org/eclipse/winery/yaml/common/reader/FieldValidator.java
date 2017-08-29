/*******************************************************************************
 * Copyright (c) 2017 University of Stuttgart.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *     Christoph Kleine - initial API and implementation
 *******************************************************************************/
package org.eclipse.winery.yaml.common.reader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldValidator {
    private Map<Class, Set<String>> declaredFields;

    public FieldValidator() {
        this.declaredFields = new LinkedHashMap<>();
    }

    private void setDeclaredFields(Class base, Class parent) {
        if (!this.declaredFields.containsKey(base)) {
            this.declaredFields.put(base, new HashSet<>());
        }

        if (!parent.equals(Object.class)) {
            this.declaredFields.get(base).addAll(Arrays.stream(parent.getDeclaredFields()).map(Field::getName).collect(Collectors.toList()));
            setDeclaredFields(base, parent.getSuperclass());
        }
    }

    public <T> List<String> validate(Class<T> t, Map<String, Object> fields) {
        List<String> msg = new ArrayList<>();

        if (!fields.isEmpty() && !this.declaredFields.containsKey(t)) {
            setDeclaredFields(t, t);
        }

        Set<String> declaredFields = this.declaredFields.get(t);
        fields.forEach((key, value) -> {
            if (key.equals("default")) {
                key = "_" + key;
            }

            if (!declaredFields.contains(key)) {
                msg.add(t.getName() + " does not have a field with the name " + key);
            }
        });
        return msg;
    }
}
