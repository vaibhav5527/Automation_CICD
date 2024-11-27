package VaibhavFrameWorkDesign.SeleniumFrameworkMyVersionTests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import VaibhavFrameWorkDesign.pageObjectModel.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Section 18
		WebDriverManager.chromedriver().setup();
		WebDriver driver= new ChromeDriver();	
		String productName = "ZARA COAT 3";
		
		driver.manage().window().maximize();
		//put some implicit weight on global level so that way if you have any sync issues
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		
		driver.get("https://rahulshettyacademy.com/client");
		driver.findElement(By.id("userEmail")).sendKeys("VBelsare@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Vbelsare1");
		driver.findElement(By.id("login")).click();        
		
		WebDriverWait wait= new  WebDriverWait(driver,Duration.ofSeconds(5));
		//this will wait until that element is visible. once it visisble then we can confirm product is added
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.mb-3")));
		
		//Get the all products of UI
		List<WebElement> Products= driver.findElements(By.cssSelector("div.mb-3"));
		//filter out the product which you want to order
		//findFirstMethod will get you the first one product, if there are multiple result or on one with name ZARA.
		//orElse Method will return null if you don't find any product like ZARA.
		WebElement prod = Products.stream().filter(product -> product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null);
		//after applying tagname button there are two button elements but "Add to card is at last postion then "you need to go to the last button, but not the first one. for First button use "last-of-type".
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();
		
		//WebDriverWait wait= new  WebDriverWait(driver,Duration.ofSeconds(5));
		//this will wait until that element is visible. once it visisble then we can confirm product is added
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		
		//waiting for the loading element to disappear
		//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".ng-animating")));
							//OR
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//click on cart section
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		List<WebElement> cartProducts =  driver.findElements(By.cssSelector(".cartSection h3"));
		//in this case we really don't want to return that web element,  we just want to make sure if any product is equals to this name, ZARA COAT 3 or not.
		//Anymatch method If it matches for any of the product, it'll return Boolean value as written type as a true.
		//Filter :-- it'll filter and give you that complete web element so you can act anything upon it.
		//anyMatch :-- If you just want to make sure the condition matches, then just use anyMatch. So it'll return if condition matched or not.
		Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase("ZARA COAT 3"));
		//if it returns true if test case will pass
		Assert.assertTrue(match);
		
		//click on checkout
		driver.findElement(By.xpath("//li[@class='totalRow'] /button")).click();
		
		Actions s = new Actions(driver);
		s.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")),"india").build().perform();
		//after putting india in country, dyanamic options shows, so need to wait until its visible
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
		
		//india is getting at second position
		
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
	
	//	driver.findElement(By.xpath("//button[@class='ta-item list-group-item ng-star-inserted'][2]")).click();
		
		driver.findElement(By.cssSelector(".action__submit")).click();
		
		String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
		
		Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));
		
		
	}

}
