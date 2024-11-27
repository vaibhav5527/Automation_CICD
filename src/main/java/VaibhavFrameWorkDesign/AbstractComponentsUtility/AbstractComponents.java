package VaibhavFrameWorkDesign.AbstractComponentsUtility;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import VaibhavFrameWorkDesign.pageObjectModel.CartPage;
import VaibhavFrameWorkDesign.pageObjectModel.OrderPage;

public class AbstractComponents {
	
	WebDriver driver;
	
	public AbstractComponents(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css="[routerlink*='cart']")
	WebElement addToCart;
	
	@FindBy(css="[routerlink*='myorders']")
	WebElement goToOrder;
	
	public void waitForElementToAppear(By byLocator)
	{
		WebDriverWait wait= new  WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
	}
	
	public void waitForWebElementToAppear(WebElement webLocator)
	{
		WebDriverWait wait= new  WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(webLocator));
	}
	
	public void waitForElementToDisappear(WebElement webLocator)
	{
		WebDriverWait wait= new  WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.invisibilityOf(webLocator));
	}
	
	public CartPage goToCartPage() //putting this method in common class because header button (cart, Home, Orders etc) is common to all pages.
	{
		addToCart.click();
		return new CartPage(driver);
		
	}
	public OrderPage goToOrderPage() //putting this method in common class because header button (cart, Home, Orders etc) is common to all pages.
	{
		goToOrder.click();
		return new OrderPage(driver);
		
	}

}
