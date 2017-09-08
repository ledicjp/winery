# Changelog

All notable changes to this project will be documented in this file.
This project **does not** adhere to [Semantic Versioning](http://semver.org/) and [Eclipse plugin versioning](https://wiki.eclipse.org/Version_Numbering).
This file tries to follow the conventions proposed by [keepachangelog.com](http://keepachangelog.com/).
Here, the categories "Changed" for added and changed functionality,
"Fixed" for fixed functionality, and
"Removed" for removed functionality is used.

We refer to [GitHub issues](https://github.com/eclipse/winery/issues) by using `#NUM`.

The ordering of v1.x and v2.x versions is sequential by time.
This is similar to [Angular's CHANGELOG.md](https://github.com/angular/angular/blob/master/CHANGELOG.md), where v4.x and v5.x is mixed.

## [unreleased]

### Changed

- Initial support for BPMN4TOSCA implemented using Angular
- Added initial CLI. Current funtionality: Consistency check of the repository.
- Rewrote the Backend UI using Angular
- org.eclipse.winery.model.tosca was extended with builders and some helper classes
- Fixed wrong output of "CSAR Export mode. Putting XSD into CSAR" if in CSAR export mode
- New project `org.eclipse.winery.repository.rest` for separating REST resources from the backend
- Add support of [Splitting](http://eclipse.github.io/winery/user/Splitting)
- Add support of [DASpecification](http://eclipse.github.io/winery/user/DASpecification)
- **BREAKING**: in the tosca model `SourceElement` and `TargetElement` are combined into `SourceOrTargetElement` due to serialization issues with JSON

## [v1.0.0] - not yet released

- Remove autocompletion for namespaces

## [v2.0.0-M1] - 2017-07-03

Intermediate milestone build for the OpenTOSCA eco system.

### Changed

- Adaptions required by the IP check
- Enfore `LF` line endings in the repository
- Add support of [XaaS Packager](http://eclipse.github.io/winery/user/XaaSPackager)

## Initial Code Contribution - 2014-03-27

This was the initial code contribution when handing over project governance to the Eclipse Software Foundation.
See also [CQ 7916](https://dev.eclipse.org/ipzilla/show_bug.cgi?id=7916).

[unreleased]: https://github.com/eclipse/winery/compare/v2.0.0-M1...master
[v2.0.0-M1]: https://github.com/eclipse/winery/compare/initial-code-contribution...v2.0.0-M1
[v1.0.0]: https://github.com/eclipse/winery/compare/initial-code-contribution...v1.0.0
