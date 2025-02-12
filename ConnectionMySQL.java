package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
	public static String status = "Não conectou...";

	public ConnectionMySQL() {
	}

	public static java.sql.Connection getConexaoMySQL() {
    Connection connection = null;
    String driverName = "com.mysql.cj.jdbc.Driver";

  	try {
  		Class.forName(driverName);
  	} catch (ClassNotFoundException e) {
  		e.printStackTrace();
  	}

  	String serverName = "localhost:3306"; // Endereço do servidor do BD
  	String mydatabase = "sgdb"; // Nome do seu banco de dados
  	String url = "jdbc:mysql://" + serverName + "/" + mydatabase; // String de Conexão.
  	String username = "root"; // Nome de um usuário de seu BD
  	String password = ""; // A senha de acesso do usuário quando tiver em uso.

  	try {
  		connection = DriverManager.getConnection(url, username, password);
  		//connection = DriverManager.getConnection("jdbc:mysql://localhost/mapeamento", "root", "");
  		status = " Connected";
  		System.out.printf("DB %s %s in url: %s", mydatabase, status, url);
  	} catch (SQLException e) {
  		System.out.println(status);
  		e.printStackTrace();
  	}

    return connection;
	}
}