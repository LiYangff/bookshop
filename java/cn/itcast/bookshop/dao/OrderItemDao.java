package cn.itcast.bookshop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.OrderItem;
import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class OrderItemDao {
	//1.添加订单项
	public void addOrderItem(Order order) throws SQLException {
		QueryRunner runner=new QueryRunner(DataSourceUtils.getDs());
		String sql="insert into orderItem values(?,?,?)";
		List<OrderItem> items=order.getOrderItems();
		Object[][] params=new Object[items.size()][3];
		for(int i=0;i<params.length;i++) {
			params[i][0]=items.get(i).getOrder().getId();
			params[i][1]=items.get(i).getP().getId();
			params[i][2]=items.get(i).getBuynum();
		}
		runner.batch(sql, params);
	}
	
	//2.根据订单查询订单项
		public List<OrderItem> findOrderItemByOrder(Order order) throws SQLException{
			QueryRunner runner=new QueryRunner(DataSourceUtils.getDs());
			String sql="select * from orderItem,products where products.id=orderItem.product_id and order_id=?";
			return runner.query(sql, new ResultSetHandler<List<OrderItem>>() {
				@Override
				public List<OrderItem> handle(ResultSet rs) throws SQLException {
					List<OrderItem> items=new ArrayList<OrderItem>();
					while(rs.next()) {
						OrderItem orderItem=new OrderItem();
						Product product=new Product();
						orderItem.setOrder(order);
						orderItem.setBuynum(rs.getInt("orderitem.buynum"));
						product.setId(rs.getString("products.id"));
						product.setName(rs.getString("products.name"));
						product.setPrice(rs.getDouble("products.price"));
						product.setCategory(rs.getString("products.category"));
						product.setPnum(rs.getInt("products.pnum"));
						product.setImgurl(rs.getString("products.imgurl"));
						product.setDescription(rs.getString("products.description"));
						
						orderItem.setP(product);
						items.add(orderItem);
					}
					return items;
					
				}	
			},order.getId());
		}

		//后台删除订单条目操作
		public void delOrderItemByid(String id) throws SQLException {
			QueryRunner runner=new QueryRunner(DataSourceUtils.getDs());
			String sql="delete from orderitem where order_id=?";
			runner.update(sql,id);
		}
}
