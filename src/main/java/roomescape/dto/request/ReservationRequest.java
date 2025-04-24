package roomescape.dto.request;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class ReservationRequest {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");

    private final String date;
    private final String name;
    private final Long timeId;

    public ReservationRequest(String date, String name, Long timeId) {
        validateDateAndTime(date);
        this.date = date;
        this.timeId = timeId;
        this.name = name;
    }

    private void validateDateAndTime(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("날짜는 0000-00-00 형식입니다");
        }
    }

    public LocalDate getDate() {
        return LocalDate.parse(date);
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }
}
