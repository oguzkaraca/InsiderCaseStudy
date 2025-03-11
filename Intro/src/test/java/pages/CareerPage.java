package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CareerPage {
    private WebDriver driver;

    // Teams Section
    @FindBy(css = "div[class='col-12 mb-xl-5 py-xl-4 py-2 text-center'] h3[class='category-title-media']")
    private WebElement findYourCallingText;

    @FindBy(css = "img[alt='Customer Success']")
    private WebElement customerSuccessText;

    @FindBy(css = "img[alt='Sales']")
    private WebElement salesText;

    @FindBy(css = "img[alt='Product & Engineering']")
    private WebElement productEngineeringText;

    @FindBy(css = ".btn.btn-outline-secondary.rounded.text-medium.mt-5.mx-auto.py-3.loadmore")
    private WebElement seeAllTeamsButton;

    // Locations Section
    @FindBy(css = ".category-title-media.ml-0")
    private WebElement ourLocationsText;

    @FindBy(css = "img[alt='New York']")
    private WebElement newYorkText;

    @FindBy(css = "img[alt='Sao Paulo']")
    private WebElement saoPauloText;

    public CareerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Method to scroll to an element
    private void scrollToElement(WebElement element, boolean centerScroll) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(element)); // Wait for the element to become visible

        if (centerScroll) {
            // Scroll the element into view with some padding to avoid overlapping with the navigation bar
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", element
            );
        } else {
            // Default scroll behavior
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView();", element
            );
        }
        Thread.sleep(1000); // Add a 1-second delay after scrolling
    }

    // Method to hover over an element with a delay
    private void hoverOverElement(WebElement element) throws InterruptedException {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).pause(1000).perform(); // Hover and pause for 1 second
        Thread.sleep(1000); // Add a 1-second delay after hovering
    }

    // Method to move the mouse cursor to a safe location
    private void moveMouseToSafeLocation() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(0, 0).perform(); // Move the cursor to the top-left corner of the viewport
    }

    // Method to perform the actions in the specified order
    public void performCareerPageActions() throws InterruptedException {
        // Step 1: Scroll to the "Find your calling" text
        scrollToElement(findYourCallingText, false); // Use default scroll behavior
        System.out.println("Step 1: Scrolled to 'Find your calling' text");

        // Step 2: Hover over the "Customer Success" text
        hoverOverElement(customerSuccessText);
        System.out.println("Step 2: Hovered over 'Customer Success' text");

        // Step 3: Hover over the "Sales" text
        hoverOverElement(salesText);
        System.out.println("Step 3: Hovered over 'Sales' text");

        // Step 4: Hover over the "Product & Engineering" text
        hoverOverElement(productEngineeringText);
        System.out.println("Step 4: Hovered over 'Product & Engineering' text");

        // Step 5: Scroll to the "See all teams" button
        scrollToElement(seeAllTeamsButton, true); // Use center scroll behavior
        System.out.println("Step 5: Scrolled to 'See all teams' button");

        // Step 6: Click the "See all teams" button and add a 2-second delay
        seeAllTeamsButton.click();
        moveMouseToSafeLocation(); // Move the cursor to a safe location
        Thread.sleep(2000); // Add a 2-second delay
        System.out.println("Step 6: Clicked 'See all teams' button");

        // Step 7: Scroll to the "Our Locations" text
        scrollToElement(ourLocationsText, true); // Use center scroll behavior
        System.out.println("Step 7: Scrolled to 'Our Locations' text");

        // Step 8: Move the mouse to a safe location before hovering over "New York"
        moveMouseToSafeLocation();

        // Step 9: Hover over the "New York" text
        hoverOverElement(newYorkText);
        System.out.println("Step 8: Hovered over 'New York' text");

        // Step 10: Hover over the "Sao Paulo" text
        hoverOverElement(saoPauloText);
        System.out.println("Step 9: Hovered over 'Sao Paulo' text");
    }
}