# TestVagrant Assignment

The framework is a TestNG based test automation framework. It provides the user with the facility to easily create/write both Selenium driven UI tests & handle API calls.

If the user wants to create a new Selenium/UI test then s(he) needs to just inherit from the BaseTestNGTest class.

The configurations of the above two get picked from the frameworkConfig.properties file in src/main/resources folder. The config file provides/contains the basic properties. Having said that some of them can be overriden from the commandline. They are as below:

1. BROWSER - chrome/firefox/internetexplorer
2. headless - whether you want to run the local browser in headless mode or not. 'true' is the default value

if the above are not supplied then they default to the values provided in frameworkConfig.properties file.

The usage is as follows:

`mvn clean test -Dheadless=false -DBROWSER=chrome`

In case the user doesn't want to specify all or any of the above, you may use any of the relevant profiles. For example, to run the process on the dev environment with driver type local and browser chrome, all the user has to do is:

`mvn clean test`