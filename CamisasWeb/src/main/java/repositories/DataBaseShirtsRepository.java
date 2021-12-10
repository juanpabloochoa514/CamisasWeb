package repositories;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import DB.ConcreteDBMSHandler;
import DB.DbFacade;
import ModelShirt.Shirt;

public class DataBaseShirtsRepository implements ShirtsRepository  {

	private DbFacade dbFacade;
	
	public DataBaseShirtsRepository() throws SQLException {
		ConcreteDBMSHandler dbmsHandler = new ConcreteDBMSHandler();
		dbmsHandler.startup();
		this.dbFacade = new DbFacade(dbmsHandler);
	}
	
	@Override
	public void addNewShirt(Shirt shirts) {
		int id = 0;
		try {
			String insertionQuery = "INSERT INTO shirts(price, name) VALUES ("+ shirts.getPrice()+",'"+shirts.getName()+"');";                     
			id = dbFacade.executeInsertionQuery(insertionQuery);
			shirts.setId(id);
		} catch (SQLException e) {
			System.err.println("Error al insertar: " + e.getMessage());
		}
	}

	@Override
	public void modifyExisting(Shirt shirts) {
		try {
			String updateQuery = "UPDATE shirts SET price = "+ shirts.getPrice()+", name='"+shirts.getName()+"' WHERE id = "+ shirts.getId() +";";                     
			int value = dbFacade.executeDeleteOrUpdateQuery(updateQuery);
			System.out.println("Resultado: " + value);
		} catch (SQLException e) {
			System.err.println("Error al actualizar: " + e.getMessage());
		}
	}

	@Override
	public void deleteByIndex(int index) {
		throw new RuntimeException("Metodo deleteByIndex no implementado para DataBase");
	}

	@Override
	public void deleteById(int id) {
		try {
			String deletionQuery = "delete from bike WHERE id = " + id;
			int value = dbFacade.executeDeleteOrUpdateQuery(deletionQuery);
			System.out.println("Resultado: " + value);
		} catch (SQLException e) {
			System.err.println("Error al borrar: " + e.getMessage());
		}
	}

	@Override
	public Shirt[] getAll() {
		try {
			List<HashMap<String,Object>> rows = this.dbFacade.executeQueryReturningSet("SELECT id, name, price FROM shirts");
			int rowCount = rows.size();
		    Shirt[] shirt = new Shirt[rowCount];
			for (int i = 0; i < rows.size(); i++) {
				Shirt bike = toShirt(rows.get(i));
				shirt[i] = bike;
			}
			return shirt;
		} catch(Exception e) {
			System.err.println("Error al recuperar: " + e.getMessage());
			return new Shirt[0];
		}
	}


	
	
	public Shirt toShirt(HashMap<String, Object> valueByColumnName) {
		int id = (Integer) valueByColumnName.get("id");
		String name = (String) valueByColumnName.get("name");
		String priceStr = (String) valueByColumnName.get("price");
		int price = Integer.parseInt(priceStr);
		
		Shirt shirt = new Shirt(name,price,id);
		return shirt;
	}
}
