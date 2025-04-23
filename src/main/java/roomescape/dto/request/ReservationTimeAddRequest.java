package roomescape.dto.request;

import java.time.LocalTime;
import java.util.regex.Pattern;
import roomescape.model.ReservationTime;

public class ReservationTimeAddRequest {

    private static final Pattern TIME_PATTERN = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    private final String time;

    public ReservationTimeAddRequest(String time) {
        validateTime(time);
        this.time = time;
    }

    public ReservationTime dtoToReservationTimeWithoutId(){
        return ReservationTime.createReservationTimeWithoutId(LocalTime.parse(time));
    }

    private void validateTime(String time) {
        if (!TIME_PATTERN.matcher(time).matches()) {
            throw new IllegalArgumentException("시간은 00:00 형식입니다");
        }
    }

    public String getTime() {
        return time;
    }
}
