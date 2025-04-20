package roomescape.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("select * from reservation", reservationRowMapper());
    }

    @Override
    public Long add(Reservation reservation) {
        return 0L;
    }

    @Override
    public Reservation findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rn) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String time = rs.getString("time");
            LocalDateTime reservationTime = LocalDateTime.parse(date + "T" + time);
            return Reservation.createReservation(id, name, reservationTime);
        };
    }

}
