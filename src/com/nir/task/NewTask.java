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

public class NewTask {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("imported from local folder");

		ChromeOptions options = new ChromeOptions();
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		String sessionId = "00DgL00000OIdzb!AQEAQClr8011iETDOCmxUlq4mM5xzmz5tFcPDEpz.1uAMZaSczmYLOvShep5kgES_cNhJ0vG0dcbzbZmVwSPp0QnBEtJgiLl";

		// Frontdoor.jsp - bypasses ALL login flows
		String frontdoorUrl = String.format(
				"https://orgfarm-372db3eb5e-dev-ed.develop.my.salesforce.com/secur/frontdoor.jsp?sid=00DgL00000OIdzb!AQEAQClr8011iETDOCmxUlq4mM5xzmz5tFcPDEpz.1uAMZaSczmYLOvShep5kgES_cNhJ0vG0dcbzbZmVwSPp0QnBEtJgiLl",  
				sessionId
				);

		driver.get(frontdoorUrl);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.urlContains("/lightning"));
		System.out.println("LOGGED IN via frontdoor.jsp!");

		//1. Navigate to Task tab
		driver.get("https://orgfarm-372db3eb5e-dev-ed.develop.lightning.force.com/lightning/o/Task/home");

		System.out.println("Current URL: " + driver.getCurrentUrl());
		System.out.println("Page title: " + driver.getTitle());

		Thread.sleep(3000);

		//2. Click on New button
		// WebElement newButton =driver.findElement(By.xpath("//div[@title='New']"));
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement dropdown= wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Tasks']/following-sibling::one-app-nav-bar-item-dropdown//a[@role='button']")));
		dropdown.click();

		WebElement newTask = driver.findElement(By.xpath("//a[@role='menuitem']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", newTask);		       
		//span[@class='slds-truncate']//span[text()='New Task']

		Thread.sleep(3000);
		// 3. Create a task
		//Assigned to field
		driver.findElement(By.xpath("//input[@title='Search People']")).click();
		WebElement assignedToDropdown = driver.findElement(By.xpath("//div[@class='primaryLabel slds-truncate slds-lookup__result-text']"));
		JavascriptExecutor executor1 = (JavascriptExecutor)driver;
		executor1.executeScript("arguments[0].click();", assignedToDropdown);
		System.out.println("selected people");
		//(//*[name()='svg'][contains(@class,'slds-icon slds-icon-text-default slds-icon_xx-small')])[17]

		Thread.sleep(3000);
		//Subject field
		WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement subjectSearchButton= wait2.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Subject']/following::input[1]")));
		subjectSearchButton.click();

		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement subjectDropdown= wait3.until(ExpectedConditions.elementToBeClickable(By.xpath("//label[text()='Subject']/following::lightning-base-combobox-item//span[text()='Call']")));
		subjectDropdown.click();	
		System.out.println("Subject value selected from the dropdown");

		//Priority field

		WebElement priorityDropdown =driver.findElement(By.xpath("//a[@role='button'][normalize-space()='Normal']"));
		priorityDropdown.click();	  
		System.out.println("Priority Dropdown visible");

		WebDriverWait wait5 = new WebDriverWait(driver, Duration.ofSeconds(15));
		WebElement priorityValue= wait5.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title = 'Normal']")));
		priorityValue.click();	
		System.out.println("Priority value selected from the dropdown");



		//Status field	
		Thread.sleep(3000);
		WebElement statusDropdown = driver.findElement(By.xpath("//a[normalize-space()='Not Started']"));
		statusDropdown.click();

		/*
		 * WebElement statusDropdown =
		 * driver.findElement(By.xpath("//span[text()= 'Status']/following::a[1]")); new
		 * Actions(driver) .click(statusDropdown) .perform();
		 * System.out.println("Status dropdown clicked");
		 */

		WebElement dropdownValue = driver.findElement(By.xpath("//a[@title= 'In Progress']/following::a[1]"));
		new Actions(driver) .click(dropdownValue) .perform();
		System.out.println("Status value selected");


		//save button
		driver.findElement(By.xpath("//span[text()= 'Save']")).click();
		System.out.println("Task saved");

		//validation		
		WebElement validation = driver.findElement(By.xpath("//h1//div[ text()='Task']/following::div[@title='Call']"));
		String taskName = validation.getText();

		if(taskName.contains("Call")) {
			System.out.println("VERIFIED: Successfully created the task. The task contains the word 'Call' and the actual task is "
					+taskName);
		}
		else
			System.out.println("TC failed");

		

	}

}
