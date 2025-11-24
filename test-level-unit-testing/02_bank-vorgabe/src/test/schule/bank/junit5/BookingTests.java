package schule.bank.junit5;
import schule.Booking;

import static org.junit.jupiter.api.Assertions.*;
class BookingTests {

	void testBookingCreation() {
		Booking booking = new Booking(12345, 50000);

		assertAll("Booking properties",
				() -> assertEquals(12345, booking.getDate(), "Date should be set correctly"),
				() -> assertEquals(50000, booking.getAmount(), "Amount should be set correctly")
		);
	}
}