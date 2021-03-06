package com.jt.common.vo;

import java.io.Serializable;
import java.util.List;

public class PageObject<T> implements Serializable{
	private static final long serialVersionUID = 6780580291247550747L;
	 /**当前页的页码值*/
		private Integer pageCurrent;
	    /**页面大小*/
	    private Integer pageSize;
	    /**总行数(通过查询获得)*/
	    private Integer rowCount;
	    /**总页数(通过计算获得)*/
	    private Integer pageCount;
	    /**当前页记录*/
	    private List<T> records;
		public Integer getPageCurrent() {
			return pageCurrent;
		}
		public void setPageCurrent(Integer pageCurrent) {
			this.pageCurrent = pageCurrent;
		}
		public Integer getPageSize() {
			return pageSize;
		}
		public void setPageSize(Integer pageSize) {
			System.out.println("pageSize:"+pageSize);
			this.pageSize = pageSize;
		}
		public Integer getRowCount() {
			return rowCount;
		}
		public void setRowCount(Integer rowCount) {
			System.out.println("rowCount:"+rowCount);
			this.rowCount = rowCount;
		}
		public Integer getPageCount() {
			//计算总页数，为什么会在此方法进行计算
			//将对象转换为json串时底层调用的是对象的get方法
			pageCount=rowCount/pageSize;//1
			if(rowCount%pageSize!=0){
				pageCount++;
			}
			return pageCount;
		}
		public void setPageCount(Integer pageCount) {
			this.pageCount = pageCount;
		}
		public List<T> getRecords() {
			return records;
		}
		public void setRecords(List<T> records) {
			this.records = records;
		}

}
