package crud;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {
		ProductCRUD prodCRUD = new ProductCRUD();
		prodCRUD.connectionDB();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nEscolha uma ação: ");
			System.out.println("1 - Criar Produto");
			System.out.println("2 - Ler Produtos");
			System.out.println("3 - Atualizar Produto");
			System.out.println("4 - Deletar Produto");
			System.out.println("0 - Sair");

			int action = scanner.nextInt();
			scanner.nextLine(); // Consumir a nova linha

			switch (action) {
				case 1:
					System.out.println("Criando Produto...");
					System.out.print("Nome: ");
					String name = scanner.nextLine();
					System.out.print("Preço: ");
					Double price = scanner.nextDouble();
					scanner.nextLine(); // Consumir a nova linha
					System.out.print("Descrição: ");
					String desc = scanner.nextLine();
					prodCRUD.createProduct(new Product(name, price, desc));
					break;
				case 2:
					System.out.println("Lendo Produtos...");
					System.out.print("Digite um nome para filtrar (ou pressione Enter para listar todos): ");
					String filterName = scanner.nextLine();
					prodCRUD.readProduct(filterName);
					break;
				case 3:
					System.out.println("Atualizando Produto...");
					System.out.print("Código: ");
					int codeToUpdate = scanner.nextInt();
					scanner.nextLine(); // Consumir a nova linha
					System.out.print("Novo Nome: ");
					String newName = scanner.nextLine();
					System.out.print("Novo Preço: ");
					double newPrice = scanner.nextDouble();
					scanner.nextLine(); // Consumir a nova linha
					System.out.print("Nova Descrição: ");
					String newDesc = scanner.nextLine();
					prodCRUD.upadateProcuct(codeToUpdate, newName, newPrice, newDesc);
					break;
				case 4:
					System.out.println("Deletando Produto...");
					System.out.print("Código: ");
					int codeToDelete = scanner.nextInt();
					prodCRUD.deleteProduct(codeToDelete);
					break;
				case 0:
					System.out.println("Saindo...");
					scanner.close();
					return;
				default:
					System.out.println("Ação inválida. Tente novamente.");
			}
		}
	}
}
