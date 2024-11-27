package VaibhavFrameWorkDesign.pageObjectModel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import VaibhavFrameWorkDesign.AbstractComponentsUtility.AbstractComponents;

public class OrderPage extends AbstractComponents{

	 WebDriver driver;
	 //Section 19, Lec 159
	public OrderPage(WebDriver driver) {
		super(driver); 
		//driver initialisation
		this.driver = driver;
		PageFactory.initElements(driver, this); 
		}
	

	//pagefactory
	@FindBy(xpath ="//tr[@class='ng-star-inserted'] /td[2]")  
	private List<WebElement> orderedItems;  //List of Cart Webelement
	
	
	public Boolean verifyOrderedItemsDisplay(String productName)
	{
		Boolean match = orderedItems.stream().anyMatch(orderedItem -> orderedItem.getText().equals(productName));
		return match;
	}
	


	
	
}
