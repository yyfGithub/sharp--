package com.sharp.student.service;

import com.sharp.student.domain.CreditDO;
import com.sharp.student.domain.StudentDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author admin
 * @email 
 * @date 2018-06-27 17:44:48
 */
public interface StudentService {
	
	StudentDO get(Long id);

	StudentDO getByUserId(Long userId);
	
	List<StudentDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(StudentDO student);
	
	int update(StudentDO student);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);

	int saveCredit(CreditDO credit);

	List<CreditDO> creditList(Map<String, Object> map);

	int creditCount(Map<String, Object> map);
}
