package cn.itcast.bookshop.service;

import java.sql.SQLException;

import cn.itcast.bookshop.dao.UserDao;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.exception.ActiveUserException;
import cn.itcast.bookshop.exception.LoginException;
import cn.itcast.bookshop.exception.RegisterException;
import cn.itcast.bookshop.utils.MailUtils;


public class UserService {
	UserDao dao=new UserDao();
	//1方法：登录判断
	public User login(String username,String password)throws LoginException {
		User user=null;
		try {
			user=dao.findUserByUsernameAndPassword(username, password);
			if (user!=null) {
				if (user.getState()==1) {
					return user;
				}
				throw new LoginException("用户未激活");
			}	
			throw new LoginException("用户名或者密码错误");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new LoginException("登录失败");
		}
	}
	
	//2业务方法：注册功能
	public void register(User user) throws RegisterException {
		try {
			dao.addUser(user);
			String emailMsg="感谢您注册网上书城，单击"+"<a href='http://localhost:8080/bookshop/activeUser?"
					+ "activeCode="+user.getActiveCode()+"'>&nbsp;激活&nbsp;"
							+ "</a>后使用"+"<br />为保障您的账户安全，请在24小时内完成激活操作";
			MailUtils.sendMail(user.getEmail(), emailMsg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RegisterException("注册失败");
		}
	}
	
	//3业务方法：用户激活
	public void activeUser(String activeCode) throws ActiveUserException {
		//1根据激活码获取用户
		User user=null;
		try {
			user=dao.findUserByActiveCode(activeCode);
			if (user==null) {
				throw new ActiveUserException("激活失败");
			}
			long time=System.currentTimeMillis()-user.getRegistTime().getTime();
			//转换为小时并判断
			//24个小时
			if (time/1000/60/60>24) {
				throw new ActiveUserException("激活码过期");
			}
			//修改用户状态为激活状态state=1
			dao.activeUser(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActiveUserException("激活失败");
		}
	}
}
