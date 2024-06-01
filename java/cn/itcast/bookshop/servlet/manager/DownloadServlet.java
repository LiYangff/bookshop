package cn.itcast.bookshop.servlet.manager;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.bookshop.service.ProductService;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
				//1.获取下载条件
				String year=request.getParameter("year");
				String month=request.getParameter("month");
				//2.调用service
				ProductService service=new ProductService();
				//3.要查询下载的数据，已完成支付的商品信息
				List<Object[]> ps=service.download(year,month);
				String fileName=year+"年"+month+"月销售榜单.csv";
				String fileType=this.getServletContext().getMimeType(fileName);
				//3.1设置文件的响应头
				response.setContentType(fileType);
				//3.2设置下载框的弹出
				response.setHeader("content-Dispostion", "attachment;filename="+new String(fileName.getBytes("GBK"),"iso8859-1"));	
				response.setCharacterEncoding("gbk");	
				//4.把查询出来的数据，写到文件里面
				PrintWriter out = response.getWriter();
				out.println("商品名称,销售数量");
				for (int i = 0; i < ps.size(); i++) {
					Object[] arr=ps.get(i);
					out.println(arr[0]+","+arr[1]);			
				}
				out.flush();
				out.close();
	}

}
