package cn.itcast.bookshop.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.itcast.bookshop.domain.Notice;
import cn.itcast.bookshop.utils.DataSourceUtils;

public class NoticeDao {
	//查询最近一条公告
	public Notice getRecentNotice() throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDs());
		String sql="select * from notice order by n_time desc limit 0,1";
		return runner.query(sql, new BeanHandler<Notice>(Notice.class));
	}
}
