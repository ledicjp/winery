## Artifacts in TOSCA YAML

TOSCA XML and TOSCA YAML define artifacts at different TOSCA components. The following sections explain how artifacts are converted between TOSCA XML and TOSCA YAML.

### TOSCA XML NodeTypeImplementation.ImplementationArtifact
TOSCA YAML has no component NodeTypeImplementation and TOSCA YAML NodeTypes do not define ArtifactsDefinitions which have a property interface and operation.
 
TOSCA XML pseudo definition
```yaml
NodeTypeImplementation:
  ...
  ImplementationArtifacts:
    ImplementationArtifact: *
      interfaceName: InterfaceeName
      operationName: OperationName
      artifactType: ArtifactTypeName?
      artifactRef: ArtifactTemplateName

ArtifactTemplate:
  ...
  ArtifactReferences:
    ArtifactReference: *
      reference: ArtifactFileName
```
Mapped to TOSCA YAML
```yaml
NodeType:
  ...
  artifacts:
    ArtifactDefinitionName: * 
      type: ArtifactTypeName
      file: 
        - File URI|Name 
      repository: RepositoryDefinitionName
      deploy_path: Path
  interfaces:
    InterfaceDefinitionName:
      type: InterfaceTypeName?
      ...
      OperationDefinitionName: *
        ...
        implementation:
          primary: ArtifactDefinitionName
          dependencies: ArtifactDefinitionName*
          
InterfaceType:
  ...
  OperationDefinitionName: *
```

To build NodeTypeImplementation.ImplementationArtifacts from TOSCA YAML NodeTypes the NodeType interface operations must be visited and Operation.Implementation is converted to an ArtifactTemplate an ImplementationArtifact. Those ImplementationArtifacts are collected into a NodeTypeImplementation for the NodeType.

Additionally the NodeType.ArtifactDefinitions are converted to ImplementationArtifacts for all operation and interfaces. There is no decision mechanism in place that decides if an YAML ArtifactDefinition contains an ImplementationArtifact or an DeploymentArtifact.

ArtifactDefinitions with an ArtifactType deriving from TOSCA YAML normative types `tosca.artifacts.Implementation` or `tosca.artifacts.Deployment` are considered as either deployment or implementation artifacts. 

ArtifactDefinitions with an ArtifactType not deriving from these TOSCA YAML normative types are converted as deployment and implementation artifacts.

#### Restrictions
YAML ArtifactDefinitions which are referenced in operation implementations will get an interfaceName property and an operationName property.

If ArtifactDefinitions are referenced in multiple operation implementation or in different interfaces, the converter will change the properties to make the TOSCA XML ImplementationArtifact available for all operations.

If the user the an ImplementationArtifact should be available for all interfaces or operations either don't reference it in any operation implementation or reference it in all operation implementations.
