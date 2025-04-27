package roomescape.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import roomescape.model.ReservationTime;

class ReservationTimeRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"00:61", "24:21", "24:00", "13~00"})
    @DisplayName("시간 패턴 검증 테스트")
    void validateTimeTest(String time) {
        assertThatThrownBy(() -> new ReservationTimeRequest(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간은 00:00 형식입니다");
    }
}