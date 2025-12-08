package ch.tbz.m450.controller;

import ch.tbz.m450.repository.Address;
import ch.tbz.m450.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressControllerTest {

    private AddressService addressService;
    private AddressController addressController;

    @BeforeEach
    void setup() {
        addressService = Mockito.mock(AddressService.class);
        addressController = new AddressController(addressService);
    }

    @Test
    void testGetAddresses() {
        when(addressService.getAll()).thenReturn(List.of(
                new Address(1, "Muster", "Max")
        ));

        var response = addressController.getAddresses();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAddressFound() {
        Address address = new Address(1, "Muster", "Max");
        when(addressService.getAddress(1)).thenReturn(Optional.of(address));

        var response = addressController.getAddress(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(address, response.getBody());
    }

    @Test
    void testGetAddressNotFound() {
        when(addressService.getAddress(1)).thenReturn(Optional.empty());

        var response = addressController.getAddress(1);

        assertEquals(404, response.getStatusCodeValue());
    }
}
