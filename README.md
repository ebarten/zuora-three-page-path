Three Page Path
===============

pre-requisites:
------------------------------------------

Java 8 installed

Build & Run
------------------------------------------
Using internal dictionary, from the source repository base directory, just run: 

_Windows_
```
gradlew.bat run
```

_Unix / Linux_
```
gradlew run
```

Deploy & Run
------------------------------------------
1. From the source repository base directory just run:

    _Windows_
    ```
   ./gradlew.bat installDist
   ```
    _Unix / Linux_
   ```
   ./gradlew installDist
   ```
2. CD to: `build/install/three-page-path/bin`
3. To execute with sample data file, type the following on:
   
    _Windows_
    ```
    three-page-path.bat ../sample-data/paths
    ```
   
   _Unix / Linux_
    ```
    ./three-page-path ../sample-data/paths
    ``` 

Design and Assumptions
------------------------------------------
The design document can be found in the repository's root directory: design-doc.docx
