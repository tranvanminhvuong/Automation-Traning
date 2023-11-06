****# Selenium with TestNG
1. Selenium Java
2. TestNG
3. webdrivermanager
4. Report: Extent Report
5. Assertions: hamcrest
6. Logging: Log4j

## Installation
```bash
# Build dependencies and not run test
mvn clean install -DskipTests
# Build dependencies and run test
mvn clean install

```

## Structure
```bash
++ src/test/java/com.nashtech.assetmanagement
    ++ pages/  # folder contains page objects, it can be grouped into sub-folder by feature/project
      ++ shared       # where to store common page class
          ++ ModalDialog
          ++ Calendar
      ++ BasePage    # where to store selenium APIs/method
    ++ tests/     # where to store test suites, testcases
    ++ utils      # folder stores common helper method

++ src/test/resource  # data (config data, test data ..)    
   ++ configs/env
          ++ dev.properties # configuration of system environments
   ++ testdata/       # where to store data excel, csv ...
   ++ testsuite   

++ logs       # store log file to debug        

++ test-output     # store test report
```

## Usage
```bash
# Run test
mvn test
# Build dependencies and run test
mvn clean install
```
## References
1. Selenium Java
+ https://www.selenium.dev/documentation/
2. Extent Report
+ https://www.extentreports.com/docs/versions/5/java/index.html
+ https://www.extentreports.com/docs/versions/5/java/plugins.html
+ https://github.com/extent-framework/extentreports-testng-adapter
+ https://github.com/grasshopper7/extentreports-cucumber7-adapter

### Notes
This framework for beginning user to start with automation testing