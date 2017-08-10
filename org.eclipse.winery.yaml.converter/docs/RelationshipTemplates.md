## TOSCA YAML Relationship Templates
Compared to TOSCA XML Relationship Templates TOSCA YAML Relationship Templates have no field for `sourceElement` and `targetElement`.

TOSCA XML Relationship Template
```yaml
RelationshipTemplate:
  id: string
  name: string
  type: relationshipTypeQName
  Properties: any
  PropertyConstraints: ..
  SourceElement:
    ref: idref
  TargetElement:
    ref: idref
  RelationshipConstraints: ..
```

TOSCA YAML Relationship Template
```yaml
relationshipTemplateName:
  type: relationshipTypeQName
  description: string
  metadata: string*
  properties: propertyAssignment*
  attributes: attributeAssignment*
  interfaces: interfaceDefinition*
  copy: relationshipTemplateName
```

TOSCA YAML NodeType.Requirement
```yaml
requirementDefinitionName:
  capability: capabilityTypeQName
  node: nodeTypeQName
  relationship: relationshipTypeQName
```

TOSCA YAML specifies the source target relation not in Relationship Templates and not for Templates rather for Types.

TOSCA YAML Node Templates specify requirements and capability. If the requirement of one Node Template is matched by the capability of an other Node Template and the requirement specifies a relationship then a Relationship Type is constructed.
