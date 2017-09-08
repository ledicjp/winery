# TOSCA YAML Converter
## DataTypes and Property Definition Conversions
### From YAML to XML
TOSCA YAML defines `TDataTypes` and T`PropertyDefinition`
```yaml
DataTypeName:
  derived_from: DataTypeName2
  version: 1.0.0
  metadata:
    description: Examlpe Data Type
    author: kleinech
  properties: 
    name:
      type: string
    ageSpecial:
      type: otherDataType
  constraints: <ConstraintClause>*
  
PropertyDefinition:
  type: typeName (e.g. string, other DataTypeName)
  required: true|false
  default: object
  status: experimental
  constraints: <ConstraintClause>*
  entry_schema: <EntrySchema>
```
Each data type is converted to an XML schema with complex types, each PropertyDefinition for other EntityType (excluding DataType) is convertet to an XML schema with an element containing a complex type and importing if necessary other schemas. 

Simple Example:
```yaml
...
node_types:
  DockerEngine:
    properties:
      DockerEngineURL:
        type: string
      DockerEngineCertificate:
        type: string
```
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" attributeFormDefault="unqualified" elementFormDefault="qualified" targetNamespace="http://www.example.com/MyTinyToDo/NodeTypes" xmlns="http://www.w3.org/2001/XMLSchema">
    <element name="DockerEngine_Properties">
        <complexType>
            <sequence xmlns:pfx0="http://www.example.com/MyTinyToDo/NodeTypes">
                <element name="DockerEngineURL" type="xsd:string"/>
                <element name="DockerEngineCertificate" type="xsd:string"/>
            </sequence>
        </complexType>
    </element>
</schema>
```

Complex Example
```yaml
...
imports:
  - data_types:
      file: /data_types.yml
      namespace_uri: http://www.example.com/DataTypesTest
      namespace_prefix: imp1

node_types:
  example.NodeType:
    properties:
      example.p1:
        type: string
      example.p2:
        type: imp1:example.com.Compose
...
################ data_types.yml #################
tosca_definitions_version: tosca_simple_yaml_1_1

data_types:
  example.com.Number:
    properties:
      prefix:
        type: integer
      suffix:
        type: integer
  example.com.Compose:
    properties:
      compose:
        type: example.com.Number
      addition:
        type: string
```
```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<schema xmlns:imp1="http://www.example.com/DataTypesTest" xmlns:xsd="http://www.w3.org/2001/XMLSchema" attributeFormDefault="unqualified" elementFormDefault="qualified" targetNamespace="http://www.example.com/NodeTemplateUsingDataType" xmlns="http://www.w3.org/2001/XMLSchema">
    <import namespace="http://www.example.com/DataTypesTest" schemaLocation="http%3A%2F%2Fwww.example.com%2FDataTypesTest.xsd"/>
    <element name="example.NodeType_Properties">
        <complexType>
            <sequence xmlns:pfx0="http://www.example.com/NodeTemplateUsingDataType">
                <element name="example.p1" type="xsd:string"/>
                <element name="example.p2" type="imp1:example.com.Compose"/>
            </sequence>
        </complexType>
    </element>
</schema>

<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<schema xmlns:xsd="http://www.w3.org/2001/XMLSchema" attributeFormDefault="unqualified" elementFormDefault="qualified" targetNamespace="http://www.example.com/DataTypesTest" xmlns="http://www.w3.org/2001/XMLSchema">
    <complexType name="example.com.Number">
        <sequence xmlns:pfx0="http://www.example.com/DataTypesTest">
            <element name="prefix" type="xsd:decimal"/>
            <element name="suffix" type="xsd:decimal"/>
        </sequence>
    </complexType>
    <complexType name="example.com.Compose">
        <sequence xmlns:pfx0="http://www.example.com/DataTypesTest">
            <element name="compose" type="pfx0:example.com.Number"/>
            <element name="addition" type="xsd:string"/>
        </sequence>
    </complexType>
</schema>

```

