package VaibhavFrameWorkDesign.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import VaibhavFrameWorkDesign.resources.ExtentReportNG;

// this is one of the interface provided by TestNG.
public class Listeners extends BaseTest implements ITestListener{

	//you can log in information like Pass or Fail using test object  which is getting created when it reaches to onTestStart method.
	ExtentTest test;
	
	//calling static getReportObject method using ExtentReportNG classname and it return object of ExtentReports.
	ExtentReports extent = ExtentReportNG.getReportObject();
	
	//Lec 178:- we are placing extentTest objects which is "test", in threadLocal object. so the type is ExtentTest.
	ThreadLocal<ExtentTest> extentThread =new ThreadLocal<ExtentTest>();
	
	// before running any TestNG test your code control first will reach to this block.
	//this result variable hold all the information about that test like all test info and even of that tc driver . So using this variable we have to smartly grab the name of the test
	public void onTestStart(ITestResult result) {
	
		//getting the method name to make entry in report for test which is executing.
		test = extent.createTest(result.getMethod().getMethodName());
		extentThread.set(test); //unique thread id for each test to avoid concurrency issue while executing test parallelly.
	  }

	  /**
	   * Invoked each time a test succeeds.
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#SUCCESS
	   */
	public void onTestSuccess(ITestResult result) {
		//extentThread.get() is used to get the exact test object which is created in "onTestStart" for each test and pushed into threadlocal 
		extentThread.get().log(Status.PASS, "Test Case Passed");
		// before :- test.log(Status.PASS, "Test Case Passed");
	  }

	 //lec 177
	public void onTestFailure(ITestResult result) {
		//test.log(Status.FAIL, "Test Case Passed");
					//OR
	//before:- test.fail(result.getThrowable()); //you will get exact error msg why test got failed.
		extentThread.get().fail(result.getThrowable()); //Lec 178:-if you want to exactly use testobject "test" which is created for each test teh use extentThread.get(). 
		
		try {
			/* Here we are fetching the driver of test which is executing.
			 * getTestClass() --> it will first go to XML and get that class the test is referring.
			 * getRealClass() -->it will actually go to the actual real class.
			 * getField("driver")   --> get the field of driver, from that class, Fields are associated in class level but not method level.
			 * */
				driver  = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
		
		String filePath = null;
		try {
			//Using driver which fetch specific for each Test case ,using it will capture the screenshot for that particular test on the browser.
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// if screenshot does not exist, it prints in the output, saying that there is no path present.
			e.printStackTrace();
		}
		
		//before: -test.addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
		extentThread.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	  }

	
	public void onTestSkipped(ITestResult result) {
	    // not implemented
	  }

	/**
	   * Invoked each time a method fails but has been annotated with successPercentage and this failure
	   * still keeps it within the success percentage requested.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   * @see ITestResult#SUCCESS_PERCENTAGE_FAILURE
	   */
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	    // not implemented
	  }

	  /**
	   * Invoked each time a test fails due to a timeout.
	   *
	   * @param result <code>ITestResult</code> containing information about the run test
	   */
	public void onTestFailedWithTimeout(ITestResult result) {
	    onTestFailure(result);
	  }

	  /**
	   * Invoked before running all the test methods belonging to the classes inside the &lt;test&gt;
	   * tag and calling all their Configuration methods.
	   *
	   * @param context The test context
	   */
	public void onStart(ITestContext context) {
	    // not implemented
	  }

	  /**
	   * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have
	   * run and all their Configuration methods have been called.
	   *
	   * @param context The test context
	   */
	public void onFinish(ITestContext context) {
	    //after executn you have to flush the extent report after finish.
		extent.flush();
	  }
	
}
