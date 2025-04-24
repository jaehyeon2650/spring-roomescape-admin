package roomescape.model;

import java.time.LocalTime;

public class ReservationTime {
    private final Long id;
    private final LocalTime startAt;

    private ReservationTime(Long id, LocalTime startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime createReservationTimeWithoutId(LocalTime startAt) {
        return new ReservationTime(null, startAt);
    }

    public static ReservationTime createReservation(Long id, LocalTime startAt) {
        return new ReservationTime(id, startAt);
    }

    public ReservationTime toEntity(Long id) {
        return new ReservationTime(id, this.startAt);
    }

    public boolean hasSameId(Long id) {
        return this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
