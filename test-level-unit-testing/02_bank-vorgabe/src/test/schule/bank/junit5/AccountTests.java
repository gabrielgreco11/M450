package schule.bank.junit5;

import schule.Account;
import schule.SalaryAccount;
import schule.SavingsAccount;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the class Account.
 */
public class AccountTests {

    /**
     * Tests the initialization of an account.
     */
    @Test
    public void testInit() {
        Account acc = new SavingsAccount("ABC123");

        assertEquals("ABC123", acc.getId());
        assertEquals(0, acc.getBalance());
        // No bookings yet → can transact any date
        assertTrue(acc.canTransact(100));
    }

    /**
     * Tests deposit.
     */
    @Test
    public void testDeposit() {
        Account acc = new SavingsAccount("A1");

        assertTrue(acc.deposit(10, 500));
        assertEquals(500, acc.getBalance());

        // negative amount → rejected
        assertFalse(acc.deposit(20, -100));
        assertEquals(500, acc.getBalance());

        // out-of-order date → rejected
        assertFalse(acc.deposit(5, 200)); // earlier than last booking (10)
        assertEquals(500, acc.getBalance());
    }

    /**
     * Tests withdraw.
     */
    @Test
    public void testWithdraw() {
        Account acc = new SavingsAccount("A1");

        acc.deposit(10, 1000);

        // OK withdrawal
        assertTrue(acc.withdraw(20, 300));
        assertEquals(700, acc.getBalance());

        // not enough balance for SavingsAccount
        assertFalse(acc.withdraw(30, 800)); // would go negative
        assertEquals(700, acc.getBalance());

        // negative amount
        assertFalse(acc.withdraw(40, -5));

        // wrong transaction order
        assertFalse(acc.withdraw(15, 100)); // before date 20
    }

    /**
     * Tests reference from SavingsAccount.
     */
    @Test
    public void testReferences() {
        Account a = new SavingsAccount("SAV1");

        assertTrue(a instanceof SavingsAccount);
        assertFalse(a instanceof SalaryAccount);
    }

    /**
     * Tests canTransact flag.
     */
    @Test
    public void testCanTransact() {
        Account acc = new SavingsAccount("A1");

        // No bookings yet → always true
        assertTrue(acc.canTransact(1));

        acc.deposit(10, 100);

        assertTrue(acc.canTransact(10));
        assertTrue(acc.canTransact(11));

        // Earlier than last booking → false
        assertFalse(acc.canTransact(5));
    }

    /**
     * Tests print() output (not content).
     */
    @Test
    public void testPrint() {
        Account acc = new SavingsAccount("A1");
        acc.deposit(10, 1000);
        acc.withdraw(20, 300);

        // Capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        acc.print();

        String output = out.toString();

        assertTrue(output.contains("Kontoauszug 'A1'"));
        assertTrue(output.contains("Saldo"));
        assertFalse(output.isEmpty());
    }

    /**
     * Tests monthly print.
     */
    @Test
    public void testMonthlyPrint() {
        Account acc = new SavingsAccount("A1");

        // January 2020 → startDate = (2020-1970)*360 + 0
        int dJan = (2020 - 1970) * 360;

        acc.deposit(dJan + 1, 500);
        acc.withdraw(dJan + 5, 100);

        // Capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        acc.print(2020, 1);

        String output = out.toString();

        assertTrue(output.contains("Kontoauszug 'A1' Monat: 1.2020"));
        assertFalse(output.isEmpty());
    }
}
