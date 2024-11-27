package VaibhavFrameWorkDesign.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {
	
	public static ExtentReports getReportObject()
	{
				// define path where reposrts will be stored
				String path = System.getProperty("user.dir")+"\\reports\\index.html";
				//this ExtentSparkReporter , responsible for creating report.
				//object of ExtentSparkReporter is responsible to make all configurations for your report.
				ExtentSparkReporter reporter = new ExtentSparkReporter(path);
				reporter.config().setReportName("Web Automation Selenium Reports");
				reporter.config().setDocumentTitle("Test Results");
				
				/*ExtentSparkReporter is a helper class, which is helping to create some configuration, and that will finally report to its main class ExtentReports.
				so there is a method called attachReporter. so once you create report, 
				you have to attach it to your main class,and you have to give the object of ExtentSparkReporter. */
				ExtentReports extent = new ExtentReports();
				extent.attachReporter(reporter);
				
				//setting up Tester name
				extent.setSystemInfo("Tester", "Vaibhav B");
				return extent;
			
	}

}
