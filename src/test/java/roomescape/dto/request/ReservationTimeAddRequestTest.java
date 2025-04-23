package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.model.ReservationTime;

class ReservationTimeAddRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"00:61", "24:21", "24:00", "13~00"})
    @DisplayName("시간 패턴 검증 테스트")
    void validateTimeTest(String time) {
        assertThatThrownBy(() -> new ReservationTimeAddRequest(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 00:00 형식입니다");
    }

    @Test
    @DisplayName("도메인 변경 테스트")
    void dtoToReservationTimeWithoutIdTest() {
        // given
        ReservationTimeAddRequest request = new ReservationTimeAddRequest("13:00");
        // when
        ReservationTime reservationTime = request.dtoToReservationTimeWithoutId();
        // then
        assertThat(reservationTime.getId()).isNull();
        assertThat(reservationTime.getStartAt()).isEqualTo(LocalTime.of(13, 0));
    }
}