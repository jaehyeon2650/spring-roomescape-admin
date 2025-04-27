package roomescape.dto.request;

import java.util.regex.Pattern;

public class ReservationTimeRequest {

    private static final Pattern TIME_PATTERN = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    private final String startAt;

    public ReservationTimeRequest(String startAt) {
        validateTime(startAt);
        this.startAt = startAt;
    }

    private void validateTime(String time) {
        if (!TIME_PATTERN.matcher(time).matches()) {
            throw new IllegalArgumentException("시간은 00:00 형식입니다");
        }
    }

    public String getStartAt() {
        return startAt;
    }
}
