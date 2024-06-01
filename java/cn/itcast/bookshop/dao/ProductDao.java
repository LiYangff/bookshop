package cn.itcast.bookshop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.itcast.bookshop.domain.Order;
import cn.itcast.bookshop.domain.OrderItem;
import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class ProductDao {
	//1查找某类商品的总数量
	public int findAllCount(String category) throws SQLException {
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		String sql="select count(*) from products";
		Long count;
		if (!category.equals("全部商品")) {
			sql+=" where category=?";
			count =(Long) runner.query(sql,new ScalarHandler(),category);
		}else {
			count=(Long) runner.query(sql,new ScalarHandler());
		}
		
		return count.intValue();
	}
	//2分页查找商品
	public List<Product> finProductByPage(int currentPage,int currentCount,String category) throws SQLException{
		String sql=null;
		Object[] params=null;
		if (!category.equals("全部商品")) {
			sql="select * from products where category=? limit ?,?";//limit n,m:代表查询第n个数据开始的m个商品，n的初值为0 n:(currentPage-1)*currentCount
			params=new Object[] {category,(currentPage-1)*currentCount,currentCount};
		}else {
			sql="select * from products limit ?,?";
			params=new Object[] {(currentPage-1)*currentCount,currentCount};
		}
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		return runner.query(sql, new BeanListHandler<Product>(Product.class),params);
	}
	
	//3查询某个关键字搜索的商品的总数量
	public int findBookByNameAllCount(String searchfiled) throws SQLException {
		String sql="select count(*) from products where name like '%"+searchfiled+"%'";
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		Long count= (Long) runner.query(sql, new ScalarHandler());
		return count.intValue();
	}
	//4分页查询某个关键字搜索的商品
	public List<Product> findBookByName(int currentPage,int currentCount,String searchfield) throws SQLException{
		String sql="select * from products where name like '%"+searchfield+"%'limit ?,?";
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		return runner.query(sql, new BeanListHandler<Product>(Product.class),(currentPage-1)*currentCount,currentCount);
	}
	//5 根据id查询某一本书/商品
	public Product findProductById(String id) throws SQLException {
		String sql="select * from products where id=?";
		QueryRunner runner =new QueryRunner(DataSourceUtils.getDs());
		return runner.query(sql, new BeanHandler<Product>(Product.class),id);
	}
	
	//6 将商品数量减少
	public void changeProductNum(Order order) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql = "update products set pnum=pnum-? where id=?";
		List<OrderItem> items = order.getOrderItems();
		Object[][] params = new Object[items.size()][2];
		for (int i = 0; i < params.length; i++) {
			params[i][0] = items.get(i).getBuynum();
			params[i][1] = items.get(i).getP().getId();
		}
		runner.batch(sql, params);
	}
	//7获取本周热卖的商品
	public List<Object[]> getWeekHotProduct() throws SQLException{
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql = "select products.id,products.name,products.imgurl,SUM(orderitem.buynum) totalsalnum "
				+" from orderitem,orders,products "
				+" where orderitem.order_id=orders.id and products.id=orderitem.product_id"
				+" and orders.paystate=1 and orders.ordertime>DATE_SUB(NOW(),INTERVAL 7 DAY)"
				+" group by products.id,products.name,products.imgurl"
				+" order by totalsalnum desc limit 0,2";
		return runner.query(sql, new ArrayListHandler());
	}
	
	/*
	 * 后台查询所有的方法
	 */
	public List<Product> listAll() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from products";
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	//多条件查询
	public List<Product> findProductByManyCondition(String id, String name, String category, String minprice,
			String maxprice) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		//1、准备sql,
		List<Object> list=new ArrayList<Object>();
		String sql="select * from products where 1=1  ";
		//2 拼接sql，给sql赋值
		if(id!=null && id.trim().length()>0){
			sql+=" and id=? ";
			list.add(id);
		}
		if(name!=null && name.trim().length()>0){
			sql+=" and name=? ";
			list.add(name);
		}
		if(category!=null && category.trim().length()>0){
			sql+=" and category=? ";
			list.add(category);
		}
		if(minprice!=null && maxprice!=null && minprice.trim().length()>0 && maxprice.trim().length()>0){
			sql+=" and price between ? and ? ";
			list.add(minprice);
			list.add(maxprice);
		}
		Object[] params=list.toArray();
		List<Product>  listProduct = runner.query(sql, new BeanListHandler<Product>(Product.class),params);
		return listProduct;
	}
	//后台添加商品方法
	public void addProduct(Product p) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql = "insert into products values(?,?,?,?,?,?,?)";
		runner.update(sql, p.getId(), p.getName(), p.getPrice(), p.getCategory(), p.getPnum(), p.getImgurl(),
				p.getDescription());
	}
	//后台创建编辑商品方法
		public void editProduct(Product p) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
			//编写sql
			String sql = "update products set name=?,price=?,category=?,pnum=?,description=?";
			//给占位符赋值
			List<Object> obj=new ArrayList<Object>();
			obj.add(p.getName());
			obj.add(p.getPrice());
			obj.add(p.getCategory());
			obj.add(p.getPnum());
			obj.add(p.getDescription());
			//编辑商品路径
			if (p.getImgurl()!=null && p.getImgurl().trim().length()>0) {
				sql+=" ,imgurl=?";
				obj.add(p.getImgurl());
			}
			
			sql+=" where id=?";
			obj.add(p.getId());
			System.out.println(sql);
			runner.update(sql,obj.toArray());
			
		}
		//删除商品方法
		public void deleteProduct(String id) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
			String sql="delete from products where id=?";
			runner.update(sql,id);
		}
		
		//后台查询销售榜单数据，查询已支付数据
		public List<Object[]> download(String year, String month) throws SQLException {
			QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
			String sql = "SELECT products.name,SUM(orderitem.buynum) totalsalnum "
					+ "FROM orders,products,orderItem "                           
					+ "WHERE orders.id=orderItem.order_id "                       
					+ "AND products.id=orderItem.product_id "                     
					+ "AND orders.paystate=1 "                                    
					+ "and year(ordertime)=? "                                    
					+ "and month(ordertime)=? "                                   
					+ "GROUP BY products.name "                                   
					+ "ORDER BY totalsalnum DESC";  
			List<Object[]> list=runner.query(sql, new ArrayListHandler(),year,month);
			return list;
		}
}
