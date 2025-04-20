package roomescape.repository;

import java.util.List;
import roomescape.model.Reservation;

public interface ReservationRepository {
    List<Reservation> findAll();

    Reservation findById(Long id);

    void deleteById(Long id);

    Long add(Reservation reservation);
}
