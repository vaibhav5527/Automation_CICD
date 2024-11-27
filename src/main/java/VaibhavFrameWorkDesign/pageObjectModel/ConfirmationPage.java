package VaibhavFrameWorkDesign.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class ConfirmationPage extends AbstractComponents{

	 WebDriver driver;
	 //Section 19, Lec 159
	public ConfirmationPage(WebDriver driver) {
		super(driver); 
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		}
	
	
	//pagefactory
	@FindBy(css =".hero-primary")  
	WebElement confirmMsg;  //List of Cart Webelement

	public String getConfirmationMsg()
	{
		waitForWebElementToAppear(confirmMsg);
		return confirmMsg.getText();
		
	}
	
	



	
	
}
