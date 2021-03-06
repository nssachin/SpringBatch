package io.spring.database.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet resultSet, int arg1) throws SQLException {
		
		return new Customer(resultSet.getLong("id"),
				resultSet.getString("firstName"),
				resultSet.getString("lastName"),
				resultSet.getDate("birthDate"));
	}

}
