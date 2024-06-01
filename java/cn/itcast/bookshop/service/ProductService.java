package cn.itcast.bookshop.service;

import java.sql.SQLException;
import java.util.List;

import javax.management.RuntimeErrorException;

import cn.itcast.bookshop.dao.ProductDao;
import cn.itcast.bookshop.domain.PageBean;
import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.exception.AddProductException;
import cn.itcast.bookshop.exception.FindProductByIdException;

public class ProductService {
	ProductDao dao=new ProductDao();
	//1业务方法：分页查询商品
	public PageBean findProductByPage(int currentPage,int currentCount,String category) {
		PageBean bean=new PageBean();
		bean.setCurrentPage(currentPage);
		bean.setCurrentCount(currentCount);
		bean.setCategory(category);
		
		int totalCount;
		try {
			totalCount=dao.findAllCount(category);
			
			bean.setTotalCount(totalCount);
			int totalPage=(int) Math.ceil(totalCount*1.0/currentCount);
			bean.setTotalPage(totalPage);
			List<Product> ps= dao.finProductByPage(currentPage, currentCount, category);
			bean.setPs(ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	
	//2业务方法：查询图书根据某个条件
	public PageBean findBookByName(int currentPage,int currentCount,String searchfield) {
		PageBean bean=new PageBean();
		//1页码
		bean.setCurrentPage(currentPage);
		//2每页数量
		bean.setCurrentCount(currentCount);
		//3封装模糊查询条件
		bean.setSearchfield(searchfield);
		
		try {
			int totalCount=dao.findBookByNameAllCount(searchfield);
			//4总数量
			bean.setTotalCount(totalCount);
			int totalpage=(int) Math.ceil(totalCount*1.0/currentCount);
			//5总页码
			bean.setTotalPage(totalpage);
		 	List<Product> ps= dao.findBookByName(currentPage, currentCount, searchfield);
		 	
		 	//6封装查询的图书列表
		 	bean.setPs(ps);
		 	return bean;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("搜索框根据书名查询图书失败");
		}
	}
	
	//3业务方法：根据id查找某个商品
	public Product findProductById(String id) throws FindProductByIdException {
		try {
			return dao.findProductById(id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FindProductByIdException("根据id查找图书失败");
		}
	}
	
	//4 获取本周热卖的商品
	public List<Object[]> getWeekHotProduct(){
		try {
			return dao.getWeekHotProduct();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("获取本周热卖商品失败！");
		}
	}

	
	/*
	 * 后台查询所有的商品的方法
	 */
	public List<Product> listAll() {
		try {
			List<Product> list=dao.listAll();
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("没有查询到商品---查询出错了");
		}
	}
	//多条件查询操作
	public List<Product> findProductByManyCondition(String id, String name, String category, String minprice,
			String maxprice) {
		List<Product> ps=null;
		try {
			ps = dao.findProductByManyCondition(id,name,category,minprice,maxprice);
			return ps;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	//后台添加商品
	public void addProduct(Product p) throws AddProductException {
		try {
			dao.addProduct(p);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new AddProductException("添加失败");
		}
	}

	//后台编辑功能
		public void editProduct(Product p) {
			try {
				dao.editProduct(p);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//后台删除商品方法
		public void deleteProduct(String id) {
			try {
				dao.deleteProduct(id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//后台
		public List<Object[]> download(String year, String month) {
			List<Object[]> list;
			try {
				list = dao.download(year,month);
				return list;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
}
