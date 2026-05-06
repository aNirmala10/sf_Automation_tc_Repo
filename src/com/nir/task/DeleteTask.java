package com.nir.task;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DeleteTask {

	public static void main(String[] args) throws InterruptedException {
System.out.println("testing branch");
		ChromeOptions options = new ChromeOptions();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String sessionId = "00DgL00000OIdzb!AQEAQMESc8lGDAr91q_kPUsjQsJ9Iq5l.EaMCOKvQFCvrKKwX2Flx8d4C43WYEh_r6zNMfhdw2uNmkH8djp80N9t_Y9kUODN";

		// Frontdoor.jsp - bypasses ALL login flows
		String frontdoorUrl = String.format(
				"https://orgfarm-372db3eb5e-dev-ed.develop.my.salesforce.com/secur/frontdoor.jsp?sid=00DgL00000OIdzb!AQEAQMESc8lGDAr91q_kPUsjQsJ9Iq5l.EaMCOKvQFCvrKKwX2Flx8d4C43WYEh_r6zNMfhdw2uNmkH8djp80N9t_Y9kUODN",  
				sessionId
				);

		driver.get(frontdoorUrl);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.urlContains("/lightning"));
		System.out.println("LOGGED IN via frontdoor.jsp!");

		//1. Navigate to Task tab
		WebElement taskTab = driver.findElement(By.xpath("//a[@title = 'Tasks']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", taskTab);	
		
		System.out.println("Current URL: " + driver.getCurrentUrl());
		System.out.println("Page title: " + driver.getTitle());

		Thread.sleep(3000);

		//2. Click on a created task
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement dropdown= wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='Recently Viewed']/following::a[1]")));
		dropdown.click();
		System.out.println("clicked on a created task");


		//3. Click on actions drop down and select delete action		
		WebElement click = driver.findElement(By.xpath("//a[@title='Show 6 more actions']"));
		click.click();

		WebElement dropdownValue = driver.findElement(By.xpath("//a[@title='Delete']"));
		new Actions(driver) .click(dropdownValue) .perform();
		System.out.println("Delete value selected from the dropdown");


		//4.click Delete button in the popup		
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement element = wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//h1[text()='Delete Task']/following::button/span[text()='Delete']")));
		element.click();
		System.out.println("Delete button is clicked");


		//5.Verification
		/*WebElement validation = driver.findElement(By.xpath("//div[@class='slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage']"));
		String deleteMsg = validation.getText();

		if(deleteMsg.contains("deleted")) {
			System.out.println("VERIFIED: Successfully deleted the task. The delete message is: "+deleteMsg);
		}
		else
			System.out.println("TC failed");*/
		
		WebElement validation = driver.findElement(By.xpath("//div[@class='slds-theme--success slds-notify--toast slds-notify slds-notify--toast forceToastMessage']"));
		String deleteMsg = validation.getText();
		String trimMsg = deleteMsg.substring(0, 48);

		if(trimMsg.contains("deleted")) {
			System.out.println("VERIFIED: Successfully deleted the task. The delete message is: "+trimMsg);
		}
		else
			System.out.println("TC failed");
		
		






	}

}
