package VaibhavFrameWorkDesign.SeleniumFrameworkMyVersionTests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import VaibhavFrameWorkDesign.TestComponents.BaseTest;
import VaibhavFrameWorkDesign.TestComponents.RetryFlakyTest;
import VaibhavFrameWorkDesign.pageObjectModel.CartPage;
import VaibhavFrameWorkDesign.pageObjectModel.CheckOutPage;
import VaibhavFrameWorkDesign.pageObjectModel.ConfirmationPage;
import VaibhavFrameWorkDesign.pageObjectModel.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{
								// retryAnalyzer = RetryFlakyTest.class is used to rerun the test if it fails deu to any reason i.e false fail)
	@Test(groups={"Error Handling"},retryAnalyzer = RetryFlakyTest.class) //To verify Error Msg when Login/Password is incorrect
	public void loginError() throws IOException, InterruptedException{
	
		String productName = "ZARA COAT 3";
		Obj1.loginApplication("VBelsare@gmail.com", "Vbelsare123");
		Assert.assertEquals("Incorrect email  password.",Obj1.getLoginErrorMsg());
	}

	@Test
	public void productErrorValidation() throws IOException{
		
		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = Obj1.loginApplication("king12@gmail.com", "King1234");
		
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addToCart(productName);
		CartPage cartpage = productCatalogue.goToCartPage(); 
		

		Boolean match = cartpage.getMatchProductCart("ZARA COAT 33");
		Assert.assertFalse(match); 
	
		
		
	}
}
