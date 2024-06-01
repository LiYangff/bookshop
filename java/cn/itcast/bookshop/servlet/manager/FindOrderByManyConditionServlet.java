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
 * Servlet implementation class FindOrderByManyConditionServlet
 */
@WebServlet("/findOrderByManyCondition")
public class FindOrderByManyConditionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//获取查询条件
				String id=request.getParameter("id");
				String receiverName=request.getParameter("receiverName");
				//创建service
				OrderService service=new OrderService();
				List<Order> orders=service.findOrderByManyCondition(id,receiverName);
				//把订单存入域对象里，转发订单页面进行回显
				request.setAttribute("orders", orders);
				request.getRequestDispatcher("/admin/orders/list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
