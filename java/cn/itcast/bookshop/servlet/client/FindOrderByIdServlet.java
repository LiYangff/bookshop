package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.service.OrderService;

/**
 * Servlet implementation class FindOrderByIdServlet
 */
@WebServlet("/findOrderById")
public class FindOrderByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取id
		String id=request.getParameter("id");
		//2根据id查询订单
		OrderService service=new OrderService();
		Order order=service.findOrderById(id);
		//3
		request.setAttribute("order", order);
		//4
		request.getRequestDispatcher("/client/orderInfo.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
