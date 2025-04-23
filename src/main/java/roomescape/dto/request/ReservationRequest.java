package roomescape.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;
import roomescape.model.Reservation;

public class ReservationRequest {

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$");
    private static final Pattern TIME_PATTERN = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");

    private final String date;
    private final String time;
    private final String name;

    public ReservationRequest(String date, String time, String name) {
        validateDateAndTime(date, time);
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation dtoToReservationWithoutId() {
        LocalDate date = LocalDate.parse(this.date);
        LocalTime time = LocalTime.parse(this.time);

        LocalDateTime reservationTime = LocalDateTime.of(date, time);
        return Reservation.createReservationWithoutId(name, reservationTime);
    }

    private void validateDateAndTime(String date, String time) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            throw new IllegalArgumentException("날짜는 0000-00-00 형식입니다");
        }
        if (!TIME_PATTERN.matcher(time).matches()) {
            throw new IllegalArgumentException("시간은 00:00 형식입니다");
        }
    }
}
