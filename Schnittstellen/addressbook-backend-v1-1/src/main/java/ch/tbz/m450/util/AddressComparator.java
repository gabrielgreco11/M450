package ch.tbz.m450.util;

import ch.tbz.m450.repository.Address;
import java.util.Comparator;

public class AddressComparator implements Comparator<Address> {

    @Override
    public int compare(Address a1, Address a2) {
        int last = a1.getLastname().compareToIgnoreCase(a2.getLastname());
        if (last != 0) return last;
        return a1.getFirstname().compareToIgnoreCase(a2.getFirstname());
    }
}
