package com.devops.cicd;

public final class PricingService {

    private final PricingConfig config;

    public PricingService(PricingConfig config) {
        this.config = config;
    }

    public double applyVat(double amountExclVat) {
        // Ex: 100 * (1 + 20/100) = 120
        return amountExclVat * (1 + config.getVatRate() / 100);
    }

    public double applyVipDiscount(double amount, boolean vip) {
        if (vip) {
            // Remise de 10% -> on garde 90% du prix
            return amount * 0.90;
        }
        return amount;
    }

    public double shippingCost(double amount) {
        if (amount >= config.getFreeShippingThreshold()) {
            return 0.0;
        }
        return 4.99;
    }

    /**
     * - TVA appliquée d'abord : HT -> TTC
     * - remise VIP appliquée sur TTC
     * - frais de livraison ajoutés ensuite (calculés sur TTC)
     */
    public double finalTotal(double amountExclVat, boolean vip) {
        double amountWithVat = applyVat(amountExclVat);
        double amountAfterDiscount = applyVipDiscount(amountWithVat, vip);
        double shipping = shippingCost(amountAfterDiscount);
        
        return amountAfterDiscount + shipping;
    }
}