import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Testing {
    public static void main(String[] args) {

        //1. Launch Chrome browser.
        WebDriver driver = new ChromeDriver();

        //2.Navigate to
        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");

        //3. Login using username Tester and password test
        driver.findElement(By.id("ctl00_MainContent_username")).click();
        driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester", Keys.ENTER);
        driver.findElement(By.id("ctl00_MainContent_password")).click();
        driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test", Keys.ENTER);

        //Click on Order link
        driver.findElement(By.xpath("//a[@href='Process.aspx']")).click();

        String productOrdered = driver.findElement(By.id("ctl00_MainContent_fmwOrder_ddlProduct")).getAttribute("value");

        //Enter a random product quantity between 1 and 100
        Random rand = new Random();
        int quantity = 1 + rand.nextInt(100);
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity")).sendKeys(String.valueOf(quantity), Keys.ENTER);


        //Click on Calculate and verify that the Total value is as expected.
        driver.findElement(By.xpath("//*[@type='submit']")).click();
        String price = driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtUnitPrice")).getAttribute("value");
        int expectedTotal = 0;
        if (quantity<10){
            expectedTotal = quantity * Integer.parseInt(price);
        }else{
            expectedTotal = 0.92 * quantity * Integer.parseInt(price);
        }
        int actualTotal = Integer.parseInt(driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtTotal")).getAttribute("value"));
        Assert.assertEquals(actualTotal, expectedTotal);

        //Generate and enter random first name and last name.
        String firstName = generateFirstName();

        String lastName = generateLastName();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName")).sendKeys((firstName+" "+lastName), Keys.ENTER);
        //Generate and Enter random street address
        String streetAddress = generateStreetAdress();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2")).sendKeys(streetAddress, Keys.ENTER);
        //Generate and Enter random city
        String city = generateCity();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3")).sendKeys(city, Keys.ENTER);
        //Generate and Enter random state
        String state = generateState();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4")).sendKeys(state, Keys.ENTER);
        //Generate and Enter a random 5 digit zip code
        String zip = denerateZip();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).click();
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5")).sendKeys(zip, Keys.ENTER);
        //Select the card type randomly. On each run your script should select a random type.
        Random rand = new Random();
        int card = 1 + rand.nextInt(3);

        if(card == 1){
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_0")).click();
            String visa = "4";
            for(int i=0; i<15; i++){
            visa += (int)(Math.random()*10);
            }
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(visa, Keys.ENTER);
        }else if(card ==2) {
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_1")).click();
            String masterCard = "5";
            for(int i=0; i<15; i++){
                masterCard += (int)(Math.random()*10);
            }
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(masterCard, Keys.ENTER);
        }else {
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_cardList_2")).click();
            String amEx = "3";
            for(int i=0; i<14; i++){
                amEx += (int)(Math.random()*10);
            }
            driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6")).sendKeys(amEx, Keys.ENTER);
        }

        //Enter a valid expiration date (newer than the current date)
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1")).click();


        //Click on Process
        driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();
        //Verify that “New order has been successfully added” message appeared on the page.

        String expected = "New order has been successfully added.";
        String actual = driver.findElement(By.xpath("//strong")).getText();
        Assert.assertEquals(actual,expected);

        //Click on View All Orders link.
        driver.findElement(By.xpath("//a[@href='Default.aspx']")).click();
        //Verify that the entire information contained on the row (Name, Product, Quantity, etc) matches the previously entered information in previous steps.
        String actualName = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[2]")).getAttribute("value");
        String expectedName = firstName+" "+lastName;
        Assert.assertEquals(actualName, expectedName);

        String actualProduct = driver.findElement(By.xpath("//*[@id="ctl00_MainContent_orderGrid"]/tbody/tr[2]/td[3]")).getAttribute("value");
        Assert.assertEquals(actualProduct, productOrdered);

        String actualQuantity = driver.findElement(By.xpath("//*[@id=\"ctl00_MainContent_orderGrid\"]/tbody/tr[2]/td[4]")).getAttribute("value");
        Assert.assertEquals(actualQuantity, quantity);

        //Log out of the application.
        driver.findElement(By.id("ctl00_logout")).click();



        driver.quit();




        public static String generateFirstName() {
            String first= null;
            for (int i = 0; i < 6; i++) {
                first += (char) (97 + (int) (Math.random() * 26));
            }
            return first;
        }

        public static String generateLastName() {
            String last= null;
            for (int i = 0; i < 7; i++) {
                last += (char) (97 + (int) (Math.random() * 26));
            }
            return last;
        }

        public static String generateStreetAdress(){
            Random rand = new Random();
            int buildingNumber = 1 + rand.nextInt(10000);
            List<String> streets = new ArrayList<>();
            streets.add("Main St");
            streets.add("Fifth St");
            streets.add("Riverside Rd");
            streets.add("Maryland ave");
            streets.add("New Rd");
            streets.add("Line Dr");
            streets.add("Up St");
            streets.add("Lee Hwy");
            streets.add("Arlington Blvd");
            streets.add("Old Rd");
            return (buildingNumber + " " + streets.get((int)(Math.random()*10)));

        }
        public static String generateCity(){
            List<String> cities = new ArrayList<>();
            cities.add("Washington DC");
            cities.add("Philadelphia");
            cities.add("Arlington");
            cities.add("Fairfax");
            cities.add("Dulles");
            cities.add("Dallas");
            cities.add("Houston");
            cities.add("Paris");
            cities.add("Austin");
            cities.add("Seattle");
            return cities.get((int)(Math.random()*10));

        }


        public static String generateState(){
            List<String> states = new ArrayList<>();
            states.add("Oregon");
            states.add("Maryland");
            states.add("Alabama");
            states.add("Virginia");
            states.add("New York");
            states.add("North Dakota");
            states.add("North Carolina");
            states.add("Florida");
            states.add("Massachusetts");
            return states.get((int)(Math.random()*10));
        }

        public static String generateZip(){
            String zip = "";
            for (int i = 0; i < 5; i++) {
                zip += (int) (Math.random() * 10);
            }
            return zip;
        }

    }
}
