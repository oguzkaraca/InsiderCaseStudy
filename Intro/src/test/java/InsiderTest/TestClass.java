package InsiderTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import pages.CareerPage;
import pages.HomePage;
import pages.QAJobsPage;

public class TestClass {
    private WebDriver driver;
    private static final String CHROME_DRIVER_PATH = "C:\\Users\\oguzk\\Desktop\\chromedriver-win64\\chromedriver.exe";

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    public void testInsiderCareers() throws InterruptedException {
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHomePage();

        // Step 1: Open Insider and accept cookies
        System.out.println("Step 1: Opened Insider homepage");
        homePage.acceptCookies(); // Accept cookies
        System.out.println("Step 1.1: Accepted cookies");

        // Step 2: Assert that Insider is open
        Assert.assertTrue(homePage.getPageTitle().contains("Insider"), "Home page title is incorrect");
        System.out.println("Step 2: Verified Insider homepage title");

        // Step 3: Hover over Company in the top menu
        homePage.hoverOverCompanyMenu();
        System.out.println("Step 3: Hovered over 'Company' menu");
        Thread.sleep(1000); // Reduced delay to 1 second

        // Step 4: Hover over Company > Careers in the top menu
        homePage.hoverOverCareersLink();
        System.out.println("Step 4: Hovered over 'Careers' link");
        Thread.sleep(1000); // Reduced delay to 1 second

        // Step 5: Click Company > Careers
        homePage.clickCareersLink();
        System.out.println("Step 5: Clicked 'Careers' link");

        // Step 6: Perform actions on the Careers page
        CareerPage careerPage = new CareerPage(driver);
        careerPage.performCareerPageActions();
        System.out.println("Step 6: Performed actions on the Careers page");

        // Step 7: Navigate to QA Jobs page, filter jobs, and verify job listings
        QAJobsPage qaJobsPage = new QAJobsPage(driver);
        qaJobsPage.navigateToQAJobsPage();
        qaJobsPage.clickSeeAllQAJobs();
        qaJobsPage.filterJobsByLocationAndDepartment("Istanbul, Turkey", "Quality Assurance");
        qaJobsPage.verifyJobDetails(); // Verify job details
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}