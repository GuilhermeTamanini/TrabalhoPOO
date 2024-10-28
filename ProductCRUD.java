package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCRUD implements ProductInterface{
	Connection conn = null;

	public void connectionDB() {
		ConnectionMySQL.getConexaoMySQL();
	}

	@Override
	public void createProduct(Product pd) throws SQLException {
		String query = "INSERT INTO PRODUCT PD VALUES (?, ?, ?, ?)";
		int nextSequence = productSequence();

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setInt(1, nextSequence);
			pst.setString(2, pd.getName());
			pst.setDouble(3, pd.getPrice());
			pst.setString(4, pd.getDesc());
			pst.executeUpdate();
			System.out.println("Product created");
		} catch (SQLException e) {
			System.out.printf("Product not created: %s %n", e.getMessage());
			throw e;
		}
    }

	@Override
	public void readProduct(String filterName) throws SQLException {
		String query = "SELECT PD.CODE, PD.DESCRIPTION, PD.PRICE FROM PRODUCT PD";
		if (filterName != null && !filterName.isEmpty()) {
			query += " WHERE pd.description LIKE ?";
		}
		try (PreparedStatement pst = conn.prepareStatement(query)) {
			if (filterName != null && !filterName.isEmpty()) {
				pst.setString(1, "%" + filterName + "%");
			}
			try (ResultSet rst = pst.executeQuery()) {
				while (rst.next()) {
					int code = rst.getInt("code");
					String name = rst.getString("description");
					Double price = rst.getDouble("price");
					System.out.printf("Code: %d Name: %s Price: %f \n", code, name, price);
				}
			}
		} catch (SQLException e) {
			System.out.printf("Error while searching for products %s %n", e.getMessage());
			throw e;
		}
	}


	@Override
	public void upadateProcuct(int code, String name, double price, String desc) throws SQLException {
		String query = "UPDATE PRODUCT PD SET PD.NAME = ?, PD.PRICE = ?, PD.DESCRIPTION = ? WHERE PD.CODE = ?";

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setInt(1, code);
			pst.setString(2, name);
			pst.setDouble(3, price);
			pst.setString(4, desc);
			int ret = pst.executeUpdate();
			if(ret > 0){
				System.out.printf("%d rows affected", ret);
				System.out.printf("Register updated: name: %s, price:  %f, description: %s", name, price, desc);
			}else{
				System.out.printf("Product with code %d not found", code);
			}
		}catch (SQLException e) {
			 System.out.printf("Error while updating product %s %n", e.getMessage());
			 throw e;
		}
	}

	@Override
	public void deleteProduct(int code) throws SQLException {
		String query = "DELETE FROM PRODUCT PD WHERE PD.CODE = ?";

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setInt(1, code);
			int ret = pst.executeUpdate();
			if(ret > 0){
				System.out.printf("Product with code %d was deleted ",code);
			}else{
				System.out.printf("The code %d doesn't exists", code);
			}
		} catch (SQLException e) {
			System.out.printf("An error occurred while deleting the product %s %n", e.getMessage());
			throw e;
		}
	}

	public int productSequence() throws SQLException {
		String query = "SELECT MAX(PD.CODE) FROM PRODUCT PD";

		try (PreparedStatement stmt = conn.prepareStatement(query);
			 ResultSet rs = stmt.executeQuery()) {
			int nextSequence = 1;
			if (rs.next()) {
				nextSequence = rs.getInt(1) + 1;
			}
			return nextSequence;
		} catch (SQLException e) {
			System.out.printf("An error occurred while getting the next sequence from table product: %s %n", e.getMessage());
			throw e;
		}
	}

}