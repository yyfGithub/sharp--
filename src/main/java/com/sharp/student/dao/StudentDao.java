package com.sharp.student.dao;

import com.sharp.student.domain.StudentDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author admin
 * @email 
 * @date 2018-06-27 17:44:48
 */
@Mapper
public interface StudentDao {

	StudentDO get(Long id);

	StudentDO getByUserId(Long userId);
	
	List<StudentDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(StudentDO student);
	
	int update(StudentDO student);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
