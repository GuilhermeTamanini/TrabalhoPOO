package crud;

import java.sql.SQLException;

public interface ProductInterface {
	void createProduct(Product pd) throws SQLException;

	void readProduct(String filterName) throws SQLException;

	void upadateProcuct(int code, String name, double price, String desc) throws SQLException;

	void deleteProduct(int code) throws SQLException;
}