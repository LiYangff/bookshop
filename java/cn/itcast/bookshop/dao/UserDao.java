package cn.itcast.bookshop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class UserDao {
	//1查询账号密码
	public User findUserByUsernameAndPassword(String username,String password) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class),username,password);
	}
	
	//2 添加用户
	public void addUser(User user) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		String sql="insert into user(username,password,gender,email,telephone,introduce,activeCode) value(?,?,?,?,?,?,?)";
		runner.update(sql,user.getUsername(),user.getPassword(),user.getGender(),user.getEmail(),user.getTelephone(),user.getIntroduce(),user.getActiveCode());
	}
	//3根据激活码查找用户
	public User findUserByActiveCode(String activeCode) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from user where activeCode=?";
		return runner.query(sql, new BeanHandler<User>(User.class),activeCode);
	}
	
	//4用户激活，修改用户状态为激活状态state=1
	public void activeUser(String activeCode) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		String sql="update user set state=? where activeCode=?";
		runner.update(sql,1,activeCode);
	}
}
