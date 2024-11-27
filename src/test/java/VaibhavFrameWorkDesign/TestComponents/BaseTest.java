package VaibhavFrameWorkDesign.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import VaibhavFrameWorkDesign.pageObjectModel.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LandingPage Obj1; // defining here because it can accessed in child class StandAloneTestPOM

//created BaseTest, which sets browser configuration details and global properties or exclusively to store all reusable content.just like abstractcomponentUtility
	public WebDriver initializeDriver() throws IOException {

		// in Java there is one class called properties. So that properties class can
		// read the global properties.
		// this properties class can read the properties and extract all global
		// parameter values. which is mentioned in key-value pair in
		// GlobalData.properties
		Properties prop = new Properties();

		// convert your file into input stream object.
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
				+ "//src//main//java//VaibhavFrameWorkDesign//resources//GlobalData.properties");
		// System.getProperty("user.dir") --> your system project location path, it will
		// retrieve. C:\Users\vaibhav
		// belsare\eclipse-workspace_New\SeleniumFrameworkMyVersion

		prop.load(fis); // global data .properties file, you have to load here and it accepts argument
						// in the form of InputStream.
		
		//lec 181 to read a browser value which is coming from Maven command from terminator. This is a system-level property whatever you send from Maven, these are also treated as system-level variables.
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser"); //java iternary concept)
	
		//	String browserName = prop.getProperty("browser"); // Now extract any value from this global property file,
															// Key-value pair

		if (browserName.contains("chrome")) {
			ChromeOptions option = new ChromeOptions();						// Writing this code here insted of standaloneTestPOM.java file because this
			WebDriverManager.chromedriver().setup();						// code is reusable
			
			if (browserName.contains("headless"))
			{
				//To run the Tc in headless mode using chromeoption.
				option.addArguments("headless");
			}
			driver = new ChromeDriver(option);
			driver.manage().window().setSize(new Dimension(1400,900));  //full screen for headless mode
		
		} else if (browserName.equalsIgnoreCase("FireFox")) {
			// firefix code webdriver
		}
		// common code
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		return driver;

	}
	
	public List<HashMap<String, String>> getJsonDataMap(String filePath) throws IOException
	{
		//readFileToString method which will JSON read file, it will scan the entire content of your JSON and convert that into one string variable. readFileToString provided by fileutils package,
		//StandardCharsets.UTF_8  --> formating of string
		String jsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);
		
		//String To HashMap --> Jackson dataBind dependency add in POM.xml
		//objectmapper class which can convert your JSON content to hash map.
		ObjectMapper mapper = new ObjectMapper();
		
		//First argument is the string variable which I want to convert into hash map.
		// second argument you need to tell how you want to convert it
		//readValue --> creates two hash maps and it will create a list and put those two hash maps
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>(){
		});
		return data; //[{map},{map1}]
	}
		
	//args driver is coming from listeners, to give the life to each test.
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		//cast TakesScreenshot to this driver object. Driver is something which have control on your browser. So driver know that it has to take a picture now.
		TakesScreenshot ts = (TakesScreenshot)driver;
		//taking the screenshot and Storing it as file
		File source = ts.getScreenshotAs(OutputType.FILE);
		//defining the the file where you want to store
		File filepath = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(source, filepath);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		
	}

	@BeforeMethod(alwaysRun=true) // needs to get executed every time before excuting any Test/method from // StandAloneTestPOM.java				
	public LandingPage launchApplication() throws IOException {
		WebDriver driver = initializeDriver();
		Obj1 = new LandingPage(driver);
		Obj1.goToURL();
		return Obj1;
	}

	
	 @AfterMethod (alwaysRun=true)
	 public void terminateDriver() { 
		 driver.quit(); 
		 }
	 
}
