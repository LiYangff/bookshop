package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.exception.FindProductByIdException;
import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class FindProductByIdServlet
 */
@WebServlet("/findProductById")
public class FindProductByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取点击商品的id
		String id=request.getParameter("id");
		//2获取查询商品的用户类型
		String type=request.getParameter("type");
		//3查询商品
		ProductService service=new ProductService();
		
		try {
			Product p=service.findProductById(id);
			request.setAttribute("p", p);
			if (type==null) {
				request.getRequestDispatcher("/client/info.jsp").forward(request, response);
				return;
			}
				request.getRequestDispatcher("/admin/products/edit.jsp").forward(request, response);
				return;
		} catch (FindProductByIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
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
