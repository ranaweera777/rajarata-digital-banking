package com.rajarata.bank.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    // Exchange rates relative to USD
    private static Map<String, Double> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.73);
        exchangeRates.put("LKR", 320.0);  
        exchangeRates.put("INR", 83.0);
        exchangeRates.put("JPY", 149.0);
        exchangeRates.put("AUD", 1.53);
    }

    public static double convert(double amount, String fromCurrency, String toCurrency) {
        if (!exchangeRates.containsKey(fromCurrency) || !exchangeRates.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Unsupported currency");
        }

        // Convert to USD first, then to target currency
        double amountInUSD = amount / exchangeRates.get(fromCurrency);
        double convertedAmount = amountInUSD * exchangeRates.get(toCurrency);

        System.out.printf("Converted %.2f %s to %.2f %s%n", 
                          amount, fromCurrency, convertedAmount, toCurrency);
        return convertedAmount;
    }

    public static double getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRates.get(toCurrency) / exchangeRates.get(fromCurrency);
    }

    public static void updateExchangeRate(String currency, double rate) {
        exchangeRates.put(currency, rate);
    }
}
