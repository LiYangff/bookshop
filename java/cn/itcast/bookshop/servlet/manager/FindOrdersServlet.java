package cn.itcast.bookshop.servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.service.OrderService;

/**
 * Servlet implementation class FindOrdersServlet
 */
@WebServlet("/findOrders")
public class FindOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		//1.创建service对象
		OrderService service=new OrderService();
		List<Order> orders=service.findAllOrder();
		//把订单存入域对象中
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/admin/orders/list.jsp").forward(request, response);
	}

}
