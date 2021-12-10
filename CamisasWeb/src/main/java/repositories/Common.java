package repositories;

import java.sql.SQLException;

public class Common {

	public static ShirtsRepository shirtsRepository; 

	static {
		 try {
			System.out.println("Iniciando base de datos");
			shirtsRepository = new DataBaseShirtsRepository();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
}


