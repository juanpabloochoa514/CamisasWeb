package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConcreteDBMSHandler {

	private final String url;
	
	private final String hostName;
	private final String portNumber;
	
	private final String dbName;
	private final String dbmsUser;
	private final String dbmsPassword;
	
	private final DBMSVendor dbmsVendor;

	public ConcreteDBMSHandler() {
		this.dbmsVendor = new PostgresDBMS();
		this.hostName = "localhost";
		this.portNumber = "5432";
		this.dbName = "DB_Camisas_Web";
		this.url = dbmsVendor.getJDBCProtocolName() + "://" + hostName + ":" + portNumber + "/" + dbName;
		this.dbmsUser = "Camisas_web";
		this.dbmsPassword = "123";
	}
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, dbmsUser, dbmsPassword);
	}

	public void startup() throws SQLException {
		this.dbmsVendor.init();
	}

}
