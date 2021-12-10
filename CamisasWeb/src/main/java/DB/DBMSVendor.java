package DB;

import java.sql.SQLException;

public interface DBMSVendor {
	public String getJDBCProtocolName();
	public void init() throws SQLException;
}

class PostgresDBMS implements DBMSVendor {

	@Override
	public String getJDBCProtocolName() {
		return "jdbc:postgresql";
	}

	@Override
	public void init() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
	}
	
}

class MySqlVendor implements DBMSVendor {

	@Override
	public String getJDBCProtocolName() {
		return "jdbc:mysql";
	}

	@Override
	public void init() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new SQLException(e);
		}
	}
	
}

