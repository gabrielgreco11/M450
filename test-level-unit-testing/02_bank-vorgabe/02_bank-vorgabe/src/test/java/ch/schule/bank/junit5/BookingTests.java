package ch.schule;
import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class BookingTests {

	@Testvoid testBookingCreation() {
		Booking booking = new Booking(12345, 50000);

		assertAll("Booking properties",
				() -> assertEquals(12345, booking.getDate(), "Date should be set correctly"),
				() -> assertEquals(50000, booking.getAmount(), "Amount should be set correctly")
		);
	}
}