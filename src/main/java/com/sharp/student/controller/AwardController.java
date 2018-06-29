package com.sharp.student.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharp.student.domain.AwardDO;
import com.sharp.student.service.AwardService;
import com.sharp.common.utils.PageUtils;
import com.sharp.common.utils.Query;
import com.sharp.common.utils.R;

/**
 * 
 * 
 * @author admin
 * @email 
 * @date 2018-06-29 15:23:08
 */
 
@Controller
@RequestMapping("/student/award")
public class AwardController {
	@Autowired
	private AwardService awardService;
	
	@GetMapping()
	@RequiresPermissions("student:award:award")
	String Award(){
	    return "student/award/award";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("student:award:award")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<AwardDO> awardList = awardService.list(query);
		int total = awardService.count(query);
		PageUtils pageUtils = new PageUtils(awardList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("student:award:add")
	String add(){
	    return "student/award/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("student:award:edit")
	String edit(@PathVariable("id") Long id,Model model){
		AwardDO award = awardService.get(id);
		model.addAttribute("award", award);
	    return "student/award/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("student:award:add")
	public R save( AwardDO award){
		if(awardService.save(award)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("student:award:edit")
	public R update( AwardDO award){
		awardService.update(award);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("student:award:remove")
	public R remove( Long id){
		if(awardService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("student:award:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		awardService.batchRemove(ids);
		return R.ok();
	}
	
}
