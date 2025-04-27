package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

class ReservationResponseTest {

    @Test
    @DisplayName("Reservation DTO 변환 테스트")
    void reservationToDtoTest() {
        // given
        ReservationTime reservationTime = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.of(1L, "코기", LocalDate.of(2000, 11, 2),
                reservationTime);
        // when
        ReservationResponse response = ReservationResponse.from(reservation);
        // then
        assertThat(response).isEqualTo(
                new ReservationResponse(1L, "코기", "2000-11-02", new ReservationTimeResponse(1L, "10:00")));
    }

    @Test
    @DisplayName("Reservations DTO 반환 테스트")
    void reservationsToDtoTest() {
        // given
        ReservationTime reservationTime1 = ReservationTime.of(1L, LocalTime.of(10, 0));
        Reservation reservation1 = Reservation.of(1L, "코기", LocalDate.of(2000, 11, 2),
                reservationTime1);
        ReservationTime reservationTime2 = ReservationTime.of(2L, LocalTime.of(11, 0));
        Reservation reservation2 = Reservation.of(2L, "멍구", LocalDate.of(2000, 12, 2),
                reservationTime2);
        List<Reservation> reservations = List.of(reservation1, reservation2);
        // when
        List<ReservationResponse> responses = ReservationResponse.from(reservations);
        // then
        assertAll(
                () -> assertThat(responses.get(0)).isEqualTo(
                        new ReservationResponse(1L, "코기", "2000-11-02", new ReservationTimeResponse(1L, "10:00"))),
                () -> assertThat(responses.get(1)).isEqualTo(
                        new ReservationResponse(2L, "멍구", "2000-12-02", new ReservationTimeResponse(2L, "11:00")))
        );

    }
}