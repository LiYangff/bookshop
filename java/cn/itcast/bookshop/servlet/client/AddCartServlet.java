package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.exception.FindProductByIdException;
import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class AddCartServlet
 */
@WebServlet("/addCart")
public class AddCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取商品id
		String id =request.getParameter("id");
		//2.根据id查找商品
		ProductService service = new ProductService();
		try {
			Product p = service.findProductById(id);
			//3.将商品p添加到购物车
			//3.1获取session对象
			HttpSession session = request.getSession();
			//3.2从session中获取购物车列表,判断是否是登录状态
			Map<Product, Integer> cart=(Map<Product, Integer>) session.getAttribute("cart");
			User user = (User) session.getAttribute("user");
			if (user != null) {
				//3.3若用户不为空的，则对购物车进行判断，如果购物车为空，则需要创建购物车
				if(cart==null) {
					cart=new HashMap<Product,Integer>();
				}
				//3.4对购物车进行商品添加
				Integer count = cart.put(p,1);
				if(count!=null) {
					cart.put(p, count+1);
				}
				session.setAttribute("cart", cart);
			}
			response.sendRedirect(request.getContextPath()+"/client/cart.jsp");
			return;
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
