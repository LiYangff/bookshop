package cn.itcast.bookshop.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSourceUtils {//数据连接池
	//定义数据源对象，采用c3p0
	private static DataSource ds=new ComboPooledDataSource();
	//定义线程工具类对象
	private static ThreadLocal<Connection> t1=new ThreadLocal<Connection>();
	
	public static DataSource getDs() {
		return ds;
	}

	//获取连接
	public static Connection getConnection() throws SQLException{
		Connection con=t1.get();
		if(con==null){
			con=ds.getConnection();
			t1.set(con);
		}
		return con;
	}
	
	//开始事务
	public static void startTransaction() throws SQLException{
		Connection con=getConnection();
		if(con!=null){
			con.setAutoCommit(false);
		}
	}
	
	//ThreadLocal中释放连接，结束事务
	public static void releaseAndCloseConnection()throws SQLException{
		Connection con=getConnection();
		if(con!=null){
			con.commit();
			t1.remove();
			con.close();
		}
	}
	//事务回滚：不保存执行内容结束事务
	public static void rollback() throws SQLException{
		Connection con=getConnection();
		if(con!=null){
			con.rollback();
		}
	}
	
}
