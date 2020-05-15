package moneyExtenstions;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public class FpConfiguration {

    public Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice(
            String discountRateString,
            String taxRateString,
            Function<BigDecimal, Boolean> applyDiscountRule,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscountFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTaxFunction,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        BigDecimal discountRate = new BigDecimal(discountRateString);
        BigDecimal taxRate = new BigDecimal(taxRateString);
        return generateCurriedCalculateFinalPrice(discountRate, taxRate, applyDiscountRule, applyDiscountFunction, applyTaxFunction, calculateFinalPriceFunction);
    }

    private Function<BigDecimal, BigDecimal> generateCurriedCalculateFinalPrice(
            BigDecimal discountRate,
            BigDecimal taxRate,
            Function<BigDecimal, Boolean> applyDiscountRule,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscount,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTax,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        Function applyDiscountForAmount = curry(applyDiscount).apply(discountRate);
        Function applyTaxForAmount = curry(applyTax).apply(taxRate);
        Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice = (Function<BigDecimal, BigDecimal>) curry(calculateFinalPriceFunction)
                .apply(applyDiscountRule)
                .apply(applyDiscountForAmount)
                .apply(applyTaxForAmount);
        return calculateFinalPriceForListingPrice;
    }

    private Function<BigDecimal, Function<BigDecimal, BigDecimal>>
    curry(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<Function<BigDecimal, Boolean>, Function<Function<BigDecimal, BigDecimal>, Function<Function<BigDecimal, BigDecimal>, Function<BigDecimal, BigDecimal>>>>
    curry(CalculateFinalPriceFunction function) {
        return applyDiscountRule -> applyDiscountForAmount -> applyTaxForAmount -> listingPrice ->
                function.apply(applyDiscountRule, applyDiscountForAmount, applyTaxForAmount, listingPrice);
    }
}
