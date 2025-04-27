package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@JdbcTest
class JdbcReservationRepositoryTest {

    private ReservationRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach(){
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation_time("
                + " id BIGINT NOT NULL AUTO_INCREMENT,"
                + " start_at VARCHAR(255) NOT NULL,"
                + " PRIMARY KEY (id));");
        jdbcTemplate.execute("CREATE TABLE reservation("
                + " id BIGINT NOT NULL AUTO_INCREMENT,"
                + " name VARCHAR(255) NOT NULL,"
                + " date VARCHAR(255) NOT NULL,"
                + " time_id BIGINT NOT NULL,"
                + " PRIMARY KEY (id),"
                + " FOREIGN KEY (time_id) references reservation_time(id))");
        repository=new JdbcReservationRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("초기에 데이터가 존재하지 않는다.")
    void testInitialRepository() {
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약을 추가할 수 있다")
    void createTest() {
        // given
        jdbcTemplate.update("insert into reservation_time(id,start_at) values(?,?)",1L,"10:00");
        ReservationTime reservationTime = ReservationTime.createReservation(1L, LocalTime.of(10, 0));
        Reservation reservation = Reservation.createReservationWithoutId("멍구", LocalDate.of(2000, 11, 2),
                reservationTime);

        // when
        repository.create(reservation);

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
        Reservation reservationEntity = repository.create(reservation);

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
