package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;

    // Company button (top bar)
    @FindBy(linkText = "Company")
    private WebElement companyMenu;

    // Company > Careers button (top bar)
    @FindBy(linkText = "Careers")
    private WebElement careersLink;

    // Cookie consent button
    @FindBy(css = "#wt-cli-accept-all-btn")
    private WebElement acceptCookiesButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void navigateToHomePage() {
        driver.get("https://useinsider.com/");
    }

    // Method to accept all cookies
    public void acceptCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton)).click();
    }

    // Hover over Company (top bar)
    public void hoverOverCompanyMenu() {
        Actions actions = new Actions(driver);
        actions.moveToElement(companyMenu).perform();
    }

    // Hover over Company > Careers (top bar)
    public void hoverOverCareersLink() {
        Actions actions = new Actions(driver);
        actions.moveToElement(careersLink).perform();
    }

    // Click Company > Careers
    public void clickCareersLink() {
        careersLink.click();
    }

    // Get page title
    public String getPageTitle() {
        return driver.getTitle();
    }
}