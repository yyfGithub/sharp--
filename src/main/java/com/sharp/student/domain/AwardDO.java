package com.sharp.student.domain;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author admin
 * @email 
 * @date 2018-06-29 15:23:08
 */
public class AwardDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long id;
	//详情
	private String info;
	//学员ID
	private Long studentId;
	//0未发放1已发发
	private Integer status;
	//
	private Date createDate;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：详情
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取：详情
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置：学员ID
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * 获取：学员ID
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * 设置：0未发放1已发发
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0未发放1已发发
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
	public Date getCreateDate() {
		return createDate;
	}
}
