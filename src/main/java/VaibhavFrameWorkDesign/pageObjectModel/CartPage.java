package VaibhavFrameWorkDesign.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class CartPage extends AbstractComponents{

	 WebDriver driver;
	 //Section 19, Lec 159
	public CartPage(WebDriver driver) {
		super(driver); //sending driver to constructor of parentclass AbstractComponents (Every child to send the parameter to the constructor of parent through super if its extending)
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this);  /* this initElements will trigger initializing all the elements.
												It takes the driver's argument and then uses "this" driver to initialize,*/
		}
	
	
	//pagefactory
	@FindBy(css =".cartSection h3")  
	List<WebElement> cartProductList;  //List of Cart Webelement
	
	@FindBy(xpath="//li[@class='totalRow'] /button")
	WebElement checkOutBtn;
	

	
	public List<WebElement> getCartProductList()
	{
		return cartProductList;		
	}
	
	public Boolean getMatchProductCart(String productName)
	{
		Boolean match = getCartProductList().stream().anyMatch(cartProduct -> 
		cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}
	
	public CheckOutPage goToCheckOut()
	{
		checkOutBtn.click();
		return new CheckOutPage(driver);
	}
	


	
	
}
