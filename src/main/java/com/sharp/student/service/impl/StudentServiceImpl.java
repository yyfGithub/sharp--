package com.sharp.student.service.impl;

import com.sharp.common.dao.DictDao;
import com.sharp.common.domain.DictDO;
import com.sharp.common.utils.StringUtils;
import com.sharp.student.config.GradeConfig;
import com.sharp.student.dao.AwardDao;
import com.sharp.student.dao.CreditDao;
import com.sharp.student.domain.AwardDO;
import com.sharp.student.domain.CreditDO;
import com.sharp.system.dao.DeptDao;
import com.sharp.system.domain.DeptDO;
import com.sharp.system.domain.UserDO;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.*;

import com.sharp.student.dao.StudentDao;
import com.sharp.student.domain.StudentDO;
import com.sharp.student.service.StudentService;



@Service
public class StudentServiceImpl implements StudentService {



	@Autowired
	private StudentDao studentDao;
	@Autowired
	DeptDao deptMapper;
	@Autowired
	private CreditDao creditDao;
	@Autowired
	private DictDao dictDao;
	@Autowired
	private AwardDao awardDao;


	@Override
	public StudentDO get(Long id){
		StudentDO studentDO = studentDao.get(id);
		studentDO.setDeptName(deptMapper.get(studentDO.getDeptId()).getName());
		return studentDO;
	}

	@Override
	public StudentDO getByUserId(Long userId) { return studentDao.getByUserId(userId); }
	
	@Override
	public List<StudentDO> list(Map<String, Object> map){
		List<Long> depts = (List<Long>) SecurityUtils.getSubject().getSession().getAttribute("depts");
		map.put("depts", depts);

		return studentDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return studentDao.count(map);
	}
	
	@Override
	public int save(StudentDO student){
		student.setUpdateDate(new Date());
		student.setCreateDate(new Date());
		return studentDao.save(student);
	}
	
	@Override
	public int update(StudentDO student){
		student.setUpdateDate(new Date());
		return studentDao.update(student);
	}
	
	@Override
	public int remove(Long id){
		return studentDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return studentDao.batchRemove(ids);
	}

	@Transient
	@Override
	public int saveCredit(CreditDO credit){

		UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
		DictDO dictDO = dictDao.get(credit.getCreditRuleId());
		credit.setCredit(Integer.parseInt(dictDO.getValue()));
		credit.setCreateDate(new Date());
		credit.setCreateBy(userDO.getName());
		int a = creditDao.save(credit);

		StudentDO studentDO = studentDao.get(credit.getStudentId());
		//总积分
		int integral = studentDO.getCredit() + Integer.parseInt(dictDO.getValue());
		studentDO.setCredit(integral);
		String starLevel = getStarLevel(integral);
		String oldStarLevel = studentDO.getStarLevel();
		studentDO.setStarLevel(starLevel);
		studentDao.update(studentDO);

		updateAward(oldStarLevel, starLevel, studentDO.getId());

		return a;
	}

	/**
	 * 获取称号
	 * @param integral 个人总积分
	 * @return
	 */
	private String getStarLevel(int integral) {
		StringBuilder result = new StringBuilder();
		int level =  integral / GradeConfig.STAR_JF;
		if (level < 27) {
			result.append(GradeConfig.LAYER_NAME[0]);
			result.append(" ");
			result.append(level / 9);
			result.append("级 ");
			if (level < 9) {
				result.append(level);
				result.append("星 ");
			} else {
				result.append(level % 9);
				result.append("星 ");
			}
		} else if (level > GradeConfig.LEVEL_NUM){
			result.append(GradeConfig.LAYER_NAME[5]);
		} else {

			result.append(GradeConfig.LAYER_NAME[level / 27]);
			result.append(" ");
			result.append(level % 27 / 9);
			result.append("级 ");

			if ((level % 27) < 9) {
				result.append(level % 27);
				result.append("星 ");
			} else {
				result.append(level % 27 % 9);
				result.append("星 ");
			}
		}
		return result.toString();
	}

	public void updateAward (String oldStr, String newStr,Long studentId){

		if (StringUtils.isNotBlank(oldStr) && StringUtils.isNotBlank(newStr) ) {
			if (!oldStr.equals(newStr)) {
				AwardDO awardDO = new AwardDO();
				awardDO.setInfo("上升一个星，颁发证书");
				awardDO.setStudentId(studentId);
				awardDO.setStatus(0);
				awardDO.setCreateDate(new Date());
				awardDao.save(awardDO);

				if (newStr.indexOf("3星") > -1 || newStr.indexOf("6星") > -1) {
					awardDO = new AwardDO();
					awardDO.setInfo("达到" + newStr + "奖励200");
					awardDO.setStudentId(studentId);
					awardDO.setStatus(0);
					awardDO.setCreateDate(new Date());
					awardDao.save(awardDO);
				}

				String temp = newStr.substring(0,2);
				if (!oldStr.substring(0,2).equals(temp) && temp.equals(GradeConfig.LAYER_NAME[1])
						&& temp.equals(GradeConfig.LAYER_NAME[2]) && temp.equals(GradeConfig.LAYER_NAME[3])) {
					awardDO = new AwardDO();
					awardDO.setInfo("达到" + temp + "，颁发证书、奖杯，另加1000大洋。");
					awardDO.setStudentId(studentId);
					awardDO.setStatus(0);
					awardDO.setCreateDate(new Date());
					awardDao.save(awardDO);
				}

				if (newStr.equals(GradeConfig.LAYER_NAME[5])) {
					awardDO = new AwardDO();
					awardDO.setInfo("达到" + GradeConfig.LAYER_NAME[5] + "，颁发证书、奖杯，另加5000大洋。");
					awardDO.setStudentId(studentId);
					awardDO.setStatus(0);
					awardDO.setCreateDate(new Date());
					awardDao.save(awardDO);
				}

			}
		}
	}

	@Override
	public List<CreditDO> creditList(Map<String, Object> map){
		return creditDao.list(map);
	}
	@Override
	public int creditCount(Map<String, Object> map){
		return creditDao.count(map);
	}
}
