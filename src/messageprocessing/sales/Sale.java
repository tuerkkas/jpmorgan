package messageprocessing.sales;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Sale implements Serializable {

	private static final long serialVersionUID = 1527467756453372662L;

	private ProductType productType;

	private int quantity;

	public Sale(String field, double value, int quantity) {
		super();

		productType = new ProductType(field, new BigDecimal(value));
		this.quantity = quantity;

	}

	@Override
	public String toString() {
		return "Sale [field=" + productType.getField() + ", value="
				+ productType.getValue() + ", quantity=" + quantity + "]";
	}

	public Sale getSaleByField(List<Sale> saleList, String field) {

		Sale result = null;

		for (Sale sale : saleList) {
			if (sale.productType.getField().equals(field)) {
				result = sale;
			}
		}
		return result;
	}

	public ProductType getProductType() {
		return productType;
	}

	public void setProductType(ProductType productType) {
		this.productType = productType;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
