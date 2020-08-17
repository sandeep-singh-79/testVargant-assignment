# TestVagrant Assignment

The framework is a TestNG based test automation framework. It provides the user with the facility to easily create/write
both Selenium driven UI tests & handle API calls.

If the user wants to create a new Selenium/UI test then s(he) needs to just inherit from the BaseTestNGTest class.

## Deploy the code on local
```git
git clone https://github.com/sandeep-singh-79/testVargant-assignment.git
```

## Run the code

The framework supports the below commandline parameters:

1. BROWSER - chrome/firefox/internetexplorer
2. headless - whether you want to run the local browser in the headless mode or not. The default value is _true_

if the above are not supplied, they default to the values provided in frameworkConfig.properties file.

The usage is as follows:

```cmd
mvn clean test -Dheadless=false -DBROWSER=chrome
```

In case the user doesn't want to specify all or any of the above, the user may run it as below:

```cmd
mvn clean test
```

## Configure the Framework
To configure the framework, the developer might want to take a look at the file frameworkConfig.properties here:
```cmd
./src/java/resources/frameworkConfig.properties
```

## Update test data
To change the city being searched for, please update the fields _searchTerm_ and _cityName_ in the **data.properties** file.
The user may also want to update the variance criteria for temperature, humidity and wind speed. The configurable values for
them are also present in the data.properties file.
```cmd
./src/test/resources/data.properties
```