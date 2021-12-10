package repositories;

import ModelShirt.Shirt;

public interface ShirtsRepository {

	void addNewShirt(Shirt shirts);

	void modifyExisting(Shirt bike);

	void deleteByIndex(int index);

	void deleteById(int id);

	Shirt[] getAll();

}