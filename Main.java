package crud;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		ProductCRUD prodCRUD = new ProductCRUD();
		prodCRUD.connect();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nEscolha uma ação: ");
			System.out.println("1 - Criar produto");
			System.out.println("2 - Buscar produtos");
			System.out.println("3 - Atualizar produto");
			System.out.println("4 - Deletar produto");
			System.out.println("0 - Sair");

			int action;
			try {
				action = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Por favor, insira um número válido.");
				scanner.nextLine();
				continue;
			}

			scanner.nextLine();

			switch (action) {
				case 1:
					System.out.println("Criando produto...\n");
					System.out.print("Nome: ");
					String name = scanner.nextLine();
					System.out.print("Preço: ");
					Double price = null;
					while (price == null) {
						try {
							price = Double.parseDouble(scanner.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Por favor, insira um número válido para o preço (ex: 22.22).");
						}
					}
					System.out.print("Descrição: ");
					String desc = scanner.nextLine();
					prodCRUD.createProduct(new Product(name, price, desc));
					break;
				case 2:
					System.out.println("Lendo produtos...\n");
					System.out.print("Digite um nome para filtrar (ou pressione Enter para listar todos): ");
					String filterName = scanner.nextLine();
					prodCRUD.readProduct(filterName);
					break;
				case 3:
					System.out.println("Atualizando produto...\n");
					System.out.print("Código: ");
					int codeToUpdate = -1;
					while (codeToUpdate == -1) {
						try {
							codeToUpdate = Integer.parseInt(scanner.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Por favor, insira um número válido para o código.");
						}
					}
					System.out.print("Novo nome: ");
					String newName = scanner.nextLine();
					System.out.print("Novo preço: ");
					Double newPrice = null;
					while (newPrice == null) {
						try {
							newPrice = Double.parseDouble(scanner.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Por favor, insira um número válido para o preço (ex: 22.22)");
						}
					}
					System.out.print("Nova descrição: ");
					String newDesc = scanner.nextLine();
					prodCRUD.updateProduct(codeToUpdate, newName, newPrice, newDesc);
					break;
				case 4:
					System.out.println("Deletando produto...\n");
					System.out.print("Código: ");
					int codeToDelete = -1;
					while (codeToDelete == -1) {
						try {
							codeToDelete = Integer.parseInt(scanner.nextLine());
						} catch (NumberFormatException e) {
							System.out.println("Por favor, insira um número válido para o código.");
						}
					}
					prodCRUD.deleteProduct(codeToDelete);
					break;
				case 0:
					System.out.println("Saindo...\n");
					scanner.close();
					return;
				default:
					System.out.println("Ação inválida. Tente novamente.");
			}
		}
	}
}
