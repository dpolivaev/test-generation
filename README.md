Test generation
===============
## Compile and run the example 

1. Install the latest eclipse with XText sdk plugin and maven
1. Create eclipse classpath variable M2_REPO pointing to your maven repository  
1. Run `mvn eclipse:eclipse` in folder test-generation-engine to create eclipse project for the engine
1. Run `mvn install` in the same folder
1. Import all projects from folders test-generation-engine and 
and testgeneration-dsl
1. Right-click on file /org.dpolivaev.dsl.tsgen/src/org/dpolivaev/dsl/tsgen/Strategy.xtext and select  "Run As -> Generate XText Artifacts"
1. Run eclipse application
1. Import project from folder test-generation-examples into the run (or debugged) application
1. Open file /ParkCalculatorExample/testgeneration/ParkCalculatorStrategy.tsgen in editor. Make sure it is the special editor
1. Make sure that derived java files are created in folder /ParkCalculatorExample/src-gen
1. Right-click on ParkCalculatorStrategy.tsgen ans select "Generate tests" . This should run the generation.
1. Check the output printed on the console 
1. Find generated files in directory org.dpolivaev.dsl.tsgen.examples/output