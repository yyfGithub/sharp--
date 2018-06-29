package com.sharp.student.dao;

import com.sharp.student.domain.CreditDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author admin
 * @email 
 * @date 2018-06-28 18:01:04
 */
@Mapper
public interface CreditDao {

	CreditDO get(Long id);
	
	List<CreditDO> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int save(CreditDO credit);
	
	int update(CreditDO credit);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
