package cn.itcast.bookshop.service;

import java.sql.SQLException;

import cn.itcast.bookshop.dao.NoticeDao;
import cn.itcast.bookshop.domain.Notice;

public class NoticeService {
	NoticeDao dao=new NoticeDao();
	//1查询最新的公告
	public Notice getRecentNotice() {
		try {
			return dao.getRecentNotice();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("查询最新添加或修改的一条公告失败！");
		}
	}
}
