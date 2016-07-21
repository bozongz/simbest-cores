package com.simbest.cores.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
public abstract class GenericModel<T> extends BaseObject<T>{

	@Transient
	private String orderByClause;  //动态排序

	@Transient
	private Date ssDate; //时间区间-开始时间
	
	@Transient
	private Date eeDate; //时间区间-结束时间
	
	@Transient
	private Integer pageindex; //起始页码
	
	@Transient
	private Integer pagesize; //每页容量
	
	/**
	 * @return the orderByClause
	 */
	@JsonIgnore
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * @param orderByClause the orderByClause to set
	 */
	@JsonProperty
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * @return the ssDate
	 */
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	public Date getSsDate() {
		return ssDate;
	}

	/**
	 * @param ssDate the ssDate to set
	 */
	public void setSsDate(Date ssDate) {
		this.ssDate = ssDate;
	}

	/**
	 * @return the eeDate
	 */
	@JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
	public Date getEeDate() {
		return eeDate;
	}

	/**
	 * @param eeDate the eeDate to set
	 */
	public void setEeDate(Date eeDate) {
		this.eeDate = eeDate;
	}

	/**
	 * @return the pageindex
	 */
	public Integer getPageindex() {
		return pageindex;
	}

	/**
	 * @param pageindex the pageindex to set
	 */
	public void setPageindex(Integer pageindex) {
		this.pageindex = pageindex;
	}

	/**
	 * @return the pagesize
	 */
	public Integer getPagesize() {
		return pagesize;
	}

	/**
	 * @param pagesize the pagesize to set
	 */
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	
}
