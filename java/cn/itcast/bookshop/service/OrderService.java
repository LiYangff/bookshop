package cn.itcast.bookshop.service;

import java.sql.SQLException;
import java.util.List;

import cn.itcast.bookshop.dao.OrderDao;
import cn.itcast.bookshop.dao.OrderItemDao;
import cn.itcast.bookshop.dao.ProductDao;
import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.OrderItem;
import cn.itcast.bookshop.domain.User;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class OrderService {
		OrderDao odao=new OrderDao();
		OrderItemDao oidao=new OrderItemDao();
		ProductDao pdao=new ProductDao();
		//1.添加订单
		public void addOrder(Order order) {
			try {
				//开启事务
				DataSourceUtils.startTransaction();
			
				//1向订单orders添加数据
				odao.addOrder(order);
				//2向订单orderItem表添加数据
				oidao.addOrderItem(order);
				//3修改每个订单项中的商品的库存
				pdao.changeProductNum(order);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					//出错则回滚事务
					DataSourceUtils.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
		}finally {
				try {
					//结束事务
					DataSourceUtils.releaseAndCloseConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
		//2.根据用户查询订单
		public List<Order> findOrderByUser(User user){
			List<Order> orders=null;
			try {
				orders=odao.findOrderByUser(user);
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
			return orders;
	}
		//3.根据id查询订单
		public Order findOrderById(String id) {
			Order order=null;
			try {
				order=odao.findOrderById(id);
				List<OrderItem> items=oidao.findOrderItemByOrder(order);
				order.setOrderItems(items);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return order;
		}
		//后台查询所有订单方法
		public List<Order> findAllOrder() {
			try {
				List<Order> list = odao.findAllOrder();
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		//后台有条件的查询订单
		public List<Order> findOrderByManyCondition(String id, String receiverName) {
			try {
				List<Order> list = odao.findOrderByManyCondition(id,receiverName);
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		//后台删除订单和订单条目
		public void delOrderById(String id) {
			try {
				
				DataSourceUtils.startTransaction();
				oidao.delOrderItemByid(id);
				odao.delOrderById(id);
				
			} catch (SQLException e) {
				
				//删除异常，回滚事物
				try {
					DataSourceUtils.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}finally {
				
				try {
					DataSourceUtils.releaseAndCloseConnection();//事务commit
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		//前台删除
		public void delOrderByIdWithClient(String id) {
			Order order=new Order();
			order.setId(id);
			try {
				
				DataSourceUtils.startTransaction();
				pdao.changeProductNum(order);
				oidao.delOrderItemByid(id);
				odao.delOrderById(id);
				
			} catch (SQLException e) {
				// 删除异常，回滚事物
				try {
					DataSourceUtils.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} finally {
				try {
					DataSourceUtils.releaseAndCloseConnection();// 事务commit
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

}
