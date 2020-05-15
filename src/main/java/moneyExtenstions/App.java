/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package moneyExtenstions;

import java.math.BigDecimal;
import java.util.function.Function;
public class App {

    public static void main(String[] args) {
        BigDecimal listingPrice = new BigDecimal(100);
        FpConfiguration config = new FpConfiguration();
        String discountRateString = "0.2";
        String taxRateString = "0.1";
        Function<BigDecimal, Boolean> applyDiscountRuleFunc = price -> price.compareTo(new BigDecimal(100)) >= 0;
        ApplyDiscountFunction applyDiscountFunc = new ApplyDiscountFunction();
        ApplyTaxFunction applyTaxFunction = new ApplyTaxFunction();
        CalculateFinalPriceFunctionImpl calculateFinalPriceFunc = new CalculateFinalPriceFunctionImpl();

        Function calculateFinalPriceForListingPriceFunc = config.calculateFinalPriceForListingPrice(discountRateString, taxRateString, applyDiscountRuleFunc, applyDiscountFunc, applyTaxFunction, calculateFinalPriceFunc);
        System.out.println(new Application(calculateFinalPriceForListingPriceFunc).run(listingPrice));
    }
}