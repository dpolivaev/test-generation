Strategy-driven test generation
===============================


Test suites should systematically and automatically execute and evaluate systems with considerably big state space for systematic testing of complex software systems, It makes their development very challenging and expansive. Testers have to implement or generate test scripts containing thousands of isolated executable test cases. Each single test case should be so simple enough so that test results can be easily evaluated. Often it makes code duplication in the test code unavoidable. If every test script is written by a human the costs for test code maintenance by specification changes can explode. The aim of this project is to give testers a tool for efficient, scalable, flexible and redundancy free implementation of automatically executable test suites. The tool also helps to estimate the test coverage achieved by the generated test suite.

Application of this tool is demonstrated by example of developing a test suite for parking cost calculator. This example was used in numerous testing community activities and testing dojos

A short introduction to the used approach.
=========================================

Usually a test case consists of three parts named Arrange, Act and Assert. They arrange all necessary preconditions and inputs, act on the system under test (SUT) and assert that the expected results have occurred (see http://c2.com/cgi/wiki?ArrangeActAssert). They can be followed by a fourth part which changes the SUT state so that next test can run. Further I call the parts as preconditions, focus, verification and post-processing. The whole test case variety comes from variety of preconditions, focuses, verifications and the related post-processing which can differ in the used calls and their parameters. For complex test suites tests can be grouped in different categories.

For instance in order to test the park calculator there are preconditions necessary to select the parking lot and enter the entry and leaving times, the test focus runs the calculation, the verification compares the result with the expectation, and no post-processing is needed. In our example all test cases are split in categories “success” and “failure”.

Test strategies describe this variety using an abstraction of test properties. Test property is a key-value pair. It can describe anything e.g. test categories, method calls, their parameters, test categories, expected results or commented references to covered requirements used for test coverage analysis. The test generator builds all relevant value combinations of different properties considering dependencies between them and maps each such combination into a test script.

The test strategy focuses on specifying variety and dependencies of the test properties. Some properties are mapped to the test script elements and to the covered specification requirements by convention. So properties with names starting with “pre”, “foc”, “veri” and “post” followed by a number like “pre1” or “pre2” are mapped to method calls used in the corresponding test fragments. Properties named like “pre1.x” are transformed to the method arguments with corresponding names.  The methods themselves must be implemented by a test developer in a separate test driver module responsible for all communications with the test target. Their signatures are completely defined by the test strategy in a TDD – like fashion.

For calculation of expected results used in the verification and for estimation of the test coverage separate implementations of the SUT logic called models can be helpful. Our DSL based on Xtext and Xbase (http://www.eclipse.org/Xtext/) offers special elements for code coverage tracking. In addition strategy properties with names inclosed in rectangular brackets are interpreted as links to the covered requirements. It makes possible to create requirement coverage reports for each test generation run.
The generated test script is internally generated as XML first. It can be transformed to arbitrary programming language using  a XSLT transformation. Example transformations to Java with JUnit and C with cmocka are already integrated in the library.

Getting started
===============
The test generation framework can be either installed from update site archive or compiled from the source code licensed under LGPL 3 or later. Compilation from the source is explained later.

1. Run eclipse with the test generation plugin and  import project from folder test-generation-examples into the eclipse workspace.
2. Open file /ParkCalculatorExample/strategies/ParkCalculatorStrategy.tsgen` in editor. Make sure it is the special editor for the strategy dsl files with syntax highlighting and outline.
3. Check that derived java files containg strategy classes are created in folder /ParkCalculatorExample/src-gen.
4. Right-click on `ParkCalculatorStrategy.tsgen` ans select "Generate tests". It should run the generation. Check the output printed on the console
5. Find generated test script `ParkCalculatorTest.java` in directory `/ParkCalculatorExample/generated-tests` and generated requirement coverage `report.xml` in directory `/ParkCalculatorExample/output/`. There is also a generated test script in xml format `output/parkcalculator/ParkCalculatorTest.xml`.

Strategy definition DSL is explained in the next chapters using two example projects.

Strategy DSL syntax reference
============================

Project `TestGenerationDSLSyntax` contains basic examples of strategy definition syntax. It demonstrates how to specify different kinds of dependencies between the test properties.

The strategy and is defined in directory `strategies` in file `StrategySyntaxExamples.tsgen`.

Running the strategy generates console output showing generated combinations of property values.

Park calculator example
===============================

Application of this tool is demonstrated by example of developing a test suite for parking cost calculator similar to the one from http://www.grr.org/ParkCalc.php. It's specification comes from http://www.grr.org/ParkingRates.php . This example was used in numerous testing community activities and testing dojos e.g. http://www.shino.de/2010/06/20/parkcalc-automation-getting-started/.  It is a web application. For demonstration purposes I implemented its logic in java and used only strings as parameters so that it feels very close to calls of internet URLs. To emulate a web page where all values can be entered separately there are setter methods corresponding to each input.

The complete tests should check all subsystems of the calculator and consider date and time parsing. They should also make sure that the calculator correctly handles winter and summer time. It makes not only the difference between the entry and leaving times but also the absolute time values and their format relevant for the test.

This project defines test strategy using one test model and the corresponding test driver. It tracks model code coverage and requirement coverage and outputs the tests as JUnit tests. The parking rate specification is given in file `requirements/parkingRates.md`

The strategy and the model are defined in directory `strategies` in file `ParkCalculatorStrategy.tsgen`, the test driver in directory `test-driver/parkcalculator`.

The framework automatically translates tsgen files to java files in directory `src-gen`. The last are run to generate the tests. The generation result is placed in directory `generated-tests` . It is also under version control that allows to see the generation results without executing the generation.

The test-driven implementation of the parking costs calculator with developer tests allows the execution of the generated tests against this code. The source code is contained in directory src, and the developer tests in directory developer-tests.

For compiling and running the tests the project build path should be added library "Test generation library" which is installed with the framework plug-ins.


Framework and eclipse plug-in compilation from the source code
=========================================
1. Install the latest eclipse with XText sdk plugin and maven
2. Create eclipse classpath variable M2_REPO pointing to your maven repository
3. Run mvn test in folder test-generation-engine.
4. Import all projects from folders test-generation-engine and and testgeneration-dsl
5. Right-click on file `/org.dpolivaev.dsl.tsgen/src/org/dpolivaev/dsl/tsgen/Strategy.xtext` and select "Run As -> Generate XText Artifacts"
6. Run eclipse application