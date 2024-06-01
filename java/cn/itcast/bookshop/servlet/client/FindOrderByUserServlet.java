package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.service.OrderService;

/**
 * Servlet implementation class FindOrderByUserServlet
 */
@WebServlet("/findOrderByUser")
public class FindOrderByUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前用户
		User user=(User) request.getSession().getAttribute("user");
		//通过service对象查询当前用户所有订单
		OrderService service=new OrderService();
		List<Order> orders=service.findOrderByUser(user);
		//查询到order存入request域对象中
		request.setAttribute("orders", orders);
		//跳转至orderlist.jsp页面
		request.getRequestDispatcher("/client/orderlist.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
