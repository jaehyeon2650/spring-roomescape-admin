package roomescape.repository;

import java.util.List;
import roomescape.model.ReservationTime;

public interface ReservationTimeRepository {
    ReservationTime create(ReservationTime reservationTime);

    ReservationTime findById(Long id);

    List<ReservationTime> findAll();

    void deleteById(Long id);
}
