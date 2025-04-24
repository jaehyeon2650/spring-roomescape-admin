package roomescape.model;

import java.time.LocalDate;

public class Reservation {
    private final Long id;
    private final String name;
    private final LocalDate reservationDate;
    private final ReservationTime reservationTime;

    private Reservation(Long id, String name, LocalDate reservationDate, ReservationTime reservationTime) {
        this.id = id;
        this.name = name;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
    }

    public static Reservation createReservationWithoutId(String name, LocalDate reservationDate,
                                                         ReservationTime reservationTime) {
        return new Reservation(null, name, reservationDate, reservationTime);
    }

    public static Reservation createReservationWithId(Long id, String name, LocalDate reservationDate,
                                                      ReservationTime reservationTime) {
        return new Reservation(id, name, reservationDate, reservationTime);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public boolean sameId(Long id) {
        return this.id.equals(id);
    }
}
