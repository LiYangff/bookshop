package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.exception.LoginException;
import cn.itcast.bookshop.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取用户名密码
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		
		//2创建UserService对象，完成登录判断
		UserService service=new UserService();
		User user=null;
		try {
			user=service.login(username,password);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("login_message", e.getMessage());
			request.getRequestDispatcher("/client/login.jsp").forward(request, response);
		}
		String role=user.getRole();
		if (role.equals("超级用户")) {
			response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
			return;
		}else{
			request.getSession().setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/client/myAccount.jsp");	
			return;
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
