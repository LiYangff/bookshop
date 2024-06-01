package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.exception.RegisterException;
import cn.itcast.bookshop.service.UserService;
import cn.itcast.bookshop.utils.ActiveCodeUtils;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1定义一个user对象  封装用户信息
		User user=new User();
		//2
		try {
			BeanUtils.populate(user, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//3注册业务逻辑
		UserService service=new UserService();
		//激活码
		user.setActiveCode(ActiveCodeUtils.createActiveCode());
		try {
			service.register(user);
		} catch (RegisterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//4
		response.sendRedirect(request.getContextPath()+"/client/registersuccess.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
