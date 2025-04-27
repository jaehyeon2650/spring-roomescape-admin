package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;

@Repository
public class JdbcReservationRepository implements ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Reservation> findAll() {
        return jdbcTemplate.query("select r.id as reservation_id,"
                + "r.name as name,"
                + "r.date as date,"
                + "t.id as time_id,"
                + "t.start_at as start_at "
                + "from reservation r "
                + "inner join reservation_time t "
                + "on r.time_id = t.id ", reservationRowMapper());
    }

    @Override
    public Reservation create(Reservation reservation) {
        String name = reservation.getName();
        LocalDate reservationDate = reservation.getReservationDate();
        ReservationTime reservationTime = reservation.getReservationTime();
        String date = reservationDate.toString();
        Long timeId = reservationTime.getId();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation(name, date, time_id) values (?,?,?)",
                    new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setLong(3, timeId);
            return ps;
        }, keyHolder);

        return reservation.toEntity(keyHolder.getKey().longValue());
    }

    @Override
    public void deleteById(Long id) {
        int deleteCount = jdbcTemplate.update("delete from reservation where id = ?", id);

        if (deleteCount == 0) {
            throw new EmptyResultDataAccessException("해당 ID가 존재하지 않습니다.", 1);
        }
    }

    private RowMapper<Reservation> reservationRowMapper() {
        return (rs, rn) -> {
            Long reservationId = rs.getLong("reservation_id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String startAt = rs.getString("start_at");
            Long timeId = rs.getLong("time_id");
            LocalDate reservationDate = LocalDate.parse(date);
            LocalTime reservationTime = LocalTime.parse(startAt);
            ReservationTime time = ReservationTime.of(timeId, reservationTime);
            return Reservation.of(reservationId, name, reservationDate, time);
        };
    }
}
