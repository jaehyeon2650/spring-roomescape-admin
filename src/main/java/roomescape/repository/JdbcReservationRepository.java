package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String name = reservation.getName();
        LocalDateTime reservationTime = reservation.getReservationTime();
        String date = reservationTime.toLocalDate().toString();
        String time = reservationTime.toLocalTime().toString();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation(name, date, time) values (?,?,?)",
                    new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, time);
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public Reservation findById(Long id) {
        return jdbcTemplate.queryForObject("select * from reservation where id = ?", reservationRowMapper(), id);
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
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String date = rs.getString("date");
            String time = rs.getString("time");
            LocalDateTime reservationTime = LocalDateTime.parse(date + "T" + time);
            return Reservation.createReservation(id, name, reservationTime);
        };
    }

}
