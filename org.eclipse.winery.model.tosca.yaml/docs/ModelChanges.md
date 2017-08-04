# Model Changes compared to TOSCA Simple Profile in YAML Version 1.1

## References
Types references and other references are defined as strings in the Specification. This model uses QNames instead of simple strings. Each reference has a namespace.

References to elements in the same ServiceTemplate have the namespace_uri of the ServiceTemplate, which is specified by the user when parsing the document or set to the default namespace.
  
References to elements specified in imported ServiceTemplate will use the namespace and prefix of the the ImportDefinition, if defined, or the namespace of the importing ServiceTemplate.
 
### TOSCA YAML Simple Profile Normative Types
References to normative types MUST NOT have a prefix and the normative types will be imported automatically.

## Relationship Template
The specification specifies the sourceElement and targetElement of Relationship Templates indirect with Node Template requirement and capability definitions.

TOSCA XML defines sourceElements and targetElements fields directly for Relationship Templates and these fields are IDRefs to Node Templates, Requirements or Capabilities. The field `node` for capability definitions for TOSCA YAML Node Templates specifies a node type. 
 
The data model added a `source` and `target` field to simplify the conversion between TOSCA YAML and TOSCA XML, which means a converter does not need to pick one instance of a Node Type and build a TOSCA XML Relationship Template. 

## Operation Definition

### Outputs
Operation definition of InterfaceDefinition or InterfaceTypes have an additional field `outputs` similar to TOSCA XML OperationDefinition.

The field `outputs` is currently not supported by the Reader and used only internally.

The specification allows to use the output of operations by using `get_operation_output` for AttributeDefinition default values:

(example for NodeType.attributes)
```yaml
attributeName:
  default: { get_operation_ouput: [ SELF, interfaceName, operationName, outputName ] }
```

### Implementation
The implementation field originally specifies the name of an implementation artifact (example: `implementation: "/path/file.sh"`) which must be point to a file within the a TOSCA CSAR file.

Since the TOSCA XML data model always requires complete artifacts with types the value of the field `implementation` or `implementation.primary` and the values of the list `implementation.dependencies` are references to ArtifactDefinitions defined in the same entity.

## Artifact Definition

### More than one file per definition
The model limits the number of files that can be specified for one Artifact definition to 1. This data model supports more than one file per Artifact Definition.

Old syntax (data model is backwards compatible):
```yaml
artifactDefinitionName:
  type: string
  file: string
  repository: string?
  description: string?
  deploy_path: string?
```
New syntax:
```yaml
artifactDefinitionName:
  type: string
  file: string+
  repository: string?
  description: string?
  deploy_path: string?
```

### Property Assignment
All tosca entities which have property definitions specified for their types should also have property assignments for instances of those types.

This data model added the field `properties` for assigning properties to the property definition specified in Artifact types.

```yaml
artifactDefinitionName:
  ...
  properties: TPropertyAssignment*
```

## List<Map<String, ?>>
All occurrences of List of Collections have an support java class that wraps the inner collection and implements the Map interface. 

This solves the problem of generating an xml schema for this model with JAXB. Because JAXB has trouble to convert List of Collections without specifying adapters for each occurrence.  

The support classes are `TMapObject.class`, `TMapImportDefinition`, `TMapPropertyFilterDefinition`, `TMapRequirementAssignment`, `TMapRequirementDefinition`.

_Explanation: YAML Map elements have no defined order therefor the specification uses a List of (single element) Maps to specify an order if necessary._

## Workflows
The specification supports the definition of `workflows` for TopologyTemplateDefinition. This data model does not support the field `workflows` and rather encourages the use of well known workflow languages like BPEL and BPMN.

## Builders
Java Builders defined for the data model classes do not change the model language and only simplify the work with this data model.
 
## Visitor Interface
Each model class has an generic visitor function.
```java
 R accept(IVisitor visitor, P parameter) throws IException 
 ```
Those functions and the abstract class and interfaces defined in the `visitor` package allows to create visitor design pattern for traversing the ServiceTemplate.
 
The java classes AbstractVisitor, AbstractParameter and AbstractResult can be extended to write Visitors:
 
```java
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractVisitor;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractResult;
import org.eclipse.winery.model.tosca.yaml.visitor.AbstractParameter;
/*...*/

public class VisitorExample extends AbstractVisitor<Result, Parameter> {
    public List<TNodeType> getNodeTypes(TServiceTemplate serviceTemplate) {
        return visit(serviceTemplate, new Parameter()).getNodeTypes();
    }	
    
    @Override
    public Result visit(TServiceTemplate node, Parameter parameter) {
        Result result = super.visit(node, parameter);
        if (result == null) {
            result = new Result();
        }
        
        return result;
    }
    
    @Override
    public Result visit(TNodeType node, Parameter parameter) throws IException {
        List<TNodeType> list = new ArrayList<>();
        list.add(node);
        return new Result().addNodeTypes(list);
    }
    
    public static class Result extends AbstractResult<Result> {
        private List<TNodeType> nodeTypes;
        
        public Result() {
            this.nodeTypes = new ArrayList<>();
        }
        
        public Result addNodeTypes(List<TNodeType> nodeTypes) {
            this.nodeTypes.addAll(nodeTypes);
        }
        
        public List<TNodeType> getNodeTypes() {
            return this.nodeTypes;
        }
        
        @Override
        public Result add(Result result) {
            return this.addNodeTypes(result.nodeTypes);
        }
    }
    
    public static class Parameter extends AbstractResult<Parameter> {
        @Override
        public Parameter copy() {
            return new Parameter();
        }
        
        @Override
        public Parameter self() {
            return this;
        }
    }
}
```
 
_The visitor design pattern functions and classes do not affect the data model._ 
