package com.sharp.student.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;



/**
 * 
 * 
 * @author admin
 * @email 
 * @date 2018-06-28 18:01:04
 */
public class CreditDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//编号
	private Long id;
	//积分外建
	private Long creditRuleId;
	//学员ID
	private Long studentId;
	//
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date createDate;
	//创建人
	private String createBy;
	//积分
	private Integer credit;
	private String creditName;

	/**
	 * 设置：编号
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：编号
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：积分外建
	 */
	public void setCreditRuleId(Long creditRuleId) {
		this.creditRuleId = creditRuleId;
	}
	/**
	 * 获取：积分外建
	 */
	public Long getCreditRuleId() {
		return creditRuleId;
	}
	/**
	 * 设置 学生ID
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
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：积分
	 */
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	/**
	 * 获取：积分
	 */
	public Integer getCredit() {
		return credit;
	}

	public String getCreditName() {
		return creditName;
	}

	public void setCreditName(String creditName) {
		this.creditName = creditName;
	}
}
