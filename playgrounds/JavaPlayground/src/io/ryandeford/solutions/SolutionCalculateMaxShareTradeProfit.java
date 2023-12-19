package io.ryandeford.solutions;

public class SolutionCalculateMaxShareTradeProfit {
    // Note: This solution is only valid if stock positions are assumed to be long equity
    // Note: Negative share prices are accepted, even though they're not valid for long equity
    // TODO: Discuss long vs. short equity positions + negative pricing representations with team
    // TODO: Discuss how to handle zero-priced equity positions (e.g. positions with zero cost basis)
    public static long solution(long[] prices) {
        // If the prices list is null or empty, return 0
        if (prices == null || prices.length == 0) {
            return 0;
        }

        long maximumPotentialProfit = 0;

        // Go through every combination of current->future price scenario and determine max profit
        // Note: Negative prices are being accepted even for long equity positions
        // Note: We're currently allowing zero-priced "buys" to be accounted for as zero cost basis
        for (int i = 0; i < prices.length; i++) {
            long currentStockPrice = prices[i];

            for (int j = i+1; j < prices.length; j++) {
                long futureStockPrice = prices[j];
                long currentPotentialProfit = futureStockPrice - currentStockPrice;

                if (currentPotentialProfit > maximumPotentialProfit) {
                    maximumPotentialProfit = currentPotentialProfit;
                }
            }
        }

        return maximumPotentialProfit;
    }
}
