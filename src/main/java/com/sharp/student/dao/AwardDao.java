package com.sharp.student.dao;

import com.sharp.student.domain.AwardDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author admin
 * @email 
 * @date 2018-06-29 15:23:08
 */
@Mapper
public interface AwardDao {

	AwardDO get(Long id);
	
	List<AwardDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(AwardDO award);
	
	int update(AwardDO award);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
