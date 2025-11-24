package schule.bank.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import schule.SalaryAccount;

import static org.junit.jupiter.api.Assertions.*;

class SalaryAccountTests {

    private SalaryAccount account;

    @BeforeEach
    void setUp() {
        // Lohnkonto mit einem Kreditlimit von -2000
        account = new SalaryAccount("P-1001", -200000);
    }

    @Test
    @DisplayName("Abhebung bis zum Kreditlimit ist erfolgreich")
    void testWithdrawToCreditLimit() {
        account.deposit(10, 100000); // Saldo: 1000
        assertTrue(account.withdraw(11, 300000)); // Saldo: -2000
        assertEquals(-200000, account.getBalance());
    }

    @Test
    @DisplayName("Abhebung über das Kreditlimit hinaus schlägt fehl")
    void testWithdrawExceedsCreditLimit() {
        account.deposit(10, 100000);
        assertFalse(account.withdraw(11, 300001));
        assertEquals(100000, account.getBalance());
    }
}