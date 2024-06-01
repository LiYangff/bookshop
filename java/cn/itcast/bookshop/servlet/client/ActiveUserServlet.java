package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.exception.ActiveUserException;
import cn.itcast.bookshop.service.UserService;

/**
 * Servlet implementation class ActiveUserServlet
 */
@WebServlet("/activeUser")
public class ActiveUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取用户激活码
		String activeCode=request.getParameter("activeCode");
		//2调用service对象的activeUser方法完成激活功能
		UserService service=new UserService();
		try {
			service.activeUser(activeCode);
		} catch (ActiveUserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.getWriter().println(e.getMessage());
		}
		response.sendRedirect(request.getContextPath()+"/client/activesuccess.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
