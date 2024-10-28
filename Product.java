package crud;

public class Product {
	private final String name;
	private final Double price;
	private final String desc;

	public Product(String name, Double price, String desc) {
		this.name  = name;
		this.price = price;
		this.desc  = desc;
	}

	public String getName() {
		return name;
	}

	public Double getPrice() {
		return price;
	}

	public String getDesc() {
		return desc;
	}
}