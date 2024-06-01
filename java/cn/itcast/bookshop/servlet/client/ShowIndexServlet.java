package cn.itcast.bookshop.servlet.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.domain.Notice;
import cn.itcast.bookshop.service.NoticeService;
import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class ShowIndexServlet
 */
@WebServlet("/showIndexServlet")
public class ShowIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1获取首页显示的公告内容和本周热卖
		//查询最近一条公告
		NoticeService noticeService=new NoticeService();
		Notice notice=noticeService.getRecentNotice();
		//获取本周销量前二的两本书
		ProductService pService=new ProductService();
		List<Object[]> pList=pService.getWeekHotProduct();
		
		//2将获取的信息存入request域对象
		request.setAttribute("n", notice);
		request.setAttribute("pList", pList);
		//3跳转到client/index.jsp页面
		request.getRequestDispatcher("/client/index.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
