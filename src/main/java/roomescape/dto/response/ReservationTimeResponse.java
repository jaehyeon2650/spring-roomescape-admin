package roomescape.dto.response;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.model.ReservationTime;

public record ReservationTimeResponse(
        Long id,
        String time
) {
    public static ReservationTimeResponse from(ReservationTime reservationTime) {
        LocalTime time = reservationTime.getStartAt();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String timeFormat = time.format(timeFormatter);
        return new ReservationTimeResponse(reservationTime.getId(), timeFormat);
    }

    public static List<ReservationTimeResponse> from(List<ReservationTime> reservationTimes) {
        return reservationTimes.stream()
                .map(ReservationTimeResponse::from)
                .toList();
    }
}
