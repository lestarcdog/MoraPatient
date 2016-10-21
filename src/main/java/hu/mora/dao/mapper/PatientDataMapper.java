package hu.mora.dao.mapper;

import hu.mora.model.PatientData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDataMapper implements RowMapper<PatientData> {
    @Override
    public PatientData mapRow(ResultSet rs, int rowNum) throws SQLException {
        PatientData pd = new PatientData();
        pd.setId(rs.getInt("id"));
        pd.setName(rs.getString("name"));
        pd.setMale(rs.getBoolean("isMale"));
        pd.setBirthDate(rs.getDate("birthdate").toLocalDate());
        pd.setPhone(rs.getString("phone"));
        pd.setEmail(rs.getString("email"));
        pd.setCity(rs.getString("city"));
        pd.setStreet(rs.getString("street"));

        return pd;
    }
}
