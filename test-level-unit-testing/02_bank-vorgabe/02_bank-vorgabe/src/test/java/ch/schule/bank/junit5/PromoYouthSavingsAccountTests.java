package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PromoYouthSavingsAccountTests {

    private PromoYouthSavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new PromoYouthSavingsAccount("Y-1002");
    }

    @Test
    @DisplayName("Einzahlung erhält 1% Bonus")
    void testDepositWithBonus() {
        assertTrue(account.deposit(10, 100000)); // 1000 einzahlen
        // Erwartet: 1000 + 1% von 1000 = 1010
        assertEquals(101000, account.getBalance());
    }
}