package com.devops.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    // Configuration fake : TVA 20%, Livraison gratuite dès 50€
    private final PricingConfig fakeConfig = new PricingConfig(20.0, 50.0);
    private final PricingService service = new PricingService(fakeConfig);

    @Test
    void shouldApplyVatCorrectly() {
        // 100€ HT + 20% TVA = 120€ TTC
        assertEquals(120.0, service.applyVat(100.0), 0.01);
    }

    @Test
    void shouldApplyVipDiscount() {
        // 100€ TTC -> VIP -10% -> 90€
        assertEquals(90.0, service.applyVipDiscount(100.0, true), 0.01);
    }

    @Test
    void shouldNotApplyVipDiscountIfCustomerIsNotVip() {
        assertEquals(100.0, service.applyVipDiscount(100.0, false), 0.01);
    }

    @Test
    void shouldApplyShippingCostBelowThreshold() {
        // Seuil à 50€. Montant 10€ -> Frais 4.99€
        assertEquals(4.99, service.shippingCost(10.0), 0.01);
    }

    @Test
    void shouldOfferShippingAboveThreshold() {
        // Seuil à 50€. Montant 50€ -> Frais 0€
        assertEquals(0.0, service.shippingCost(50.0), 0.01);
    }

    @Test
    void shouldCalculateFinalTotalForVip() {
        // 100 HT -> 120 TTC -> VIP (108) -> >50 donc livraison 0 -> Total 108
        assertEquals(108.0, service.finalTotal(100.0, true), 0.01);
    }

    @Test
    void shouldCalculateFinalTotalForNonVipSmallAmount() {
        // 10 HT -> 12 TTC -> Pas VIP (12) -> <50 donc livraison 4.99 -> Total 16.99
        assertEquals(16.99, service.finalTotal(10.0, false), 0.01);
    }
}