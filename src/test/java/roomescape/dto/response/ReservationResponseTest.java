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
        ReservationTime reservationTime = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.createReservationWithId(1L, "코기", LocalDate.of(2000, 11, 2),
                reservationTime);
        // when
        ReservationResponse response = ReservationResponse.from(reservation);
        // then
        assertAll(
                () -> assertThat(response.id()).isEqualTo(1L),
                () -> assertThat(response.name()).isEqualTo("코기"),
                () -> assertThat(response.date()).isEqualTo("2000-11-02"),
                () -> assertThat(response.time().id()).isEqualTo(1L),
                () -> assertThat(response.time().time()).isEqualTo("10:00")
        );
    }

    @Test
    @DisplayName("Reservations DTO 반환 테스트")
    void reservationsToDtoTest() {
        // given
        ReservationTime reservationTime1 = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        Reservation reservation1 = Reservation.createReservationWithId(1L, "코기", LocalDate.of(2000, 11, 2),
                reservationTime1);
        ReservationTime reservationTime2 = ReservationTime.createReservation(2L, LocalTime.of(11, 0));
        Reservation reservation2 = Reservation.createReservationWithId(2L, "멍구", LocalDate.of(2000, 12, 2),
                reservationTime2);
        List<Reservation> reservations = List.of(reservation1, reservation2);
        // when
        List<ReservationResponse> responses = ReservationResponse.from(reservations);
        // then
        assertAll(
                () -> assertThat(responses.get(0).id()).isEqualTo(1L),
                () -> assertThat(responses.get(0).name()).isEqualTo("코기"),
                () -> assertThat(responses.get(0).date()).isEqualTo("2000-11-02"),
                () -> assertThat(responses.get(0).time().id()).isEqualTo(1L),
                () -> assertThat(responses.get(0).time().time()).isEqualTo("10:00"),
                () -> assertThat(responses.get(1).id()).isEqualTo(2L),
                () -> assertThat(responses.get(1).name()).isEqualTo("멍구"),
                () -> assertThat(responses.get(1).date()).isEqualTo("2000-12-02"),
                () -> assertThat(responses.get(1).time().id()).isEqualTo(2L),
                () -> assertThat(responses.get(1).time().time()).isEqualTo("11:00")
        );

    }
}