Strategy-driven test generation
===============================

Test suites developed for complex software systems contain thousands of test cases. Keeping track on the test coverage and changing the test suite as the system requirements evolve can consume significant efforts. Testers have to implement or generate test scripts containing thousands of isolated executable test cases. Each single test case should be so simple enough so that test results can be easily evaluated. Often it makes code duplication in the test code unavoidable. If every test script is written by a human the costs for test code maintenance by specification changes can explode. The aim of this project is to give testers a tool for efficient implementation of scalable and flexible test suites by writing redundancy free test specifications. The technique allows complete and explicit control over test amount, test depth and test coverage. 

Application of this tool is demonstrated by examples including tests of a log-in page and tests of parking rate calculator.

A short introduction to the used approach.
=========================================
The test suites are generated from test specification files describing test categories, test steps, test input data variations and requirement coverage criteria. All these kinds of data are commonly referred to as test properties. Test property is a key-value pair. It can describe anything e.g. test categories, method calls, their parameters, test categories, expected results or commented references to covered requirements used for test coverage analysis. The test generator builds all relevant value combinations of different properties considering dependencies between them and maps each such combination into a test case script.

Dependencies and variations of test properties in generated test suites are defined in test strategies. The test strategies are expressed in a test specification DSL which allows to express complex dependencies in a concise and easily understandable way. Behind the scene there is a rule engine generating test property value combinations from the test strategy definitions. 

The test suites containing independently executable test cases can be generated in any programming or scripting language or as a text for a human reader. The generator uses a generic and configurable algorithm for mapping of test properties to the test scripts based on property naming conventions. The generated test script is internally generated as XML first. It can be transformed to arbitrary programming language using  a XSLT transformation. Example transformations to Java with JUnit and C with cmocka are already integrated in the library.

For calculation of expected results and evaluation of code coverage separate test oracle modules can be supplied. For automatic test case execution a separate test driver component containing definition of single test steps referenced by the strategy should be written in the chosen test script language. The DSL based on Xtext and Xbase (http://www.eclipse.org/Xtext/) offers special elements for code coverage tracking. In addition strategy properties with names inclosed in rectangular brackets are interpreted as links to the covered requirements. It makes possible to create requirement coverage reports for each test generation run.

Separate implementation of test oracles, test strategies, test script writers and test drivers allows clear and clean design and high reuse of the test specification elements. It offers natural ways to distribute the work among different people. 

Getting started
===============
The test generation framework can be either installed from update site archive or compiled from the source code licensed under LGPL 3 or later. Compilation from the source is explained later.

1. Run eclipse with the test generation plugin and  import project from folder test-generation-examples into the eclipse workspace.
2. Open file /ParkCalculatorExample/strategies/ParkCalculatorStrategy.testspec` in editor. Make sure it is the special editor for the strategy dsl files with syntax highlighting and outline.
3. Check that derived java files containg strategy classes are created in folder /ParkCalculatorExample/src-gen.
4. Right-click on `ParkCalculatorStrategy.testspec` ans select "Generate tests". It should run the generation. Check the output printed on the console
5. Find generated test script `ParkCalculatorTest.java` in directory `/ParkCalculatorExample/generated-tests` and generated requirement coverage `report.xml` in directory `/ParkCalculatorExample/output/`. There is also a generated test script in xml format `output/parkcalculator/ParkCalculatorTest.xml`.

Strategy definition DSL is explained in the next chapters using two example projects.

Test generation tutorial
==========================
A tutorial demonstrating the test generation on an easy example of testing a log-in page is available in a at https://github.com/dpolivaev/test-generation/tree/master/test-generation-examples/LoginPage-Tutorial

Strategy DSL syntax reference
============================

Project `TestGenerationDSLSyntax` contains basic examples of strategy definition syntax. It demonstrates how to specify different kinds of dependencies between the test properties.

The strategy and is defined in directory `strategies` in file `StrategySyntaxExamples.testspec`.

Running the strategy generates console output showing generated combinations of property values.

Park calculator example
===============================

Application of this tool is demonstrated by example of developing a test suite for parking cost calculator similar to the one from http://www.grr.org/ParkCalc.php. It's specification comes from http://www.grr.org/ParkingRates.php . This example was used in numerous testing community activities and testing dojos e.g. http://www.shino.de/2010/06/20/parkcalc-automation-getting-started/.  It is a web application. For demonstration purposes I implemented its logic in java and used only strings as parameters so that it feels very close to calls of internet URLs. To emulate a web page where all values can be entered separately there are setter methods corresponding to each input.

The complete tests should check all subsystems of the calculator and consider date and time parsing. They should also make sure that the calculator correctly handles winter and summer time. It makes not only the difference between the entry and leaving times but also the absolute time values and their format relevant for the test.

This project defines test strategy using one test model and the corresponding test driver. It tracks model code coverage and requirement coverage and outputs the tests as JUnit tests. The parking rate specification is given in file `requirements/parkingRates.md`

The strategy and the model are defined in directory `strategies` in file `ParkCalculatorStrategy.testspec`, the test driver in directory `test-driver/parkcalculator`.

The framework automatically translates testspec files to java files in directory `src-gen`. The last are run to generate the tests. The generation result is placed in directory `generated-tests` . It is also under version control that allows to see the generation results without executing the generation.

The test-driven implementation of the parking costs calculator with developer tests allows the execution of the generated tests against this code. The source code is contained in directory src, and the developer tests in directory developer-tests.

For compiling and running the tests the project build path should be added library "Test generation library" which is installed with the framework plug-ins.


Framework and eclipse plug-in compilation from the source code
=========================================
1. Install the latest eclipse with XText sdk plugin and maven
2. Create eclipse classpath variable M2_REPO pointing to your maven repository
3. Run mvn test in folder test-generation-engine.
4. Import all projects from folders test-generation-engine and and testgeneration-dsl
5. Right-click on file `/org.dpolivaev.dsl.testspec/src/org/dpolivaev/dsl/testspec/Strategy.xtext` and select "Run As -> Generate XText Artifacts"
6. Run eclipse application