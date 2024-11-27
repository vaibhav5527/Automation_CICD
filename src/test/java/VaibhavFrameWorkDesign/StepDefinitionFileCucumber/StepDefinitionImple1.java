package VaibhavFrameWorkDesign.StepDefinitionFileCucumber;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import VaibhavFrameWorkDesign.TestComponents.BaseTest;
import VaibhavFrameWorkDesign.pageObjectModel.CartPage;
import VaibhavFrameWorkDesign.pageObjectModel.CheckOutPage;
import VaibhavFrameWorkDesign.pageObjectModel.ConfirmationPage;
import VaibhavFrameWorkDesign.pageObjectModel.LandingPage;
import VaibhavFrameWorkDesign.pageObjectModel.ProductCatalogue;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImple1 extends BaseTest{
	
	LandingPage landingPage;
	ProductCatalogue productCatalogue; 
	ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerce Page")
	public void I_landed_on_Ecommerce_Page() throws IOException
	{
		landingPage = launchApplication();
	}
	
	//regular expression (.+) --> it accepts any type of parameter and cap (^) and with dollar ($) to represent that entire string is regular expression.
	@Given("^Logged in with username (.+) and password (.+)$")
	public void Logged_in_username_and_password(String userName, String passWord)
	{
		productCatalogue = landingPage.loginApplication(userName, passWord);
	}
	
	@When("^I add product (.+) to cart$")
	public void add_Product_To_Cart(String productName)
	{
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addToCart(productName);
	}
	
	//for and we can @And or @When both works, and it depends on with what previous step "And" is used. if its used in Given or then then you can use @And only.
	@When("^Checkout (.+) and submit the order$")
	public void checkout_and_submit_order(String productName)
	{
		CartPage cartpage = productCatalogue.goToCartPage(); 
		Boolean match = cartpage.getMatchProductCart(productName);
		Assert.assertTrue(match);	
		CheckOutPage checkoutPage = cartpage.goToCheckOut();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}
	
	//if data or parameter directly coming from the step itself (not example section) from featurefile then you have mentioned type of data with curly braces. Ex. {string}
	@Then("{string} message is displayed on ConfirmationPage")
	public void message_displayed_ConfirmationPage(String strMsg)
	{
		String confirmMsg = confirmationPage.getConfirmationMsg();		
		Assert.assertTrue(confirmMsg.equalsIgnoreCase(strMsg));
	}
	
	//Errorvalidation 
	 @Then("{string} message is displayed")
	    public void something_message_is_displayed(String strArg1) throws Throwable {
	  
	    	Assert.assertEquals(strArg1, landingPage.getLoginErrorMsg());
	    	driver.close();
	    }	
}
