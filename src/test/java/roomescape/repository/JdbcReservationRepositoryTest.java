package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationRepositoryTest {

    @Autowired
    private ReservationRepository repository;

    @DisplayName("초기에 데이터가 존재하지 않는다.")
    @Test
    void testInitialRepository() {
        assertThat(repository.findAll()).hasSize(3);
    }

    @DisplayName("예약을 추가할 수 있다")
    @Test
    void addTest() {
        // given
        Reservation reservation = Reservation.createReservationWithoutId("멍구", LocalDateTime.of(2024, 4, 4, 10, 0));

        // when
        repository.add(reservation);

        // then
        assertThat(repository.findAll()).hasSize(4);
    }

    @DisplayName("id로 예약을 찾을 수 있다.")
    @Test
    void findByIdTest() {
        // given
        Long id = 1L;

        // when
        Reservation reservation = repository.findById(id);

        // then
        assertThat(reservation.getId()).isEqualTo(1L);
        assertThat(reservation.getName()).isEqualTo("브라운");
        assertThat(reservation.getReservationTime()).isEqualTo(LocalDateTime.of(2024, 4, 1, 10, 0));
    }

    @DisplayName("없는 id로 찾으려고 할 때 예외가 발생한다.")
    @Test
    void findByIdTest_exception() {
        // given
        Long id = 0L;

        // when & then
        assertThatThrownBy(() -> repository.findById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @DisplayName("id로 예약을 삭제할 수 있다.")
    @Test
    void deleteByIdTest() {
        // given
        Long id = 1L;

        // when
        repository.deleteById(id);

        // then
        assertThat(repository.findAll()).hasSize(2);
    }

    @DisplayName("없는 id로 삭제하려고 할 때 예외가 발생한다.")
    @Test
    void deleteByIdTest_exception() {
        // given
        Long id = 0L;

        // when & then
        assertThatThrownBy(() -> repository.deleteById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

}
