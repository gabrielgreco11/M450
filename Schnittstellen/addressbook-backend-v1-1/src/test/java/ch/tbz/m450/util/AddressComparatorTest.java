package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressComparatorTest {

    @Test
    void testCompareLastname() {
        Address a1 = new Address(1, "Aab", "Hans");
        Address a2 = new Address(2, "Zed", "Paul");

        AddressComparator comp = new AddressComparator();

        assertTrue(comp.compare(a1, a2) < 0);
    }

    @Test
    void testCompareFirstnameWhenLastnamesEqual() {
        Address a1 = new Address(1, "Muster", "Anna");
        Address a2 = new Address(2, "Muster", "Bernd");

        AddressComparator comp = new AddressComparator();

        assertTrue(comp.compare(a1, a2) < 0);
    }
}
