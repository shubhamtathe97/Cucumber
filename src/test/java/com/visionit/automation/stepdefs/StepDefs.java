package com.visionit.automation.stepdefs;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.visionit.automation.core.WebDriverFactory;
import com.visionit.automation.pageobjects.CmnPageObjects;
import com.visionit.automation.pageobjects.SearchPageObjects;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;

public class StepDefs {

	private static final Logger logger = LogManager.getLogger(StepDefs.class);

	WebDriver driver;
	String base_url = "https://amazon.in";
	int implicit_wait_timeout_in_sec = 20;
	Scenario scn; // this is set in the @Before method

	CmnPageObjects cmnPageObjects;
	SearchPageObjects searchPageObjects;

	// make sure to use this before import io.cucumber.java.Before;
	// Use @Before to execute steps to be executed before each scnerio
	// one example can be to invoke the browser
	@Before
	public void setUp(Scenario scn) throws Exception{
		this.scn = scn;
		//driver = new ChromeDriver();
		String browserName = WebDriverFactory.getBrowserName();
		driver = WebDriverFactory.getWebDriverForBrowser(browserName);
		logger.info("Browser invoked.");

		cmnPageObjects = new CmnPageObjects(driver, scn);
		searchPageObjects = new SearchPageObjects(driver, scn);
		
	}


	//	@Given("User open the browser")                  to comment junit lines used:- control+&
	//	public void user_open_the_browser() {            to perform multiline comment in feacture file used :- control+/
	//
	//		driver=new FirefoxDriver();
	//		driver.manage().deleteAllCookies();
	//		driver.manage().window().maximize();
	//		driver.manage().timeouts().implicitlyWait(Implicit_Wait_timeUnit_In_Sec, TimeUnit.SECONDS);
	//
	//	}

	@Given("User navigate to the home page of the application url")
	public void user_navigate_to_the_home_page_of_the_application_url() {

		
		WebDriverFactory.navigateToTheUrl(base_url);
		scn.log("Browser navigated to URL: " + base_url);
		cmnPageObjects.validateLandingPageTitle();
	}

	@When("User search for a product {string}")
	public void user_search_for_a_product(String productName) {

		//Wait and Search for product
				cmnPageObjects.searchProduct(productName);
				cmnPageObjects.clickOnSearchBtn();
	}

	@Then("Search product is displayed")
	public void search_product_is_displayed() {

		//Wait for titile
				searchPageObjects.validateSearchPageTitle();
	}

	@When("User click on any product")
	public void user_click_on_any_product() {

		//listOfProducts will have all the links displayed in the search box
				searchPageObjects.clickOnFirstProd();
	}


	@Then("Product Description is displayed in new tab")
	public void product_description_is_displayed_in_new_tab() {

		//As product description click will open new tab, we need to switch the driver to the new tab
		//If you do not switch, you can not access the new tab html elements
		//This is how you do it
		Set<String> Handles=driver.getWindowHandles();

		scn.log("List of windows found: "+Handles.size());
		logger.info("List of windows found: "+Handles.size());
		
		scn.log("Windows handles: " + Handles.toString());
		logger.info("Windows handles: " + Handles.toString());

		Iterator<String> it=Handles.iterator();// get the iterator to iterate the elements in set

		String original=it.next();//gives the parent window id
		String ProdDecsp=it.next();//gives the child window id

		driver.switchTo().window(ProdDecsp);
		scn.log("Switched to the new window/tab");
		logger.info("Switched to the new window/tab");

		//Now driver can access new driver window, but can not access the orignal tab
		//Check product title is displayed
		WebElement productTitle=driver.findElement(By.xpath("//span[@id='productTitle']"));		
		Assert.assertTrue("Search product Title not Match", productTitle.isDisplayed());
		scn.log("Product Title header is matched and displayed as: " + productTitle.getText() );
		logger.info("Product Title header is matched and displayed as: " + productTitle.getText());

		WebElement AddToCartBtn=driver.findElement(By.xpath("//span[@id='submit.add-to-cart']"));
		Assert.assertEquals("Add To Cart Button not Display", true, AddToCartBtn.isDisplayed());
		scn.log("Add to cart Button is displayed");
		logger.info("Add to cart Button is displayed");

		//Switch back to the Original Window, however no other operation to be done
		driver.switchTo().window(original);
		System.out.println("After Shift original Window Title : "+driver.getTitle());
		scn.log("Switched back to Original tab");
		logger.info("Switched back to Original tab");
	}


	// make sure to use this after import io.cucumber.java.After;
	// Use @After to execute steps to be executed after each scnerio
	// one example can be to close the browser
	@After
	public void cleanUp(){
		
		WebDriverFactory.quitDriver();	
	}








}
