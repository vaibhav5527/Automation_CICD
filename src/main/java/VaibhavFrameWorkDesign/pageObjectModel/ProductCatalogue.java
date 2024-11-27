package VaibhavFrameWorkDesign.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class ProductCatalogue extends AbstractComponents{

	 WebDriver driver;
	 //Section 19, Lec 159
	public ProductCatalogue(WebDriver driver) {
		// TODO Auto-generated constructor stub
		super(driver); //sending driver to constructor of parentclass AbstractComponents (Every child to send the parameter to the constructor of parent through super if its extending)
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this);  /* this initElements will trigger initializing all the elements.
												It takes the driver's argument and then uses "this" driver to initialize,*/
		}

	//List<WebElement> Products= driver.findElements(By.cssSelector("div.mb-3"));
	
	//pagefactory
	@FindBy(css ="div.mb-3")  //findElements(By.cssSelector("div.mb-3"));
	List<WebElement> ProductsList;  //List of webelement
	
	@FindBy(css =".ng-animating")  //findElements(By.cssSelector(".ng-animating"));
	WebElement loadingScreen;  
	
	By productlocator = By.cssSelector("div.mb-3"); //explicity mentioning Bylocator bcoz it has same locator for all productslist.
	By addtoCartBtn = By.cssSelector(".card-body button:last-of-type");
	By successAddToCartMsg = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productlocator); //this method accepts BYLocator which is "By.cssSelector("div.mb-3")"
		return ProductsList;
		
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement productbyName = getProductList().stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return productbyName;
	}
	
	public void addToCart(String productName)
	{
		getProductByName(productName).findElement(addtoCartBtn).click();
		waitForElementToAppear(successAddToCartMsg);
		waitForElementToDisappear(loadingScreen);	
	}
	


	
	
}
