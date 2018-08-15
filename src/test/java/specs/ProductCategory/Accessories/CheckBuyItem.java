package specs.ProductCategory.Accessories;

import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import pageobjects.ProductCategory.Accessories.BuyItem;
import specs.AbstractSpec;

public class CheckBuyItem extends AbstractSpec {
    private static BuyItem buyItem;

    @BeforeTest
    public void setUp() {
        buyItem = new BuyItem(driver);
    }

}
