package cn.itcast.bookshop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class OrderDao {
	//1方法添加订单
	public void addOrder(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql = "insert into orders values(?,?,?,?,?,0,CURRENT_TIMESTAMP,?)";
		runner.update(sql,order.getId(), order.getMoney(),order.getReceiverAddress(),order.getReceiverName(),
				order.getReceiverPhone(),order.getUser().getId());
		
	}
	//2方法根据用户查询订单
	public List<Order> findOrderByUser(User user) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from orders where user_id=?";
		//返回结果集，自己重新定义，需要重写handle()方法
		return runner.query(sql, new ResultSetHandler<List<Order>>() {
			@Override
			public List<Order> handle(ResultSet rs) throws SQLException {
				List<Order> orders=new ArrayList<Order>();
				while (rs.next()) {
					Order order=new Order();
					order.setId(rs.getString("id"));
					order.setMoney(rs.getDouble("money"));
					order.setOrdertime(rs.getDate("ordertime"));
					order.setPaystate(rs.getInt("paystate"));
					order.setReceiverAddress(rs.getString("receiverAddress"));
					order.setReceiverName(rs.getString("receiverName"));
					order.setReceiverPhone(rs.getString("receiverPhone"));
					order.setUser(user);
					orders.add(order);
				}
				return orders;
			}
		},user.getId());
	}
	
	//3根据id查找订单
	public Order findOrderById(String id) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from orders,user where orders.user_id=user_id and orders.id=?";
		return runner.query(sql,new ResultSetHandler<Order>() {
			@Override
			public Order handle(ResultSet rs) throws SQLException {
				//重新定义返回的结果
				Order order=new Order();
				while(rs.next()) {
					order.setId(rs.getString("orders.id"));
					order.setMoney(rs.getDouble("orders.money"));
					order.setOrdertime(rs.getDate("orders.ordertime"));
					order.setPaystate(rs.getInt("orders.paystate"));
					order.setReceiverAddress(rs.getString("orders.receiverAddress"));
					order.setReceiverName(rs.getString("orders.receiverName"));
					order.setReceiverPhone(rs.getString("orders.receiverPhone"));
					order.setPaystate(rs.getInt("orders.paystate"));
					
					User user=new User();
					user.setId(rs.getInt("user.id"));
					user.setEmail(rs.getString("user.email"));
					user.setGender(rs.getString("user.gender"));
					user.setActiveCode(rs.getString("user.activecode"));
					user.setIntroduce(rs.getString("user.introduce"));
					user.setPassword(rs.getString("user.password"));
					user.setRegistTime(rs.getDate("user.registtime"));
					user.setRole(rs.getString("user.role"));
					user.setState(rs.getInt("user.state"));
					user.setTelephone(rs.getString("user.telephone"));
					user.setUsername(rs.getString("user.username"));
					order.setUser(user);
				}
				return order;
			}
		},id);
	}
	//查询所有订单操作
		public List<Order> findAllOrder() throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
			//1准备sql
			String sql="select orders.*,user.* from orders,user "+
					   " where orders.user_id=user_id order by orders.user_id";
			List<Order> list = runner.query(sql, new ResultSetHandler<List<Order>>() {
				@Override
				public List<Order> handle(ResultSet rs) throws SQLException {
					//遍历rs结果集，封装了订单和用户数据
					List<Order> orders=new ArrayList<Order>();
					while (rs.next()) {
						//创建订单对象
						Order order=new Order();
						order.setId(rs.getString("orders.id"));
						order.setMoney(rs.getDouble("orders.money"));
						order.setOrdertime(rs.getDate("orders.ordertime"));
						order.setPaystate(rs.getInt("orders.paystate"));
						order.setReceiverAddress(rs.getString("orders.receiverAddress"));
						order.setReceiverName(rs.getString("orders.receiverName"));
						order.setReceiverPhone(rs.getString("orders.receiverPhone"));
						order.setPaystate(rs.getInt("orders.paystate"));
						orders.add(order);
						User user=new User();
						user.setId(rs.getInt("user.id"));
						user.setEmail(rs.getString("user.email"));
						user.setGender(rs.getString("user.gender"));
						user.setActiveCode(rs.getString("user.activecode"));
						user.setIntroduce(rs.getString("user.introduce"));
						user.setPassword(rs.getString("user.password"));
						user.setRegistTime(rs.getDate("user.registtime"));
						user.setRole(rs.getString("user.role"));
						user.setState(rs.getInt("user.state"));
						user.setTelephone(rs.getString("user.telephone"));
						user.setUsername(rs.getString("user.username"));
						order.setUser(user);
					}
					return orders;
				}
			});
			return list;
		}
		
		//后台：查询有条件的订单查询
		/*
		 * 封装订单数据，由于在查询订单时，设计到orders,user两张表，
		 * 不能使用BeanListHandler结果集处理器来封装数据，
		 * 所以需要使用匿名内部类的方式，手动封装数据到order对象
		 */
		public List<Order> findOrderByManyCondition(String id, String receiverName) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
			String sql="select orders.*,user.* from orders,user "+
					   " where orders.user_id=user_id";
			List<Object> objs=new ArrayList<Object>();
			if (id!=null && id.trim().length()>0) {
				sql+=" and orders.id=?";
				objs.add(id);
			}
			if (receiverName!=null && receiverName.trim().length()>0) {
				sql+=" and orders.receiverName=?";
				objs.add(receiverName);
			}
			sql+=" order by orders.user_id";
			List<Order> list = runner.query(sql, new ResultSetHandler<List<Order>>() {
				@Override
				public List<Order> handle(ResultSet rs) throws SQLException {
					//遍历rs结果集，封装了订单和用户数据
					List<Order> orders=new ArrayList<Order>();
					while (rs.next()) {
						//创建订单对象
						Order order=new Order();
						order.setId(rs.getString("orders.id"));
						order.setMoney(rs.getDouble("orders.money"));
						order.setOrdertime(rs.getDate("orders.ordertime"));
						order.setPaystate(rs.getInt("orders.paystate"));
						order.setReceiverAddress(rs.getString("orders.receiverAddress"));
						order.setReceiverName(rs.getString("orders.receiverName"));
						order.setReceiverPhone(rs.getString("orders.receiverPhone"));
						orders.add(order);
						User user=new User();
						user.setId(rs.getInt("user.id"));
						user.setEmail(rs.getString("user.email"));
						user.setGender(rs.getString("user.gender"));
						user.setActiveCode(rs.getString("user.activecode"));
						user.setIntroduce(rs.getString("user.introduce"));
						user.setPassword(rs.getString("user.password"));
						user.setRegistTime(rs.getDate("user.registtime"));
						user.setRole(rs.getString("user.role"));
						user.setState(rs.getInt("user.state"));
						user.setTelephone(rs.getString("user.telephone"));
						user.setUsername(rs.getString("user.username"));
						order.setUser(user);
					}
					return orders;
				}
			},objs.toArray());
			return list;
		}
		//删除订单
		public void delOrderById(String id) throws SQLException {
			QueryRunner runner=new QueryRunner(DataSourceUtils.getDs());
			String sql="delete from orders where id=?";
			runner.update(sql,id);
		}
}
