package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationRepositoryTest {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("초기에 데이터가 존재하지 않는다.")
    void testInitialRepository() {
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약을 추가할 수 있다")
    void addTest() {
        // given
        jdbcTemplate.update("insert into reservation_time(id,start_at) values(?,?)",1L,"10:00");
        ReservationTime reservationTime = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.createReservationWithoutId("멍구", LocalDate.of(2000, 11, 2),
                reservationTime);

        // when
        repository.add(reservation);

        // then
        assertThat(repository.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("id로 예약을 삭제할 수 있다.")
    void deleteByIdTest() {
        // given
        jdbcTemplate.update("insert into reservation_time(id,start_at) values(?,?)",1L,"10:00");
        ReservationTime reservationTime = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.createReservationWithoutId("멍구", LocalDate.of(2000, 11, 2),
                reservationTime);
        Reservation reservationEntity = repository.add(reservation);

        // when
        repository.deleteById(reservationEntity.getId());

        // then
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("없는 id로 삭제하려고 할 때 예외가 발생한다.")
    void deleteByIdTest_exception() {
        // given
        Long id = 0L;

        // when & then
        assertThatThrownBy(() -> repository.deleteById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }
}
