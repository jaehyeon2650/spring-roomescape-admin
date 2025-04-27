package roomescape.service;

import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              ReservationTimeRepository timeRepository) {
        this.reservationRepository = reservationRepository;
        this.timeRepository = timeRepository;
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        ReservationTime reservationTime = timeRepository.findById(request.getTimeId());
        Reservation reservationWithoutId = Reservation.createReservationWithoutId(request.getName(), request.getDate(),
                reservationTime);
        Reservation reservationEntity = reservationRepository.create(reservationWithoutId);
        return ReservationResponse.from(reservationEntity);
    }

    public List<ReservationResponse> findAllReservations() {
        return ReservationResponse.from(reservationRepository.findAll());
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeAddRequest request) {
        ReservationTime time = ReservationTime.createReservationTimeWithoutId(LocalTime.parse(request.getStartAt()));
        ReservationTime timeEntity = timeRepository.create(time);
        return ReservationTimeResponse.from(timeEntity);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return ReservationTimeResponse.from(timeRepository.findAll());
    }

    public void deleteReservationTime(Long id) {
        timeRepository.deleteById(id);
    }
}
