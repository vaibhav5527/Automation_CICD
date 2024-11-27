package VaibhavFrameWorkDesign.SeleniumFrameworkMyVersionTests;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import VaibhavFrameWorkDesign.TestComponents.BaseTest;
import VaibhavFrameWorkDesign.pageObjectModel.CartPage;
import VaibhavFrameWorkDesign.pageObjectModel.CheckOutPage;
import VaibhavFrameWorkDesign.pageObjectModel.ConfirmationPage;
import VaibhavFrameWorkDesign.pageObjectModel.LandingPage;
import VaibhavFrameWorkDesign.pageObjectModel.OrderPage;
import VaibhavFrameWorkDesign.pageObjectModel.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTestPOM extends BaseTest{

	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData",groups="PurchaseOrder")
	public void submitOrder(HashMap<String,String> input) throws IOException{
		
		//Section 18
		//WebDriverManager.chromedriver().setup();
		//WebDriver driver= new ChromeDriver();
		
		//driver.manage().window().maximize();
		//put some implicit weight on global level so that way if you have any sync issues
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//Part 1, create object of LandingPage class (adding this code in Base Test because first launch application is required. 
		//LandingPage Obj1 =  new LandingPage(driver); 
		//Obj1.goToURL();
		//LandingPage Obj1 = launchApplication();  //doesnt required bcoz getting this object (LandingPage Obj1 ) from parent class BaseTest
		
		ProductCatalogue productCatalogue = Obj1.loginApplication(input.get("email"), input.get("password"));
		
		/*rather than creating obj of every page, we know after performing action it will go the next page,
		then create the same obj for that page in that action method. 
		after click on login button it goes to product page so creating the object in loginApplication method*/
        //ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addToCart(input.get("productName"));
		CartPage cartpage = productCatalogue.goToCartPage(); //accessing the method of parent using child obj and creating obj of cartpage 
		
		
		//CartPage cartpage = new CartPage(driver); No Need creating obj in above method
		Boolean match = cartpage.getMatchProductCart(input.get("productName"));
		Assert.assertTrue(match); //Validation never write in POM files, It must be in Test File Only.
		
		CheckOutPage checkoutPage = cartpage.goToCheckOut();
		checkoutPage.selectCountry("india");
		
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMsg = confirmationPage.getConfirmationMsg();		
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		
		//driver.close(); defining in after Method
		
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"}) //above test should get executed first before verifying the product is in the order page or not. because above method is placing the order and here we are veifying the same product is visisble in ordere page or not
	public void orderHistoryTest()
	{
		ProductCatalogue productCatalogue = Obj1.loginApplication("VBelsare@gmail.com", "Vbelsare1");
		OrderPage orderPage = productCatalogue.goToOrderPage();
		Boolean match = orderPage.verifyOrderedItemsDisplay(productName);
		Assert.assertTrue(match); 
	}
	

	
	@DataProvider
	public Object[][] getData() throws IOException 
	{
		//method 3--> using Json
		List<HashMap<String,String>> jsonData= getJsonDataMap(System.getProperty("user.dir")+"\\src\\test\\java\\VaibhavFrameWorkDesign\\Json_Data\\PurchaseOrder.json");
		return new Object[][] { {jsonData.get(0)}, {jsonData.get(1)} }; //fetching the Map using indexes from List bcoz two maps are present at 0 and 1 indexes.
	}
	
	 /* @DataProvider public Object[][] getData() { 
	 * //Method 2 using HashMap
	 * HashMap<String,String> map = new HashMap<String,String>(); map.put("email",
	 * "VBelsare@gmail.com"); map.put("password", "Vbelsare1");
	 * map.put("productName", "ZARA COAT 3");

	 * HashMap<String,String> map1 = new HashMap<String,String>(); 
	 * map1.put("email", "king12@gmail.com"); 
	 * map1.put("password", "King1234");
	 * map1.put("productName", "ADIDAS ORIGINAL"); 
	 
	 * return new Object[][] {{map},{map1}};
	 * public void submitOrder(HashMap<String,String> input) throws IOException{
	 * }
	 */
	
	/*
	* @DataProvider public Object[][] getData() 
	* { 
	* //Method 1 using Object array :-
	* return new Object[][] {{"VBelsare@gmail.com","Vbelsare1","ZARA COAT 3"},{"king12@gmail.com",  "King1234","ADIDAS ORIGINAL"}}; 
	* //public void submitOrder(String email,  String password,String productName) throws IOException{ } 
	* }
	*/
}
