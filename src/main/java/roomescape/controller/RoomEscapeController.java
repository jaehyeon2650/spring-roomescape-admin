package roomescape.controller;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.request.ReservationTimeAddRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.model.ReservationTime;
import roomescape.repository.JdbcReservationTimeRepository;
import roomescape.repository.ReservationRepository;

@RestController
public class RoomEscapeController {

    private final ReservationRepository repository;
    private final JdbcReservationTimeRepository timeRepository;

    public RoomEscapeController(ReservationRepository repository, JdbcReservationTimeRepository timeRepository) {
        this.repository = repository;
        this.timeRepository = timeRepository;
    }

    @GetMapping("/reservations")
    public List<ReservationResponse> findReservations() {
        return ReservationResponse.from(repository.findAll());
    }

    @PostMapping("/reservations")
    public ReservationResponse addReservation(@RequestBody ReservationRequest request) {
        Long id = repository.add(request.dtoToReservationWithoutId());
        return ReservationResponse.from(repository.findById(id));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long id) {
        try {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/times")
    public ReservationTimeResponse addReservationTime(@RequestBody ReservationTimeAddRequest request) {
        Long id = timeRepository.addReservationTime(request.dtoToReservationTimeWithoutId());
        ReservationTime reservationTime = timeRepository.findReservationTimeById(id);
        return ReservationTimeResponse.from(reservationTime);
    }

    @GetMapping("/times")
    public List<ReservationTimeResponse> findAllReservationTimes() {
        List<ReservationTime> reservationTimes = timeRepository.findAllReservationTimes();
        return ReservationTimeResponse.from(reservationTimes);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> deleteReservationTime(@PathVariable("id") Long id) {
        try {
            timeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
