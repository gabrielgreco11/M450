package ch.tbz.m450;

import ch.tbz.m450.repository.Address;
import ch.tbz.m450.repository.AddressRepository;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private AddressService addressService;

    @BeforeEach
    void setup() {
        addressRepository = Mockito.mock(AddressRepository.class);
        addressService = new AddressService(addressRepository);
    }

    @Test
    void testSave() {
        Address address = new Address(1, "Muster", "Max");
        when(addressRepository.save(address)).thenReturn(address);

        Address result = addressService.save(address);

        assertEquals(address, result);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void testGetAllSorted() {
        Address a1 = new Address(1, "Müller", "Hans");
        Address a2 = new Address(2, "Meier", "Anna");

        when(addressRepository.findAll()).thenReturn(List.of(a1, a2));

        List<Address> result = addressService.getAll();

        assertEquals(List.of(a2, a1), result); // alphabetisch Meier < Müller
    }

    @Test
    void testGetAddress() {
        Address address = new Address(1, "Muster", "Max");
        when(addressRepository.findById(1)).thenReturn(Optional.of(address));

        Optional<Address> result = addressService.getAddress(1);

        assertTrue(result.isPresent());
        assertEquals(address, result.get());
    }
}
