/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package find_products;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Eminda
 */
public class ProductScraper {

    WebDriver driver = FirefoxInitializer.getDriver();

    public static List<String> getAuctionList(String url) {
//        WebDriver driver = ChromeDriverInitialize.getDriver();
//        driver.get(url);
//        Document doc = Jsoup.parse(driver.getPageSource());
//        Element body = doc.body();
//
//        List<WebElement> elems = driver.findElements(By.xpath("/html/body/section/div[4]/div/div[4]/div"));
//
        List<String> urls = new ArrayList<>();
//        urls.add("https://www.amazon.com/dp/B006YSCA8C");
//        urls.add("https://www.amazon.com/dp/B00BGWA8OE");
//        urls.add("https://www.amazon.com/Instant-Pot-Programmable-Pressure-Steamer/dp/B01B1VC13K/ref=sr_1_1?s=kitchen-intl-ship&ie=UTF8&qid=1543227068&sr=1-1");
//        urls.add("https://www.amazon.com/Brewista-Stout-Variable-Temperature-Kettle/dp/B01IOBF3A8/ref=sr_1_3?s=electronics&ie=UTF8&qid=1543244079&sr=1-3&keywords=kettle");
        urls.add("washing machine");
        urls.add("refrigerator");
        urls.add("bed");
        urls.add("cooker");
//
//        for (WebElement elm : elems) {
//            try {
//                urls.add(elm.findElement(By.tagName("a")).getAttribute("href"));
//            } catch (Exception e) {
//            }
//        }

        return urls;
    }

    public static List<ProductContent> doM(String url) throws InterruptedException {
        WebDriver driver = FirefoxInitializer.getDriver();
//        driver.get(url);
//        Document doc = Jsoup.parse(driver.getPageSource());
//        Element body = doc.body();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        List<ProductContent> productContents = new ArrayList<>();
        ProductContent auctionContent = null;

//        String upcCode = driver.findElement(By.cssSelector("td.bucket > div:nth-child(2) > ul:nth-child(1) > li:nth-child(6)")).getAttribute("innerText");
//        String upcCode = "";
//        try {
//            upcCode = driver.findElement(By.id("detail-bullets")).findElement(By.className("content")).findElement(By.xpath("./ul")).findElement(By.xpath("./li[6]")).getAttribute("innerText").replace("Item model number:", "").trim();
//        } catch (Exception e) {
//            upcCode = driver.findElement(By.id("productDetails_detailBullets_sections1")).findElement(By.xpath("./tbody")).findElement(By.xpath("./tr[6]")).findElement(By.xpath("./td")).getAttribute("innerText").replace("Item model number:", "").trim();
//        }
////        String upcCode = details.get(5).getAttribute("innerText");
//        System.out.println("UPC Code : " + upcCode);
//        auctionContent.setUpcCode(upcCode);
//
//        String productTitle = driver.findElement(By.id("productTitle")).getAttribute("innerText");
//        System.out.println("Product Title : " + productTitle);
//        auctionContent.setProductTitle(productTitle);
//        auctionContent.setAmzonLink(url);
        //checkHomeDepot
        for (int i = 0; i < 5; i++) {

            auctionContent = new ProductContent();
            auctionContent.setProductTitle(url);
            System.out.println("Product Title : " + url);

            driver.get("https://www.homedepot.com");
            String hdLink = "";
            String hdPrice = "";

            WebElement homeDepotSearch = driver.findElement(By.id("headerSearch"));
//        try {
            System.out.println("Home Depot Checking from code..");
            homeDepotSearch.sendKeys(url);
            Thread.sleep(1000);
            driver.findElement(By.id("headerSearchButton")).click();
            Thread.sleep(1000);
            try {
                List<WebElement> homedepotItems = driver.findElement(By.className("pod-plp__container")).findElements(By.className("js-pod"));

//            String itemDesc = homedepotItems.get(i).findElement(By.className("pod-plp__description")).findElement(By.tagName("a")).getAttribute("innerText");
//                if (itemDesc.equalsIgnoreCase(productTitle)) {
//            System.out.println("***** " + itemDesc);
                hdLink = homedepotItems.get(i).findElement(By.className("pod-plp__description")).findElement(By.tagName("a")).getAttribute("href");
                hdPrice = homedepotItems.get(i).findElement(By.className("price__numbers")).getAttribute("innerText").replace("$", "");
                System.out.println("HD Link : " + hdLink);
                System.out.println("HD Price : " + hdPrice);
                auctionContent.setHd_productLink(hdLink);
                auctionContent.setHd_price(hdPrice);
            } catch (Exception e) {
                System.out.println("No more Procuts in Homedepots..");
                auctionContent.setHd_productLink(" -- ");
                auctionContent.setHd_price(" -- ");
            }

            driver.get("https://www.overstock.com");
            String osLink = "";
            String osPrice = "";

            WebElement overstockSearch = driver.findElement(By.id("search-input"));
            System.out.println("Over Stock Checking by Code..");
            overstockSearch.sendKeys(url);
            Thread.sleep(1000);
            driver.findElement(By.className("searchButton")).click();
            Thread.sleep(1000);
            try {
                List<WebElement> overstockItems = driver.findElement(By.id("product-container")).findElements(By.className("product-wrapper"));
//            String itemDesc = searchItems.get(i).findElement(By.className("product-info")).findElement(By.className("product-title")).getAttribute("innerText");
//            System.out.println("***** " + itemDesc);
                osLink = overstockItems.get(i).findElement(By.className("product-link")).getAttribute("href");
                osPrice = overstockItems.get(i).findElement(By.className("product-price")).getAttribute("innerText").split(" ")[1];
                System.out.println("Over Stock Link : " + osLink);
                System.out.println("Over Stock Price : " + osPrice);
                auctionContent.setOs_productLink(osLink);
                auctionContent.setOs_price(osPrice);
            } catch (Exception e) {
                System.out.println("No More Products in Overstock..");
                auctionContent.setOs_productLink(" -- ");
                auctionContent.setOs_price(" -- ");
            }

            driver.get("https://www.bedbathandbeyond.com");
//            Thread.sleep(5000);
            String bbLink = "";
            String bbPrice = "";

            WebElement bedBathSearch = driver.findElement(By.id("searchInput"));
            try {
                driver.findElement(By.className("rclCloseBtnWrapper")).click();
            } catch (Exception e) {
                System.out.println("No Popup..");
            }
//        try {
            bedBathSearch.click();
            System.out.println("Bed Bath Checking by Code..");
            bedBathSearch.sendKeys(url);
            Thread.sleep(1000);
            driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/header/div/div[2]/div/div/div/div/div[3]/div/form/div[2]/button[2]")).click();
            System.out.println("Bed Bath Search Clicked..");
            Thread.sleep(1000);
//            List<WebElement> bedBathItems = driver.findElement(By.className("slick-track")).findElements(By.className("slick-slide"));
            try {
                List<WebElement> bedbathItems = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/section/div[4]/div/div")).findElements(By.xpath("./*"));
//                String itemDesc = bedBathItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("innerText");
//                if (itemDesc.equalsIgnoreCase(productTitle)) {
//                System.out.println("***** " + itemDesc);
                if (i == bedbathItems.size()) {
                    js.executeScript("arguments[0].scrollIntoView();", bedbathItems.get(i - 1).findElement(By.className("PrimaryLink_1RLwvm")));
                    Thread.sleep(2000);
                    bedbathItems = driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/main/section/div[4]/div/div")).findElements(By.xpath("./*"));
                }
                bbLink = bedbathItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("href");
                bbPrice = bedbathItems.get(i).findElement(By.className("Price_3HnIBb")).getAttribute("innerText").replace("$", "");
                System.out.println("BB Product Link : " + bbLink);
                System.out.println("BB Price : " + bbPrice);
                auctionContent.setBb_productLink(bbLink);
                auctionContent.setBb_price(bbPrice);
            } catch (Exception e) {
                System.out.println("No more Products in Bedbath..");
                auctionContent.setBb_productLink(" -- ");
                auctionContent.setBb_price(" -- ");
            }

            driver.get("https://www.walmart.com");
//            Thread.sleep(5000);
            String walLink = "";
            String walPrice = "";

            WebElement walmartSearch = driver.findElement(By.id("global-search-input"));

            System.out.println("Walmart Checking by Code..");
            walmartSearch.sendKeys(url);
            Thread.sleep(1000);
            driver.findElement(By.className("GlobalHeaderSearchbar-submit")).findElement(By.tagName("button")).click();
            System.out.println("Walmart Search Clicked..");
            Thread.sleep(1000);
            try {
                List<WebElement> walmartItems = driver.findElement(By.id("searchProductResult")).findElement(By.tagName("ul")).findElements(By.xpath("./*"));
//            String itemDesc = searchItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("innerText");
//            System.out.println("***** " + itemDesc);
                walLink = walmartItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("href");
                walPrice = walmartItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + walmartItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
                System.out.println("Walmart Link : " + walLink);
                System.out.println("Walmart Price : " + walPrice);
                auctionContent.setWalmart_productLink(walLink);
                auctionContent.setWalmart_price(walPrice);
            } catch (Exception e) {
                System.out.println("No more Products in Walmart..");
                auctionContent.setWalmart_productLink(" -- ");
                auctionContent.setWalmart_price(" -- ");
            }

            driver.get("https://www.costco.com");
//            Thread.sleep(5000);
            String costLink = "";
            String costPrice = "";

            WebElement costcoSearch = driver.findElement(By.id("search-field"));

            System.out.println("Costco Checking by Code..");
            costcoSearch.sendKeys(url);
            Thread.sleep(1000);
            driver.findElement(By.className("co-search-thin")).click();
            System.out.println("Costco Search Clicked..");
            Thread.sleep(1000);
            try{
            List<WebElement> costcoItems = driver.findElement(By.className("product-list")).findElements(By.className("product-tile-set"));
//                String itemDesc = searchItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("innerText");
//                    System.out.println("***** " + itemDesc);
            costLink = costcoItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("href");
//            costcoItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).click();
//            Thread.sleep(2000);
            // // !!! Asks to SIgnin for Price !!! // //
//                    costPrice = searchItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + searchItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
//                }
auctionContent.setCostco_price(" -- ");
            System.out.println("Costco Link : " + costLink);
            auctionContent.setCostco_productLink(costLink);

//                } else {
//                    throw new Exception();
//                }
            productContents.add(auctionContent);
            }catch(Exception e){
                System.out.println("No more Products in Costco..");
                auctionContent.setCostco_productLink(" -- ");
                auctionContent.setCostco_price(" -- ");
            }
        }

//        } catch (Exception e) {
//            System.out.println("Home Depot Not found from Code..");
//            e.printStackTrace();
//            try {
//                homeDepotSearch = driver.findElement(By.id("headerSearch"));
//                System.out.println("Home Depot Checking by Title..");
//                homeDepotSearch.sendKeys(productTitle);
//                Thread.sleep(1000);
//                driver.findElement(By.id("headerSearchButton")).click();
//                Thread.sleep(1000);
//                List<WebElement> searchItems = driver.findElement(By.className("pod-plp__container")).findElements(By.className("js-pod"));
//                for (int i = 0; i < 5; i++) {
//                    String itemDesc = searchItems.get(i).findElement(By.className("pod-plp__description")).findElement(By.tagName("a")).getAttribute("innerText");
//                    if (itemDesc.equalsIgnoreCase(productTitle)) {
//                        System.out.println("***** " + itemDesc);
//                        hdLink = searchItems.get(i).findElement(By.className("pod-plp__description")).findElement(By.tagName("a")).getAttribute("href");
//                        hdPrice = searchItems.get(i).findElement(By.className("price__numbers")).getAttribute("innerText");
//                    }
//                }
//            } catch (Exception f) {
//                System.out.println("No Item Found");
//                f.printStackTrace();
//                hdLink = " - ";
//                hdPrice = " - ";
//            }
//        }
//        System.out.println("HD Link : " + hdLink);
//        System.out.println("HD Price : " + hdPrice);
//        auctionContent.setHd_productLink(hdLink);
//        auctionContent.setHd_price(hdPrice);
        //check OverStock
//        driver.get("https://www.overstock.com");
//        String osLink = "";
//        String osPrice = "";
//
//        WebElement overstockSearch = driver.findElement(By.id("search-input"));
//        try {
//            System.out.println("Over Stock Checking by Code..");
//            overstockSearch.sendKeys(upcCode);
//            Thread.sleep(1000);
//            driver.findElement(By.className("searchButton")).click();
//            Thread.sleep(1000);
//            List<WebElement> searchItems = driver.findElement(By.className("product-container")).findElements(By.className("product-wrapper"));
//            for (int i = 0; i < 5; i++) {
//                String itemDesc = searchItems.get(i).findElement(By.className("product-info")).findElement(By.className("product-title")).getAttribute("innerText");
//                if (itemDesc.equalsIgnoreCase(productTitle)) {
//                    System.out.println("***** " + itemDesc);
//                    osLink = searchItems.get(i).findElement(By.className("product-link")).getAttribute("href");
//                    osPrice = searchItems.get(i).findElement(By.className("product-price")).getAttribute("innerText").split(" ")[1];
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("Over Stock Not Found from Code..");
//            e.printStackTrace();
//            try {
//                overstockSearch = driver.findElement(By.id("search-input"));
//                System.out.println("Over Stock Checking by Title..");
//                overstockSearch.sendKeys(productTitle);
//                Thread.sleep(1000);
//                driver.findElement(By.className("searchButton")).click();
//                Thread.sleep(1000);
//                List<WebElement> searchItems = driver.findElement(By.className("product-container")).findElements(By.className("product-wrapper"));
//                for (int i = 0; i < 5; i++) {
//                    String itemDesc = searchItems.get(i).findElement(By.className("product-info")).findElement(By.className("product-title")).getAttribute("innerText");
//                    if (itemDesc.equalsIgnoreCase(productTitle)) {
//                        System.out.println("***** " + itemDesc);
//                        osLink = searchItems.get(i).findElement(By.className("product-link")).getAttribute("href");
//                        osPrice = searchItems.get(i).findElement(By.className("product-price")).getAttribute("innerText").split(" ")[1];
//                    }
//                }
//            } catch (Exception f) {
//                System.out.println("No Item Found");
//                f.printStackTrace();
//                osLink = " - ";
//                osPrice = " - ";
//            }
//        }
//        auctionContent.setOs_productLink(osLink);
//        auctionContent.setOs_price(osPrice);
        //check BedBath & Beyond
//        driver.get("https://www.bedbathandbeyond.com");
//        Thread.sleep(5000);
//        String bbLink = "";
//        String bbPrice = "";
//
//        WebElement bedBathSearch = driver.findElement(By.id("searchInput"));
//        try {
//            driver.findElement(By.className("rclCloseBtnWrapper")).click();
//        } catch (Exception e) {
//            System.out.println("No Popup..");
//        }
////        try {
//        System.out.println("Bed Bath Checking by Code..");
//        bedBathSearch.sendKeys(url);
//        Thread.sleep(1000);
//        driver.findElement(By.className("SearchInput-inline_2RJpvS")).click();
//        System.out.println("Bed Bath Search Clicked..");
//        Thread.sleep(1000);
//        List<WebElement> bedBathItems = driver.findElement(By.className("slick-track")).findElements(By.className("slick-slide"));
//        for (int i = 0; i < 5; i++) {
//            String itemDesc = bedBathItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("innerText");
////                if (itemDesc.equalsIgnoreCase(productTitle)) {
//            System.out.println("***** " + itemDesc);
//            bbLink = bedBathItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("href");
//            bbPrice = bedBathItems.get(i).findElement(By.className("mb2")).findElement(By.xpath("./div/div/div[1]/span[1]")).getAttribute("innerText").replace("$", "");
////                }
//        }
//        } catch (Exception e) {
//            System.out.println("Bed Bath Not Found from Code..");
//            e.printStackTrace();
//            try {
//                bedBathSearch = driver.findElement(By.id("searchInput"));
//                System.out.println("Bed Bath Checking by Title..");
//                bedBathSearch.sendKeys(productTitle);
//                Thread.sleep(1000);
//                driver.findElement(By.className("SearchInput-inline_2RJpvS")).click();
//                System.out.println("Bed Bath Search Clicked..");
//                Thread.sleep(1000);
//                List<WebElement> searchItems = driver.findElement(By.className("slick-track")).findElements(By.className("slick-slide"));
//                for (int i = 0; i < 5; i++) {
//                    String itemDesc = searchItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("innerText");
//                    if (itemDesc.equalsIgnoreCase(productTitle)) {
//                        System.out.println("***** " + itemDesc);
//                        bbLink = searchItems.get(i).findElement(By.className("PrimaryLink_1RLwvm")).getAttribute("href");
//                        bbPrice = searchItems.get(i).findElement(By.className("mb2")).findElement(By.xpath("./div/div/div[1]/span[1]")).getAttribute("innerText").replace("$", "");
//                    }
//                }
//            } catch (Exception f) {
//                System.out.println("No Item Found");
//                f.printStackTrace();
//                bbLink = " - ";
//                bbPrice = " - ";
//            }
//        }
//        System.out.println("BB Product Link : " + bbLink);
//        System.out.println("BB Price : ");
//        auctionContent.setBb_productLink(bbLink);
//        auctionContent.setBb_price(bbPrice);
        //check Walmart
//        driver.get("https://www.walmart.com");
//        Thread.sleep(5000);
//        String walLink = "";
//        String walPrice = "";
//
//        WebElement walmartSearch = driver.findElement(By.id("global-search-input"));
//
//        try {
//            System.out.println("Walmart Checking by Code..");
//            walmartSearch.sendKeys(upcCode);
//            Thread.sleep(1000);
//            driver.findElement(By.className("GlobalHeaderSearchbar-submit")).findElement(By.tagName("button")).click();
//            System.out.println("Walmart Search Clicked..");
//            Thread.sleep(1000);
//            List<WebElement> searchItems = driver.findElement(By.className("search-result-listview-items")).findElements(By.xpath("./*"));
//            for (int i = 0; i < 5; i++) {
//                String itemDesc = searchItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("innerText");
//                if (itemDesc.equalsIgnoreCase(productTitle)) {
//                    System.out.println("***** " + itemDesc);
//                    walLink = searchItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("href");
//                    walPrice = searchItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + searchItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("Bed Bath Not Found from Code..");
//            e.printStackTrace();
//            try {
//                walmartSearch = driver.findElement(By.id("global-search-input"));
//                System.out.println("Walmart Checking by Title..");
//                walmartSearch.sendKeys(productTitle);
//                Thread.sleep(1000);
//                driver.findElement(By.className("GlobalHeaderSearchbar-submit")).findElement(By.tagName("button")).click();
//                System.out.println("Walmart Search Clicked..");
//                Thread.sleep(1000);
//                List<WebElement> searchItems = driver.findElement(By.className("search-result-listview-items")).findElements(By.xpath("./*"));
//                for (int i = 0; i < 5; i++) {
//                    String itemDesc = searchItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("innerText");
//                    if (itemDesc.equalsIgnoreCase(productTitle)) {
//                        System.out.println("***** " + itemDesc);
//                        walLink = searchItems.get(i).findElement(By.className("search-result-product-title")).findElement(By.className("product-title-link")).getAttribute("href");
//                        walPrice = searchItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + searchItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
//                    }
//                }
//            } catch (Exception f) {
//                System.out.println("No Item Found");
//                f.printStackTrace();
//                walLink = " - ";
//                walPrice = " - ";
//            }
//        }
//        auctionContent.setWalmart_productLink(walLink);
//        auctionContent.setWalmart_price(walPrice);
        //check Walmart
//        driver.get("https://www.costco.com");
//        Thread.sleep(5000);
//        String costLink = "";
//        String costPrice = "";
//
//        WebElement costcoSearch = driver.findElement(By.id("search-field"));
//
//        try {
//            System.out.println("Costco Checking by Code..");
//            costcoSearch.sendKeys(upcCode);
//            Thread.sleep(1000);
//            driver.findElement(By.className("co-search-thin")).click();
//            System.out.println("Costco Search Clicked..");
//            Thread.sleep(1000);
//            List<WebElement> searchItems = driver.findElement(By.className("product-list")).findElements(By.className("product-tile-set"));
//            for (int i = 0; i < 5; i++) {
//                String itemDesc = searchItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("innerText");
//                if (itemDesc.equalsIgnoreCase(productTitle)) {
//                    System.out.println("***** " + itemDesc);
//                    costLink = searchItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("href");
////                    costPrice = searchItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + searchItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
//                }
//            }
//
//        } catch (Exception e) {
//            System.out.println("Costc Not Found from Code..");
//            e.printStackTrace();
//            try {
//                costcoSearch = driver.findElement(By.id("search-field"));
//                System.out.println("Costco Checking by Title..");
//                costcoSearch.sendKeys(productTitle);
//                Thread.sleep(1000);
//                driver.findElement(By.className("co-search-thin")).click();
//                System.out.println("Costco Search Clicked..");
//                Thread.sleep(1000);
//                List<WebElement> searchItems = driver.findElement(By.className("product-list")).findElements(By.className("product-tile-set"));
//                for (int i = 0; i < 5; i++) {
//                    String itemDesc = searchItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("innerText");
//                    if (itemDesc.equalsIgnoreCase(productTitle)) {
//                        System.out.println("***** " + itemDesc);
//                        costLink = searchItems.get(i).findElement(By.className("description")).findElement(By.tagName("a")).getAttribute("href");
////                    costPrice = searchItems.get(i).findElement(By.className("price-characteristic")).getAttribute("innerText") + "." + searchItems.get(i).findElement(By.className("price-mantissa")).getAttribute("innerText");
//                    }
//                }
//            } catch (Exception f) {
//                System.out.println("No Item Found");
//                f.printStackTrace();
//                costLink = " - ";
//                costPrice = " - ";
//            }
//        }
//        auctionContent.setCostco_productLink(costLink);
//        auctionContent.setCostco_price(costPrice);
        return productContents;
    }

}
