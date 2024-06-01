package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.PageBean;
import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class ShowProductByPageServlet
 */
@WebServlet("/showProductByPage")
public class ShowProductByPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取当前页码
		String _currentPage=request.getParameter("currentPage");
		int currentPage=_currentPage==null?1:Integer.parseInt(_currentPage);
		//2每页的数量
		int currentCount=4;
		//3类别
		String _category=request.getParameter("category");
		String category=_category==null?"全部商品":_category;
		//4查询
		ProductService service=new ProductService();
		PageBean bean=service.findProductByPage(currentPage, currentCount, category);
		//当前的页码  当前页显示的数量   目前分页的类别
		//5存储+跳转
		request.setAttribute("bean", bean);
		request.getRequestDispatcher("/client/product_list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
