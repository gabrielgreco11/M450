package ch.schule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankTests {

    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
    }

    @Test
    @DisplayName("Erstellung eines Sparkontos")
    void testCreateSavingsAccount() {
        String id = bank.createSavingsAccount();
        assertNotNull(id);
        assertTrue(id.startsWith("S-"));
        assertEquals(0, bank.getBalance(id));
    }

    @Test
    @DisplayName("Erstellung eines Lohnkontos")
    void testCreateSalaryAccount() {
        String id = bank.createSalaryAccount(-50000);
        assertNotNull(id);
        assertTrue(id.startsWith("P-"));
        assertEquals(0, bank.getBalance(id));
    }
    
    @Test
    @DisplayName("Erstellung eines Lohnkontos mit ungültigem Limit schlägt fehl")
    void testCreateSalaryAccountWithInvalidLimit() {
        String id = bank.createSalaryAccount(100); // Positives Limit ist ungültig
        assertNull(id);
    }

    @Test
    @DisplayName("Transaktionen über die Bank-Klasse")
    void testTransactionsViaBank() {
        String id = bank.createSavingsAccount();
        assertTrue(bank.deposit(id, 10, 50000));
        assertEquals(50000, bank.getBalance(id));

        assertTrue(bank.withdraw(id, 11, 20000));
        assertEquals(30000, bank.getBalance(id));
    }

    @Test
    @DisplayName("Transaktion auf nicht-existierendes Konto schlägt fehl")
    void testTransactionOnNonExistentAccount() {
        assertFalse(bank.deposit("S-9999", 10, 50000));
        assertFalse(bank.withdraw("S-9999", 11, 20000));
    }
    
    @Test
    @DisplayName("Bank-Saldo wird berechnet")
    void testBankBalance() {
        String id1 = bank.createSavingsAccount();
        bank.deposit(id1, 10, 100000); // Saldo Konto 1: 1000

        String id2 = bank.createSalaryAccount(-50000);
        bank.deposit(id2, 10, 20000); // Saldo Konto 2: 200
        
        // Die Formel in Bank.getBalance() ist `balance -= aa[i].getBalance();`
        // Erwartet wird also -(100000 + 20000) = -120000
        assertEquals(-120000, bank.getBalance());
    }
}