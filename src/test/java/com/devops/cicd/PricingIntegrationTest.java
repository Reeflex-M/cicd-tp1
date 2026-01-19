package com.devops.cicd;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PricingIntegrationTest {

    @Test
    void fullPricingFlow_withRealConfigFile() {
        // 1. Charger la vraie config (TVA=20, Seuil=50)
        PricingConfigLoader loader = new PricingConfigLoader();
        PricingConfig realConfig = loader.load();
        
        // 2. Initialiser le service
        PricingService service = new PricingService(realConfig);
        
        // 3. Scénario : 100€ HT, VIP
        // Attendu : 100 + 20% = 120 -> VIP -10% = 108 -> Livraison (108 > 50) = 0 -> TOTAL 108
        double total = service.finalTotal(100.0, true);
        
        assertEquals(108.0, total, 0.01);
    }
}