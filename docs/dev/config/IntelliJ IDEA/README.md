# IntelliJ Configuration

Preparation: Generate a war to have all dependencies fetched by maven: `mvn package`

1. Install JRebel plugin
    - JRebel enables a better debugging - changes can be immediately loaded without building the whole project again
    - Download https://zeroturnaround.com/software/jrebel/
    - Get a JRebel license from <https://my.rebel.com>.
      It is for free if JRebel may post to your Twitter account.
    - File --> Settings --> Plugins --> Search for JRebel
    - If JRebel is not available press "Browse repositories" --> Search -->Install
2. Enable checkstyle: Follow the shown steps and apply them in IntelliJ
  ![Enable CheckStyle in IntelliJ](activate-checkstyle.gif)
  - Install the [IntelliJ Checkstyle Plugin](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea).
  - Open Settings > Other Settings > CheckStyle.
  - Click on the green plus and add `checkstyle.xml` from the root of the Winery code repository.
3. Open `pom.xml` in the main directory
4. Configure the code style
    1. Press <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>S</kbd>
    2. Go to "Editor > Code Style"
    3. Click "Manage..." (right of "Scheme:")
    4. Click "Import..."
    4. IntelliJ Code Style XML
    5. Navigate to  `IntelliJ Code Style.xml`. It is located inside the winery git repository under `docs/config/IntelliJ IDEA`.
    6. Press "OK"
    7. Press "OK"
    8. Press "Close"
    9. Press "OK"
6. Setup tomcat as usual. In case you develop the backend, use following configuration:
  - `org.eclipse.winery.repository.rest:war exploded` to `/winery`
  - External artifact `.../org.eclipse.winery.repository.ui/target/winery-ui.war` to `/winery-ui`
  - `org.eclipse.winery.topologymodeler:war exploded` to `/winery-topologymodeler`
  - External artifact `.../org.eclipse.winery.workflowmodeler/target/winery-workflowmodeler.war` to `/winery-workflowmodeler`

## Further Remarks

* Please let `.editorconfig` override the settings of IntelliJ
* Shortcuts
  - 2x <kbd>Shift</kbd> / <kbd>Ctrl</kbd>+<kbd>Shift</kbd>+<kbd>F</kbd> / <kbd>Ctrl</kbd>+<kbd>F</kbd>: Differrent forms of search
  - <kbd>Ctrl</kbd>+<kbd>Alt</kbd>+<kbd>O</kbd>: Organize imports (fixes checkstyle)
  - <kbd>Ctrl</kbd>+<kbd>X</kbd>: if nothing is marked: delete line and free space

## License

Copyright (c) 2017 University of Stuttgart.

All rights reserved. This program and the accompanying materials
are made available under the terms of the [Eclipse Public License v1.0]
and the [Apache License v2.0] which both accompany this distribution.

  [Apache License v2.0]: http://www.apache.org/licenses/LICENSE-2.0.html
  [Eclipse Public License v1.0]: http://www.eclipse.org/legal/epl-v10.html
