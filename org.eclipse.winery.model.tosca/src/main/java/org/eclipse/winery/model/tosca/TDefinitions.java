/*******************************************************************************
 * Copyright (c) 2013-2017 University of Stuttgart
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and the Apache License 2.0 which both accompany this distribution,
 * and are available at http://www.eclipse.org/legal/epl-v10.html
 * and http://www.apache.org/licenses/LICENSE-2.0
 *
 * Contributors:
 *    Oliver Kopp - initial code generation using vhudson-jaxb-ri-2.1-2
 *    Christoph Kleine - Builder implementation
 *******************************************************************************/

package org.eclipse.winery.model.tosca;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.w3c.dom.Element;


/**
 * <p>Java class for tDefinitions complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="tDefinitions">
 *   &lt;complexContent>
 *     &lt;extension base="{http://docs.oasis-open.org/tosca/ns/2011/12}tExtensibleElements">
 *       &lt;sequence>
 *         &lt;element name="Extensions" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Extension" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tExtension"
 * maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Import" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tImport" maxOccurs="unbounded"
 * minOccurs="0"/>
 *         &lt;element name="Types" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="ServiceTemplate" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tServiceTemplate"/>
 *           &lt;element name="NodeType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tNodeType"/>
 *           &lt;element name="NodeTypeImplementation" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tNodeTypeImplementation"/>
 *           &lt;element name="RelationshipType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tRelationshipType"/>
 *           &lt;element name="RelationshipTypeImplementation" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tRelationshipTypeImplementation"/>
 *           &lt;element name="RequirementType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tRequirementType"/>
 *           &lt;element name="CapabilityType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tCapabilityType"/>
 *           &lt;element name="ArtifactType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tArtifactType"/>
 *           &lt;element name="ArtifactTemplate" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tArtifactTemplate"/>
 *           &lt;element name="PolicyType" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tPolicyType"/>
 *           &lt;element name="PolicyTemplate" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tPolicyTemplate"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="targetNamespace" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *       &lt;anyAttribute processContents='lax' namespace='##other'/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tDefinitions", propOrder = {
		"extensions",
		"_import",
		"types",
		"serviceTemplateOrNodeTypeOrNodeTypeImplementation"
})
@XmlSeeAlso({
		Definitions.class
})
public class TDefinitions extends TExtensibleElements {
	@XmlElement(name = "Extensions")
	protected TDefinitions.Extensions extensions;
	@XmlElement(name = "Import")
	protected List<TImport> _import;
	@XmlElement(name = "Types")
	protected TDefinitions.Types types;
	@XmlElements({
			@XmlElement(name = "RelationshipType", type = TRelationshipType.class),
			@XmlElement(name = "RelationshipTypeImplementation", type = TRelationshipTypeImplementation.class),
			@XmlElement(name = "ArtifactTemplate", type = TArtifactTemplate.class),
			@XmlElement(name = "PolicyTemplate", type = TPolicyTemplate.class),
			@XmlElement(name = "ServiceTemplate", type = TServiceTemplate.class),
			@XmlElement(name = "ArtifactType", type = TArtifactType.class),
			@XmlElement(name = "CapabilityType", type = TCapabilityType.class),
			@XmlElement(name = "NodeType", type = TNodeType.class),
			@XmlElement(name = "NodeTypeImplementation", type = TNodeTypeImplementation.class),
			@XmlElement(name = "RequirementType", type = TRequirementType.class),
			@XmlElement(name = "PolicyType", type = TPolicyType.class)
	})
	protected List<TExtensibleElements> serviceTemplateOrNodeTypeOrNodeTypeImplementation;
	@XmlAttribute(name = "id", required = true)
	@XmlJavaTypeAdapter(CollapsedStringAdapter.class)
	@XmlID
	@XmlSchemaType(name = "ID")
	protected String id;
	@XmlAttribute(name = "name")
	protected String name;
	@XmlAttribute(name = "targetNamespace", required = true)
	@XmlSchemaType(name = "anyURI")
	protected String targetNamespace;

	public TDefinitions() {
	}

	public TDefinitions(Builder builder) {
		super(builder);
		this.extensions = builder.extensions;
		this._import = builder._import;
		this.types = builder.types;
		this.serviceTemplateOrNodeTypeOrNodeTypeImplementation = builder.getServiceTemplateOrNodeTypeOrNodeTypeImplementation();
		this.id = builder.id;
		this.name = builder.name;
		this.targetNamespace = builder.target_namespace;
	}

	/**
	 * Gets the value of the extensions property.
	 *
	 * @return possible object is {@link TDefinitions.Extensions }
	 */
	public TDefinitions.Extensions getExtensions() {
		return extensions;
	}

	/**
	 * Sets the value of the extensions property.
	 *
	 * @param value allowed object is {@link TDefinitions.Extensions }
	 */
	public void setExtensions(TDefinitions.Extensions value) {
		this.extensions = value;
	}

	/**
	 * Gets the value of the import property.
	 *
	 * <p>
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the import property.
	 *
	 * <p>
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getImport().add(newItem);
	 * </pre>
	 *
	 *
	 * <p>
	 * Objects of the following type(s) are allowed in the list
	 * {@link TImport }
	 */
	public List<TImport> getImport() {
		if (_import == null) {
			_import = new ArrayList<TImport>();
		}
		return this._import;
	}

	/**
	 * Gets the value of the types property.
	 *
	 * @return possible object is {@link TDefinitions.Types }
	 */
	public TDefinitions.Types getTypes() {
		return types;
	}

	/**
	 * Sets the value of the types property.
	 *
	 * @param value allowed object is {@link TDefinitions.Types }
	 */
	public void setTypes(TDefinitions.Types value) {
		this.types = value;
	}

	/**
	 * Gets the value of the serviceTemplateOrNodeTypeOrNodeTypeImplementation property.
	 *
	 * <p> This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
	 * make to the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE>
	 * method for the serviceTemplateOrNodeTypeOrNodeTypeImplementation property.
	 *
	 * <p> For example, to add a new item, do as follows:
	 * <pre>
	 *    getServiceTemplateOrNodeTypeOrNodeTypeImplementation().add(newItem);
	 * </pre>
	 *
	 *
	 * <p> Objects of the following type(s) are allowed in the list {@link TRelationshipType } {@link
	 * TRelationshipTypeImplementation } {@link TArtifactTemplate } {@link TPolicyTemplate } {@link TServiceTemplate }
	 * {@link TArtifactType } {@link TCapabilityType } {@link TNodeType } {@link TNodeTypeImplementation } {@link
	 * TRequirementType } {@link TPolicyType }
	 */
	public List<TExtensibleElements> getServiceTemplateOrNodeTypeOrNodeTypeImplementation() {
		if (serviceTemplateOrNodeTypeOrNodeTypeImplementation == null) {
			serviceTemplateOrNodeTypeOrNodeTypeImplementation = new ArrayList<TExtensibleElements>();
		}
		return this.serviceTemplateOrNodeTypeOrNodeTypeImplementation;
	}

	/**
	 * Gets the value of the id property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setId(String value) {
		this.id = value;
	}

	/**
	 * Gets the value of the name property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the targetNamespace property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getTargetNamespace() {
		return targetNamespace;
	}

	/**
	 * Sets the value of the targetNamespace property.
	 *
	 * @param value allowed object is {@link String }
	 */
	public void setTargetNamespace(String value) {
		this.targetNamespace = value;
	}


	/**
	 * <p>Java class for anonymous complex type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="Extension" type="{http://docs.oasis-open.org/tosca/ns/2011/12}tExtension"
	 * maxOccurs="unbounded"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"extension"
	})
	public static class Extensions {

		@XmlElement(name = "Extension", required = true)
		protected List<TExtension> extension;

		/**
		 * Gets the value of the extension property.
		 *
		 * <p>
		 * This accessor method returns a reference to the live list,
		 * not a snapshot. Therefore any modification you make to the
		 * returned list will be present inside the JAXB object.
		 * This is why there is not a <CODE>set</CODE> method for the extension property.
		 *
		 * <p>
		 * For example, to add a new item, do as follows:
		 * <pre>
		 *    getExtension().add(newItem);
		 * </pre>
		 *
		 *
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link TExtension }
		 */
		public List<TExtension> getExtension() {
			if (extension == null) {
				extension = new ArrayList<TExtension>();
			}
			return this.extension;
		}
	}


	/**
	 * <p>Java class for anonymous complex type.
	 *
	 * <p>The following schema fragment specifies the expected content contained within this class.
	 *
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;any processContents='lax' namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = {
			"any"
	})
	public static class Types {

		@XmlAnyElement(lax = true)
		protected List<Object> any;

		/**
		 * Gets the value of the any property.
		 *
		 * <p>
		 * This accessor method returns a reference to the live list,
		 * not a snapshot. Therefore any modification you make to the
		 * returned list will be present inside the JAXB object.
		 * This is why there is not a <CODE>set</CODE> method for the any property.
		 *
		 * <p>
		 * For example, to add a new item, do as follows:
		 * <pre>
		 *    getAny().add(newItem);
		 * </pre>
		 *
		 *
		 * <p>
		 * Objects of the following type(s) are allowed in the list
		 * {@link Element }
		 * {@link Object }
		 */
		public List<Object> getAny() {
			if (any == null) {
				any = new ArrayList<Object>();
			}
			return this.any;
		}
	}

	public static class Builder extends TExtensibleElements.Builder {
		private final String id;
		private final String target_namespace;

		private TDefinitions.Extensions extensions;
		private List<TImport> _import;
		private TDefinitions.Types types;
		private List<TServiceTemplate> serviceTemplates;
		private List<TNodeType> nodeTypes;
		private List<TNodeTypeImplementation> nodeTypeImplementations;
		private List<TRelationshipType> relationshipTypes;
		private List<TRelationshipTypeImplementation> relationshipTypeImplementations;
		private List<TRequirementType> requirementTypes;
		private List<TCapabilityType> capabilityTypes;
		private List<TArtifactType> artifactTypes;
		private List<TArtifactTemplate> artifactTemplates;
		private List<TPolicyType> policyTypes;
		private List<TPolicyTemplate> policyTemplate;
		private String name;

		public Builder(String id, String target_namespace) {
			this.id = id;
			this.target_namespace = target_namespace;
		}

		public Builder RMsetExtensions(TDefinitions.Extensions extensions) {
			this.extensions = extensions;
			return this;
		}

		public Builder RMsetImport(List<TImport> _import) {
			this._import = _import;
			return this;
		}

		public Builder RMsetTypes(TDefinitions.Types types) {
			this.types = types;
			return this;
		}

		public Builder RMsetServiceTemplates(List<TServiceTemplate> serviceTemplates) {
			this.serviceTemplates = serviceTemplates;
			return this;
		}

		public Builder RMsetNodeTypes(List<TNodeType> nodeTypes) {
			this.nodeTypes = nodeTypes;
			return this;
		}

		public Builder RMsetNodeTypeImplementations(List<TNodeTypeImplementation> nodeTypeImplementations) {
			this.nodeTypeImplementations = nodeTypeImplementations;
			return this;
		}

		public Builder RMsetRelationshipTypes(List<TRelationshipType> relationshipTypes) {
			this.relationshipTypes = relationshipTypes;
			return this;
		}

		public Builder RMsetRelationshipTypeImplementations(List<TRelationshipTypeImplementation> relationshipTypeImplementations) {
			this.relationshipTypeImplementations = relationshipTypeImplementations;
			return this;
		}

		public Builder RMsetRequirementTypes(List<TRequirementType> requirementTypes) {
			this.requirementTypes = requirementTypes;
			return this;
		}

		public Builder RMsetCapabilityTypes(List<TCapabilityType> capabilityTypes) {
			this.capabilityTypes = capabilityTypes;
			return this;
		}

		public Builder RMsetArtifactTypes(List<TArtifactType> artifactTypes) {
			this.artifactTypes = artifactTypes;
			return this;
		}

		public Builder RMsetArtifactTemplates(List<TArtifactTemplate> artifactTemplates) {
			this.artifactTemplates = artifactTemplates;
			return this;
		}

		public Builder RMsetPolicyTypes(List<TPolicyType> policyTypes) {
			this.policyTypes = policyTypes;
			return this;
		}

		public Builder RMsetPolicyTemplate(List<TPolicyTemplate> policyTemplate) {
			this.policyTemplate = policyTemplate;
			return this;
		}

		public Builder RMsetName(String name) {
			this.name = name;
			return this;
		}

		public Builder addExtensions(List<TExtension> extensions) {
			if (extensions == null) {
				return this;
			}

			if (this.extensions == null) {
				this.extensions = new TDefinitions.Extensions();
			}
			this.extensions.getExtension().addAll(extensions);
			return this;
		}

		public Builder addTypes(List<Object> dataTypes) {
			if (dataTypes == null) {
				return this;
			}

			if (this.types == null) {
				this.types = new TDefinitions.Types();
			}
			this.types.getAny().addAll(dataTypes);
			return this;
		}

		public Builder addServiceTemplates(List<TServiceTemplate> serviceTemplates) {
			if (serviceTemplates == null) {
				return this;
			}

			if (this.serviceTemplates == null) {
				this.serviceTemplates = serviceTemplates;
			} else {
				this.serviceTemplates.addAll(serviceTemplates);
			}
			return this;
		}

		public Builder addServiceTemplates(TServiceTemplate serviceTemplate) {
			if (serviceTemplate == null) {
				return this;
			}

			List<TServiceTemplate> tmp = new ArrayList<>();
			tmp.add(serviceTemplate);
			return addServiceTemplates(tmp);
		}

		public TDefinitions build() {
			return new TDefinitions(this);
		}

		public List<TExtensibleElements> getServiceTemplateOrNodeTypeOrNodeTypeImplementation() {
			List<TExtensibleElements> tmp = new ArrayList<>();

			Optional.ofNullable(serviceTemplates).ifPresent(tmp::addAll);
			Optional.ofNullable(nodeTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(nodeTypeImplementations).ifPresent(tmp::addAll);
			Optional.ofNullable(relationshipTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(relationshipTypeImplementations).ifPresent(tmp::addAll);
			Optional.ofNullable(requirementTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(capabilityTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(artifactTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(artifactTemplates).ifPresent(tmp::addAll);
			Optional.ofNullable(policyTypes).ifPresent(tmp::addAll);
			Optional.ofNullable(policyTemplate).ifPresent(tmp::addAll);
			return tmp;
		}
	}
}
