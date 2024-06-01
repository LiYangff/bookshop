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
 * Servlet implementation class MenuSearchServlet
 */
@WebServlet("/menuSearch")
public class MenuSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1定义当前页码 默认页码1
		int currentPage=1;
		//获取页码
		String _currentPage=request.getParameter("currentPage");
		if (_currentPage!=null) {
			currentPage=Integer.parseInt(_currentPage);
			
		}
		//2定义每页显示条数，默认4
		int currentCount=4;
		//3获取搜素框输入的条件，默认查询所有
		String searchfield=request.getParameter("textfield");
		if ("请输入书名".equals(searchfield)) {
			request.getRequestDispatcher("showProductByPage").forward(request, response);
			return;
		}
		//4调用service方法查找搜索的图书
		ProductService service=new ProductService();
		PageBean bean=service.findBookByName(currentPage, currentCount, searchfield);
		//5分页搜索的结果相关信息封装到PageBean对象中
		request.setAttribute("bean", bean);
		//6跳转至product_search_list.jsp
		request.getRequestDispatcher("/client/product_search_list.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
