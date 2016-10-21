package hu.mora.dao.mapper;

import hu.mora.model.views.LoginTherapist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TherapistMapper implements RowMapper<LoginTherapist> {
    @Override
    public LoginTherapist mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new LoginTherapist(rs.getInt("id"), rs.getString("name"));
    }
}
