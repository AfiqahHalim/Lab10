package demo.ftmk.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CheckOutRedirectServlet
 */
@WebServlet("/demo/checkOutRedirectServlet")
public class CheckOutRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckOutRedirectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Get orderedProducts from session
		HttpSession session = request.getSession();
		List<OrderedProduct> orderedProducts = (List<OrderedProduct>)session.getAttribute("orderedProducts");
		
		
		//Redirect servlet to zeroOrder.html if orderedProduct does not exist
		if ( session.getAttribute("orderedProducts") == null) {
			
			response.sendRedirect("zeroOrder.html");
			return;
			
		}
		
		//Redirect servlet to zeroOrder.html if orderedProduct does not exist
		if (( orderedProducts == null) || (orderedProducts.size() == 0))
				response.sendRedirect("zeroOrder.html");
		
		//Calculate total quantity and total order
		//Invoke the appropriate method from OrderDataManager
		OrderDataManager orderManager = new OrderDataManager();
		Order order = orderManager.processOrder(orderedProducts);
		
		// Get writer
		PrintWriter writer =response.getWriter();
		
		// Display details of order
		writer.print("<html><h3>Details of Order</h3>");
		writer.print("Order Id: " + order.getOrderId() + "<br>");
		writer.print("Date: " + order.getOrderDate() + "<br>");
		
		writer.print("<table>");
		writer.print("\t<tr>");
		writer.print("\t<td>No</td>");
		writer.print("\t <td>Name</td>");
		writer.print("\t <td>Price Per Unit (RM)</td>");
		writer.print("\t <td>Quantity</td>");
		writer.print("\t <td>Sub Total (RM)</td>");
		writer.print("\t</tr>");
		
		
		// Display list of ordered products
		int number = 1;
		int totalQuantity = 0;
		double subTotal = 0;
		double totalAmountBeforeTax = 0;
		for(OrderedProduct orderedProduct:order.getOrderedProducts()) {
		
			subTotal = orderedProduct.getQuantity() * orderedProduct.getProduct().getPrice();
			totalQuantity += orderedProduct.getQuantity();
			totalAmountBeforeTax += subTotal;
			
			writer.print("\t<tr>");
			writer.print("\t<td>" + (number++) +"</td>");
			writer.print("\t<td>" + orderedProduct.getProduct().getName()+"</td>");
			writer.print("\t<td>" + orderedProduct.getProduct().getPrice() +"</td>");
			writer.print("\t<td>" + orderedProduct.getQuantity() + "</td>");
			writer.print("\t<td>" + String.format("%.2f", subTotal) + "</td>");
			
			writer.print("\t</tr>");
			
		}
		
		writer.print("</table><br>");
		
		writer.print("Total Quantity of Product: " + totalQuantity + 
		"<br>");
		writer.print("Service Tax: RM " + String.format("%.2f", order.getServiceTax()) + 
		"<br>");
		writer.print("Total Amount (Before Tax): RM " + String.format("%.2f", totalAmountBeforeTax) + 
		"<br>");
		writer.print("<b>Total Amount (After Tax): RM " + String.format("%.2f", order.getTotalAmount()) + 
		"</b><br>");
		writer.print("<a href=\"..\"index.jsp\">Home</a>");
		
		
		// Remove attribute from session
		session.removeAttribute("orderedProducts");
		
		
	}

}
