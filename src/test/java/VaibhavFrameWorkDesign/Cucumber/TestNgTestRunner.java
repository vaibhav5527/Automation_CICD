package VaibhavFrameWorkDesign.Cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/VaibhavFrameWorkDesign/Cucumber",glue="VaibhavFrameWorkDesign.StepDefinitionFileCucumber",
monochrome=true,tags = "@RegessionCucumber",plugin= {"html:target/cucumber.html"})
public class TestNgTestRunner extends AbstractTestNGCucumberTests{
	

}
