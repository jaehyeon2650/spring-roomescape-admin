package roomescape.dto.response;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import roomescape.model.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        ReservationTimeResponse time
) {

    public static ReservationResponse from(Reservation reservation) {
        LocalDate date = reservation.getReservationDate();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                date.format(dateFormatter),
                ReservationTimeResponse.from(reservation.getReservationTime()));
    }

    public static List<ReservationResponse> from(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }
}
