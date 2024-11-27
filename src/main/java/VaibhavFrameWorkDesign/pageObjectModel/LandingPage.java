package VaibhavFrameWorkDesign.pageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class LandingPage extends AbstractComponents {

	 WebDriver driver;
	 //Section 19, Lec 158
	public LandingPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		super(driver); //sending driver to constructor of parentclass AbstractComponents
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this);  /* this initElements will trigger initializing all the elements.
												It takes the driver's argument and then uses "this" driver to initialize,*/
		}

	//WebElement userEmail = driver.findElement(By.id("userEmail")).sendKeys("VBelsare@gmail.com");
	
	//pagefactory
	//You can mention css, xpath, name, className, tagName, linkText, partialLinkText just like id
	@FindBy(id ="userEmail")  //findElement(By.id("userEmail"))
	WebElement userEmail;  //webelement
	
	@FindBy(id ="userPassword")  //driver.findElement(By.id("userPassword"))
	WebElement password; 
	
	@FindBy(id ="login")  //driver.findElement(By.id("login"))
	WebElement submit; 
	
	@FindBy(css="[class*='flyInOut']")
	WebElement loginErrorMsg; 
	
	public ProductCatalogue loginApplication(String email, String passKey)
	{
		userEmail.sendKeys(email);
		password.sendKeys(passKey);
		submit.click();
		return new ProductCatalogue(driver);
		
	}
	
	public void goToURL()
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
	public String getLoginErrorMsg()
	{
		waitForWebElementToAppear(loginErrorMsg);
		return loginErrorMsg.getText();
		
	}
}
