package demo.ftmk.order;

import demo.ftmk.product.Product;

/**
 * This class represent the product ordered by the customer.
 * 
 * @author fiqa
 *
 */
public class OrderedProduct {
	
	private Product product;
	int quantity;
	
	public Product getProduct() {
		return product;
	}
	public void setOrderedProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	

}
