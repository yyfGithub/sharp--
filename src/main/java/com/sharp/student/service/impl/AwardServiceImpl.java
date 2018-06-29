package com.sharp.student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.sharp.student.dao.AwardDao;
import com.sharp.student.domain.AwardDO;
import com.sharp.student.service.AwardService;



@Service
public class AwardServiceImpl implements AwardService {
	@Autowired
	private AwardDao awardDao;
	
	@Override
	public AwardDO get(Long id){
		return awardDao.get(id);
	}
	
	@Override
	public List<AwardDO> list(Map<String, Object> map){
		return awardDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return awardDao.count(map);
	}
	
	@Override
	public int save(AwardDO award){
		return awardDao.save(award);
	}
	
	@Override
	public int update(AwardDO award){
		return awardDao.update(award);
	}
	
	@Override
	public int remove(Long id){
		return awardDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return awardDao.batchRemove(ids);
	}
	
}
