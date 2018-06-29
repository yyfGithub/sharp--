package com.sharp.system.service.impl;

import com.sharp.system.domain.UserDO;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sharp.common.domain.Tree;
import com.sharp.common.utils.BuildTree;
import com.sharp.system.dao.DeptDao;
import com.sharp.system.domain.DeptDO;
import com.sharp.system.service.DeptService;



@Service
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptDao sysDeptMapper;

	@Override
	public DeptDO get(Long deptId){
		return sysDeptMapper.get(deptId);
	}

	@Override
	public List<DeptDO> list(Map<String, Object> map){
		List<Long> depts = (List<Long>) SecurityUtils.getSubject().getSession().getAttribute("depts");
		map.put("depts", depts);
		return sysDeptMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return sysDeptMapper.count(map);
	}

	@Override
	public int save(DeptDO sysDept){
		int result = sysDeptMapper.save(sysDept);
		changDepts();
		return result;
	}

	@Override
	public int update(DeptDO sysDept){
		int result = sysDeptMapper.update(sysDept);
		changDepts();
		return result;
	}

	@Override
	public int remove(Long deptId){
		int result =  sysDeptMapper.remove(deptId);
		changDepts();
		return result;
	}

	@Override
	public int batchRemove(Long[] deptIds){
		int result = sysDeptMapper.batchRemove(deptIds);
		changDepts();
		return result;
	}

	@Override
	public Tree<DeptDO> getTree() {
		List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));
		for (DeptDO sysDept : sysDepts) {
			Tree<DeptDO> tree = new Tree<DeptDO>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}

		UserDO user = (UserDO) SecurityUtils.getSubject().getPrincipal();

		// 默认顶级菜单为０，根据数据库实际情况调整
		Tree<DeptDO> t = BuildTree.build(trees, user.getDeptId().toString());
		return t;
	}

	@Override
	public boolean checkDeptHasUser(Long deptId) {
		// TODO Auto-generated method stub
		//查询部门以及此部门的下级部门
		int result = sysDeptMapper.getDeptUserNumber(deptId);
		return result==0?true:false;
	}

	private void changDepts(){
		UserDO userDO = (UserDO) SecurityUtils.getSubject().getPrincipal();
		List<Long> list = getDepts(userDO.getDeptId());
		SecurityUtils.getSubject().getSession().setAttribute("depts", list);
	}

	private  List<Long> getDepts (Long pid) {

		List<Long> deptIds = new ArrayList<>();
		deptIds.add(pid);
		List<DeptDO> sysDepts = sysDeptMapper.list(new HashMap<String,Object>(16));

		getChild(pid.toString(), sysDepts, deptIds);

		return deptIds;
	}

	private void getChild(String id, List<DeptDO> deptDOS, List<Long> childs) {
		List<DeptDO> childList = new ArrayList<>();
		for (DeptDO deptDO : deptDOS) {
			if (deptDO.getParentId().toString().equals(id)) {
				childList.add(deptDO);
				childs.add(deptDO.getDeptId());
			}
		}
		for (DeptDO deptDO : childList) {// 没有url子菜单还有子菜单
			getChild(deptDO.getDeptId().toString(), deptDOS, childs);
		}
		// 递归退出条件
		if (childList.size() == 0) {
			return;
		}
	}

}
