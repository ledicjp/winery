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
package org.eclipse.winery.model.tosca.yaml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.eclipse.winery.model.tosca.yaml.support.Annotations;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.IVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.VisitorNode;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tArtifactDefinition", namespace = " http://docs.oasis-open.org/tosca/ns/simple/yaml/1.0", propOrder = {
    "type",
    "files",
    "repository",
    "description",
    "deployPath"
})
public class TArtifactDefinition implements VisitorNode {
    @XmlAttribute(name = "type", required = true)
    private QName type;
    private String repository;
    private String description;
    @XmlAttribute(name = "deploy_path")
    private String deployPath;

    @Annotations.StandardExtension
    @XmlAttribute(name = "files", required = true)
    private List<String> files;
    @Annotations.StandardExtension
    private Map<String, TPropertyAssignment> properties;

    public TArtifactDefinition() {
    }

    public TArtifactDefinition(Builder builder) {
        this.setType(builder.type);
        this.setFiles(builder.files);
        this.setRepository(builder.repository);
        this.setDescription(builder.description);
        this.setDeployPath(builder.deployPath);
        this.setProperties(builder.properties);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TArtifactDefinition)) return false;
        TArtifactDefinition that = (TArtifactDefinition) o;
        return Objects.equals(type, that.type) &&
            Objects.equals(files, that.files) &&
            Objects.equals(repository, that.repository) &&
            Objects.equals(description, that.description) &&
            Objects.equals(deployPath, that.deployPath) &&
            Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, files, repository, description, deployPath, properties);
    }

    @NonNull
    public QName getType() {
        return type;
    }

    public void setType(QName type) {
        this.type = type;
    }

    @NonNull
    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Deprecated
    @NonNull
    public String getFile() {
        return this.getFiles().get(0);
    }

    @Deprecated
    public void setFile(String file) {
        this.files = new ArrayList<>(Collections.singleton(file));
    }

    @Nullable
    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Nullable
    public String getDeployPath() {
        return deployPath;
    }

    public void setDeployPath(String deployPath) {
        this.deployPath = deployPath;
    }

    public Map<String, TPropertyAssignment> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, TPropertyAssignment> properties) {
        this.properties = properties;
    }

    public <R extends AbstractResult<R>, P extends AbstractParameter<P>> R accept(IVisitor<R, P> visitor, P parameter) {
        return visitor.visit(this, parameter);
    }

    public static class Builder {
        private final QName type;
        private final List<String> files;

        private String repository;
        private String description;
        private String deployPath;
        private Map<String, TPropertyAssignment> properties;

        public Builder(QName type, List<String> files) {
            this.type = type;
            this.files = files;
        }

        public Builder(TArtifactDefinition artifactDefinition) {
            this.type = artifactDefinition.getType();
            this.files = artifactDefinition.getFiles();
            this.repository = artifactDefinition.getRepository();
            this.description = artifactDefinition.getDescription();
            this.deployPath = artifactDefinition.getDeployPath();
            this.properties = artifactDefinition.getProperties();
        }

        public Builder setRepository(String repository) {
            this.repository = repository;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDeployPath(String deployPath) {
            this.deployPath = deployPath;
            return this;
        }

        public Builder setProperties(Map<String, TPropertyAssignment> properties) {
            this.properties = properties;
            return this;
        }

        public TArtifactDefinition build() {
            return new TArtifactDefinition(this);
        }
    }
}
