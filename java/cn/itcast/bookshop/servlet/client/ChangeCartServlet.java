package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.exception.FindProductByIdException;
import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class ChangeCartServlet
 */
@WebServlet("/changeCart")
public class ChangeCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取所要改变的购物车中商品的id
		String id=request.getParameter("id");
		//2获取修改后的商品的数量
		int count=Integer.parseInt(request.getParameter("count"));
		//3从session取出购物车
		Map<Product, Integer> cart = (Map<Product, Integer>)request.getSession().getAttribute("cart");
		ProductService service = new ProductService();
		try {
			Product p=service.findProductById(id);
			if (count!=0) {
				cart.put(p,count);
			}else {
				cart.remove(p);
			}
			
			response.sendRedirect(request.getContextPath()+"/client/cart.jsp");
		} catch (FindProductByIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
