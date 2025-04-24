package roomescape.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.dao.EmptyResultDataAccessException;
import roomescape.model.ReservationTime;

public class MemoryReservationTimeRepository implements ReservationTimeRepository {

    private final List<ReservationTime> reservationTimes;

    private AtomicLong index = new AtomicLong(0);

    public MemoryReservationTimeRepository() {
        this.reservationTimes = new ArrayList<>();
    }

    @Override
    public ReservationTime add(ReservationTime reservationTime) {
        long currentIndex = index.incrementAndGet();
        ReservationTime reservationTimeEntity = reservationTime.toEntity(currentIndex);
        reservationTimes.add(reservationTimeEntity);
        return reservationTimeEntity;
    }

    @Override
    public ReservationTime findById(Long id) {
        return reservationTimes.stream()
                .filter(reservationTime -> reservationTime.hasSameId(id))
                .findAny()
                .orElseThrow(() -> new EmptyResultDataAccessException("해당 ID가 존재하지 않습니다.", 1));
    }

    @Override
    public List<ReservationTime> findAllReservationTimes() {
        return Collections.unmodifiableList(reservationTimes);
    }

    @Override
    public void deleteById(Long id) {
        ReservationTime findReservationTime = findById(id);
        reservationTimes.remove(findReservationTime);
    }
}
