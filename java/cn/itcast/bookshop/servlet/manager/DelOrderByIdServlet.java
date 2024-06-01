package cn.itcast.bookshop.servlet.manager;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.service.OrderService;

/**
 * Servlet implementation class DelOrderByIdServlet
 */
@WebServlet("/delOrderById")
public class DelOrderByIdServlet extends HttpServlet {
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
				//获取id和type
				String id=request.getParameter("id");
				String type=request.getParameter("type");
				OrderService service=new OrderService();
				//判断前台删除还是后台删除
				if (type!=null&&type.trim().length()>0) {
					//后台删除
					if (type.equals("admin")) {
						service.delOrderById(id);
						request.getRequestDispatcher("/findOrders").forward(request, response);
						return;
					}
				}
				//前台删除
				service.delOrderByIdWithClient(id);
				response.sendRedirect(request.getContextPath()+"/client/delOrderSuccess.jsp");
				return;
			}
	}


