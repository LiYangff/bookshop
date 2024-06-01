package cn.itcast.bookshop.servlet.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.itcast.bookshop.domain.Product;
import cn.itcast.bookshop.exception.AddProductException;
import cn.itcast.bookshop.service.ProductService;
import cn.itcast.bookshop.utils.FileUploadutils;
import cn.itcast.bookshop.utils.IdUtils;

/**
 * Servlet implementation class AddProductServlet
 */
@WebServlet("/addProduct")
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
/**
 * 
 * 
 * 后台******
 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1.封装数据到product
				Product p = new Product();
				// 保存商品的数据，（name,'js入门'）（price，'20'）
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", IdUtils.getUUID());// 封装商品id
				// 2.上传图片
				/*
				 * 2.1创建工厂对象 创建临时文件位置temp
				 */
				DiskFileItemFactory dfif = new DiskFileItemFactory();
				String temp = this.getServletContext().getRealPath("/temp");
				dfif.setRepository(new File(temp));
				dfif.setSizeThreshold(1024 * 1024 * 10);
				// 2.2 通过工厂对象得到解释器对象
				ServletFileUpload upload = new ServletFileUpload(dfif);
				upload.setHeaderEncoding("utf-8");
				// 2.3 解析request，得到fileItem
				try {
					List<FileItem> items = upload.parseRequest(request);
					for (FileItem item : items) {
						boolean flag = item.isFormField();
						if (flag) {// 普通组件
							String fieldName = item.getFieldName();
							String value = item.getString("utf-8");
							map.put(fieldName, value);
						} else {// 上传组件
							String fileName = item.getName();
							fileName = FileUploadutils.subFileName(fileName);
							// 避免文件重名 uuid+"_"+filename
							String randomName = FileUploadutils.generateRandomFileName(fileName);
							// 得到随机目录，避免同一个目录下面存放文件过多的问题
							String randomDir = FileUploadutils.generateRandomDir(randomName);
							// 获取文件上传的位置
							String imgurl_parent = "/productImg" + randomDir;
							String imgPath = this.getServletContext().getRealPath(imgurl_parent);
							File parentDir = new File(imgPath);
							if (!parentDir.exists()) {
								parentDir.mkdirs();
							}
							// 把文件路径封装到map中
							String imgurl = imgurl_parent + "/" + randomName;
							map.put("imgurl", imgurl);

							// 上传文件
							InputStream input = item.getInputStream();
							FileOutputStream output = new FileOutputStream(new File(parentDir, randomName));
							IOUtils.copy(input, output);
							item.delete();
						}
					}
					// 把上传的商品数据保存到product对象中
					try {
						BeanUtils.populate(p, map);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 调用service完成添加
					ProductService service = new ProductService();
					// 有问题
					try {
						service.addProduct(p);
						response.sendRedirect(request.getContextPath() + "/listProduct");
					} catch (AddProductException e) {
						response.getWriter().print("添加商品失败");
					}

				} catch (FileUploadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

}
