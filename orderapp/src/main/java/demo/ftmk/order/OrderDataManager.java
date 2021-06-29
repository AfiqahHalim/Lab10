package demo.ftmk.order;

import java.time.LocalDate;
import java.util.List;

import demo.ftmk.product.Product;

/**
 * This class implements the business logic to process the order placed 
 * by the customer.
 * 
 * @author fiqa
 *
 */
public class OrderDataManager {
	
	private double serviceTaxRate = 0.06;
	
	
	/**
	 * This method computer the total amount of the order
	 * @param subTotal
	 * @param serviceTax
	 * @return
	 */
	private double getTotalAmount (double subTotal, double serviceTax) {
		
		return subTotal + serviceTax;
		
	}
	
	
	/**
	 * This method compute service tax of a total amount of order
	 * @param totalAmount
	 * @return
	 */
	private double getServiceTax (double subTotal) {
		
		return subTotal * serviceTaxRate;
	}
	
	/**
	 * This method computes the total amount of placed order
	 * 
	 * @param orderedProducts
	 * @return
	 */
	protected Order processOrder (List<OrderedProduct> orderedProducts) {
		
		
		// Compute total quantity and amount
		int totalQuantity = 0;
		double subTotal = 0;
		double total = 0;
		for (OrderedProduct orderedProduct:orderedProducts) {
			
			// Count total quantity
			totalQuantity += orderedProduct.getQuantity();
			
			// Get product
			Product product = this.getProduct(orderedProduct.getProduct().getProductId());
			orderedProduct.setOrderedProduct(product);
			
			// Get total amount
			total = orderedProduct.getQuantity() * product.getPrice();
			subTotal += total;
			
		}
		
		// Compute total amount
		double serviceTax = this.getServiceTax(subTotal);
		double totalAmount = this.getTotalAmount(subTotal, serviceTax);
		
		// Wrap the into order
		Order order = new Order();
		order.setOrderedProducts(orderedProducts);
		order.setServiceTax(serviceTax);
		order.setTotalQuantity(totalQuantity);
		order.setTotalAmount(totalAmount);
		order.setOrderDate(LocalDate.now());
		
		return order;
		
	}
	
	/**
	 * This method gets details of a product 
	 * @param productId
	 * @return
	 */
	private Product getProduct (int productId) {
		
		double price = 0.00;
		String productName = "";
		switch (productId) {
		
		case 101:
			productName = "McChicken Value Meal";
			price = 13.20;
			break;
			
		case 102:
			productName = "Smoky Grilled Beef Meal";
			price = 17.90;
			break;
		
		case 103:  
			productName = "Ayam Goreng McD Spicy Meal 2pcs";
			price = 16.95;
			break;
		
		case 104:   
			productName = "Spicy McChicken Deluxe Meal";
			price = 16.65;
			break;
		
		case 105:    
			productName = "Chicken McNuggets 6pcs Meal";
			price = 13.20;
			break;	
			
			
		}
		
		// Wrap into Product
		Product product = new Product();
		product.setProductId(productId);
		product.setPrice(price);
		product.setName(productName);
		
		return product;
		
	} 
}
