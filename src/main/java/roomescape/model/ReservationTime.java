package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    public ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime createReservationTimeWithoutId(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime createReservation(Long id, LocalTime startAt){
        return new ReservationTime(id,startAt);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
