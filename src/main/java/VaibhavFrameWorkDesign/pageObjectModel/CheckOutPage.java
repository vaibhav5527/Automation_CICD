package VaibhavFrameWorkDesign.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class CheckOutPage extends AbstractComponents{

	 WebDriver driver;
	 //Section 19, Lec 159
	public CheckOutPage(WebDriver driver) {
		super(driver); 
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		}
	
	
	//pagefactory
	@FindBy(xpath ="//input[@placeholder='Select Country']")  
	WebElement Country;  //List of Cart Webelement
	
	@FindBy(xpath="//button[contains(@class,'ta-item')][2]")
	WebElement selectCountry;
	
	@FindBy(css=".action__submit")
	WebElement submit;

	By result = By.cssSelector(".ta-results");
	
	public void selectCountry(String countryName)
	{
		Actions s = new Actions(driver);
		s.sendKeys(Country,countryName).build().perform();
		waitForElementToAppear(result);
		selectCountry.click();
		
	}
	
	
	public ConfirmationPage submitOrder()
	{
		submit.click();
		return new ConfirmationPage(driver);
	}
	


	
	
}
