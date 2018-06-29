package com.sharp.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sharp.common.domain.LogDO;
import com.sharp.common.domain.PageDO;
import com.sharp.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
