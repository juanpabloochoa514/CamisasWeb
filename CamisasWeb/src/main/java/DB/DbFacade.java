package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbFacade {

	private final ConcreteDBMSHandler cb;

	public DbFacade(ConcreteDBMSHandler cb) {
		if (cb == null) {
			throw new IllegalArgumentException("Need a not null connection builder's instance");
		}
		this.cb = cb;
	}

	public int executeInsertionQuery(String query) throws SQLException {
		Connection con = null;
		Statement st = null;
		int id = 0;
		try {
			con = this.cb.getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.execute(query, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = st.getGeneratedKeys();
			rs.first();
			id = rs.getInt(1);
		} catch (SQLException e) {
			System.err.println(e);
			throw e;
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return id;
	}
	
	public int executeDeleteOrUpdateQuery(String query) throws SQLException {
		Connection con = null;
		Statement st = null;
		int affectedRows = 0;
		try {
			con = this.cb.getConnection();
			st = con.createStatement();
			affectedRows = st.executeUpdate(query);
		} catch (SQLException e) {
			System.err.println(e);
			throw e;
		} finally {
			try {
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return affectedRows;
	}

	public List<HashMap<String, Object>> executeQueryReturningSet(String query) throws SQLException {
		Connection con = null;
		Statement st = null;
		ArrayList<HashMap<String, Object>> ret = new ArrayList<HashMap<String, Object>>();
		try {
			con = this.cb.getConnection();
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData(); // pido información del resultado
			int cols = rsmd.getColumnCount(); // obtengo cantidad de columnas
			
			while (rs.next()) {
				HashMap<String, Object> record = new HashMap<String, Object>(); // creo un registro (fila)
				for (int i = 1; i <= cols; i++) {
					Object value = rs.getObject(i);
					record.put(rsmd.getColumnName(i), value); // voy cargando de valores sus atributos (columnas)
				}
				ret.add(record); // lo agrego al listado
			}
		} catch (SQLException e) {
			System.err.println(e);
			throw e;
		} finally {
			try {
				if (st != null)
					st.close();
				if (con != null)
					con.close();
			} catch (SQLException e) {
				System.err.println(e);
			}
		}
		return ret;
	}

}
