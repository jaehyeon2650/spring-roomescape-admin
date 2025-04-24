package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.model.ReservationTime;

@JdbcTest
class JdbcReservationTimeRepositoryTest {

    private JdbcReservationTimeRepository repository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DROP TABLE reservation_time IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation_time("
                + " id BIGINT NOT NULL AUTO_INCREMENT,"
                + " start_at VARCHAR(255) NOT NULL,"
                + " PRIMARY KEY (id));");
        repository = new JdbcReservationTimeRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("reservationTime 추가 테스트")
    void addTest() {
        // given
        ReservationTime time = ReservationTime.createReservationTimeWithoutId(LocalTime.of(11, 0));
        // when
        Long id = repository.add(time);
        // then
        ReservationTime findTime = repository.findById(id);
        assertThat(findTime).isNotNull();
        assertThat(findTime.getStartAt()).isEqualTo(time.getStartAt());
    }

    @Test
    @DisplayName("reservationTime 조회 테스트")
    void findById() {
        // given
        ReservationTime time1 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(10, 0));
        repository.add(time1);
        repository.add(time2);
        Long time3Id = repository.add(time3);
        // when
        ReservationTime findTime = repository.findById(time3Id);
        // then
        assertThat(findTime.getStartAt()).isEqualTo(time3.getStartAt());
    }

    @Test
    @DisplayName("전체 reservationTime 조회 테스트")
    void findAllReservationTimeTest() {
        // given
        ReservationTime time1 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(10, 0));
        repository.add(time1);
        repository.add(time2);
        repository.add(time3);
        // when
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        // then
        assertThat(allReservationTimes).hasSize(3);
    }

    @Test
    @DisplayName("reservationTime 삭제 테스트")
    void deleteReservationTimeTest() {
        // given
        ReservationTime time1 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(11, 0));
        ReservationTime time2 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(12, 0));
        ReservationTime time3 = ReservationTime.createReservationTimeWithoutId(LocalTime.of(10, 0));
        repository.add(time1);
        repository.add(time2);
        Long time3Id = repository.add(time3);
        // when
        repository.deleteById(time3Id);
        // then
        List<ReservationTime> allReservationTimes = repository.findAllReservationTimes();
        assertThat(allReservationTimes).hasSize(2);
        assertThat(allReservationTimes.get(0).getStartAt()).isNotEqualTo(time3.getStartAt());
        assertThat(allReservationTimes.get(1).getStartAt()).isNotEqualTo(time3.getStartAt());
    }
}