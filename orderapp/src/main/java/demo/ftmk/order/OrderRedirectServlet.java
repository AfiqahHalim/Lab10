package demo.ftmk.order;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import demo.ftmk.product.Product;


/**
 * Servlet implementation class OrderRedirectServlet
 */
@WebServlet("/demo/orderRedirectServlet")
public class OrderRedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderRedirectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Validate form if customer has specified all information
		//If zero order then display zero order
		if ((request.getParameter("product") == null) || (request.getParameter("quantity") == null)) {
			
			response.sendRedirect("zeroOrder.html");
			return;
		}
		
		//Get session
		HttpSession session = request.getSession();
		
		//To get orderedItem from session and eneter in List array
		List<OrderedProduct> orderedProducts = (List<OrderedProduct>)session.getAttribute("orderedProducts");
		
		//To validate the list
		if ( orderedProducts == null)
			orderedProducts = new ArrayList<OrderedProduct>();
		
		//To get data from the web form
		int productId = Integer.parseInt(request.getParameter("product"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));
		
		//To wrap the data into array
		Product product = new Product();
		product.setProductId(productId);
		
		OrderedProduct orderedProduct = new OrderedProduct();
		orderedProduct.setOrderedProduct(product);
		orderedProduct.setQuantity(quantity);
		
		//Add object into the list
		orderedProducts.add(orderedProduct);
		
		//Add list to session
		session.setAttribute("orderedProducts", orderedProducts);
		
		//Redirect to the same page
		response.sendRedirect("orderedRedirectForm.html");
		
		}

}
