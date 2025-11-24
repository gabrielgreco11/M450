package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTests {

    private SavingsAccount account;

    @BeforeEach
    void setUp() {
        account = new SavingsAccount("S-1000");
    }

    @Test
    @DisplayName("Erfolgreiche Einzahlung")
    void testSuccessfulDeposit() {
        assertTrue(account.deposit(10, 100000));
        assertEquals(100000, account.getBalance());
    }

    @Test
    @DisplayName("Einzahlung mit negativem Betrag schlägt fehl")
    void testNegativeDeposit() {
        assertFalse(account.deposit(10, -50000));
        assertEquals(0, account.getBalance());
    }

    @Test
    @DisplayName("Erfolgreiche Abhebung")
    void testSuccessfulWithdraw() {
        account.deposit(10, 100000);
        assertTrue(account.withdraw(11, 50000));
        assertEquals(50000, account.getBalance());
    }

    @Test
    @DisplayName("Abhebung übersteigt Saldo und schlägt fehl")
    void testWithdrawExceedsBalance() {
        account.deposit(10, 100000);
        assertFalse(account.withdraw(11, 150000));
        assertEquals(100000, account.getBalance());
    }

    @Test
    @DisplayName("Transaktion mit veraltetem Datum schlägt fehl")
    void testOutdatedTransaction() {
        account.deposit(20, 100000);
        assertFalse(account.withdraw(19, 50000));
        assertEquals(100000, account.getBalance());
    }
}