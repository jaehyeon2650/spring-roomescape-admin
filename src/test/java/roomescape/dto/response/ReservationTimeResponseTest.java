package roomescape.dto.response;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.model.ReservationTime;

class ReservationTimeResponseTest {

    @Test
    @DisplayName("DTO 생성 테스트")
    void createDtoTest() {
        // given
        ReservationTime reservation = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        // when
        ReservationTimeResponse response = ReservationTimeResponse.from(reservation);
        // then
        assertThat(response.startAt()).isEqualTo("10:00");
        assertThat(response.id()).isEqualTo(1L);
    }
}