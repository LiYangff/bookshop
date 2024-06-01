package cn.itcast.bookshop.domain;

import java.util.List;

public class PageBean {//分页展示商品的信息
	private int currentPage;//当前的页码
	private int totalPage;//总页数
	private int totalCount;//总页码上的总数量
	private int currentCount;//当前页显示的数量
	private String category;//目前分页的类别
	private List<Product> ps;
	private String searchfield;//查询范围
	
	
	public List<Product> getPs() {
		return ps;
	}
	public void setPs(List<Product> ps) {
		this.ps = ps;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSearchfield() {
		return searchfield;
	}
	public void setSearchfield(String searchfield) {
		this.searchfield = searchfield;
	}
	
}
