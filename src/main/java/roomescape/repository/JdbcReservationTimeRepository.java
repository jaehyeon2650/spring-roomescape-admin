package roomescape.repository;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ReservationTime create(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String time = reservationTime.getStartAt().toString();
        jdbcTemplate.update((connection) -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into reservation_time(start_at) values(?)", new String[]{"id"});
            ps.setString(1, time);
            return ps;
        }, keyHolder);
        return reservationTime.assignId(keyHolder.getKey().longValue());
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject("select * from reservation_time where id = ?", reservationTimeRowMapper(),
                id);
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("select * from reservation_time", reservationTimeRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        int deleteCount = jdbcTemplate.update("delete from reservation_time where id = ?", id);

        if (deleteCount == 0) {
            throw new EmptyResultDataAccessException("해당 ID가 존재하지 않습니다.", 1);
        }
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String startAt = rs.getString("start_at");
            LocalTime time = LocalTime.parse(startAt);
            return ReservationTime.of(id, time);
        };
    }
}
