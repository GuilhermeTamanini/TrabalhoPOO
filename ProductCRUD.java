package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductCRUD implements ProductInterface{
	Connection conn = null;

	public void connect() {
		this.conn = ConnectionMySQL.getConexaoMySQL();
	}

	@Override
	public void createProduct(Product pd) throws SQLException {
		String query = "INSERT INTO PRODUCT (code, name, price, description) VALUES (?, ?, ?, ?)";
		int nextSequence = productSequence();

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setInt(1, nextSequence);
			pst.setString(2, pd.name());
			pst.setDouble(3, pd.price());
			pst.setString(4, pd.desc());
			pst.executeUpdate();
			System.out.println("\nProduto criado!");
		} catch (SQLException e) {
			System.out.printf("Product not created: %s \n", e.getMessage());
			throw e;
		}
    }

	@Override
	public void readProduct(String filterName) throws SQLException {
		String query = "SELECT PD.CODE, PD.NAME, PD.PRICE, PD.DESCRIPTION FROM PRODUCT PD";
		if (filterName != null && !filterName.isEmpty()) {
			query += " WHERE PD.NAME LIKE ?";
		}
		try (PreparedStatement pst = conn.prepareStatement(query)) {
			if (filterName != null && !filterName.isEmpty()) {
				pst.setString(1, "%" + filterName + "%");
			}
			try (ResultSet rst = pst.executeQuery()) {
				while (rst.next()) {
					int code = rst.getInt("code");
					String name = rst.getString("name");
					String description = rst.getString("description");
					Double price = rst.getDouble("price");
					System.out.printf("\nCódigo: %d\nNome: %s\nPreço: %f\nDescrição: %s\n", code, name, price, description);
				}
			}
		} catch (SQLException e) {
			System.out.printf("Error while searching for products: %s\n", e.getMessage());
			throw e;
		}
	}


	@Override
	public void updateProduct(int code, String name, double price, String desc) throws SQLException {
		String query = "UPDATE PRODUCT SET NAME = ?, PRICE = ?, DESCRIPTION = ? WHERE CODE = ?";

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setString(1, name);
			pst.setDouble(2, price);
			pst.setString(3, desc);
			pst.setInt(4, code);
			int ret = pst.executeUpdate();
			if(ret > 0){
				System.out.printf("\n%d rows affected", ret);
				System.out.printf("\nRegistro atualizado: \nNome: %s\nPreço:  %f\nDescrição: %s\n", name, price, desc);
			}else{
				System.out.printf("Product with code %d not found", code);
			}
		}catch (SQLException e) {
			 System.out.printf("Error while updating product: %s\n", e.getMessage());
			 throw e;
		}
	}

	@Override
	public void deleteProduct(int code) throws SQLException {
		String query = "DELETE FROM PRODUCT WHERE CODE = ?";

		try {
			PreparedStatement pst;
			pst = conn.prepareStatement(query);
			pst.setInt(1, code);
			int ret = pst.executeUpdate();
			if(ret > 0){
				System.out.printf("\nProduto com o código %d deletado\n",code);
			}else{
				System.out.printf("\nThe code %d doesn't exists", code);
			}
		} catch (SQLException e) {
			System.out.printf("An error occurred while deleting the product with code %d: %s \n", code, e.getMessage());
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
			System.out.printf("An error occurred while getting the next sequence from table product: %s \n", e.getMessage());
			throw e;
		}
	}

}