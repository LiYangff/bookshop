package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.OrderItem;
import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.service.OrderService;
import cn.itcast.bookshop.utils.IdUtils;

/**
 * Servlet implementation class CreateOrderServlet
 */
@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前用户
		User user =(User) request.getSession().getAttribute("user");
		//2获取购物车列表
		Map<Product, Integer> cart=(Map<Product, Integer>) request.getSession().getAttribute("cart");
		//3将订单表单中数据封装到订单对象中
		Order order=new Order();
		try {
			BeanUtils.populate(order, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//4封装其他属性到order对象
		order.setId(IdUtils.getUUID());
		order.setUser(user);
		for(Product p:cart.keySet()) {
			OrderItem item=new OrderItem();
			item.setOrder(order);
			item.setBuynum(cart.get(p));
			item.setP(p);
			order.getOrderItems().add(item);
		}
		//5调用service对象完成订单的创建/添加
		OrderService service=new OrderService();
		service.addOrder(order);
		response.sendRedirect(request.getContextPath()+"/client/createOrderSuccess.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
