package com.sharp.student.controller;

import java.util.List;
import java.util.Map;

import com.sharp.common.service.DictService;
import com.sharp.student.domain.CreditDO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sharp.student.domain.StudentDO;
import com.sharp.student.service.StudentService;
import com.sharp.common.utils.PageUtils;
import com.sharp.common.utils.Query;
import com.sharp.common.utils.R;

/**
 * 
 * 
 * @author admin
 * @email 
 * @date 2018-06-27 17:44:48
 */
 
@Controller
@RequestMapping("/student/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	DictService dictService;

	@GetMapping()
	@RequiresPermissions("student:student:student")
	String Student( Model model){

		model.addAttribute("sex", dictService.getSexList());
	    return "student/student/student";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("student:student:student")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<StudentDO> studentList = studentService.list(query);
		int total = studentService.count(query);
		PageUtils pageUtils = new PageUtils(studentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("student:student:add")
	String add(Model model){
		model.addAttribute("sexList",dictService.getSexList());
	    return "student/student/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("student:student:edit")
	String edit(@PathVariable("id") Long id,Model model){
		StudentDO student = studentService.get(id);
		model.addAttribute("student", student);
		model.addAttribute("sexList",dictService.getSexList());
	    return "student/student/edit";
	}


	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("student:student:add")
	public R save( StudentDO student){
		if(studentService.save(student)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("student:student:edit")
	public R update( StudentDO student){
		studentService.update(student);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("student:student:remove")
	public R remove( Long id){
		if(studentService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("student:student:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		studentService.batchRemove(ids);
		return R.ok();
	}


	@GetMapping("/updateCredit/{id}")
	@RequiresPermissions("student:student:edit")
	String updateCredit(@PathVariable("id") Long id,Model model){
		StudentDO student = studentService.get(id);
		model.addAttribute("student", student);
		model.addAttribute("sexList",dictService.getSexList());
		model.addAttribute("creditList", dictService.getCreditList());
		return "student/student/updateCredit";
	}

	/**
	 * 修改积分详情
	 */
	@ResponseBody
	@RequestMapping("/updateCreditInfo")
	//@RequiresPermissions("student:student:edit")
	public R updateCreditInfo( CreditDO creditDO){
		studentService.saveCredit(creditDO);
		return R.ok();
	}

	@GetMapping("/credit")
	@RequiresPermissions("student:student:student")
	String crdeit( Model model){
		return "student/student/credit";
	}

	@ResponseBody
	@GetMapping("/creditList")
	@RequiresPermissions("student:student:student")
	public PageUtils creditList(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<CreditDO> creditList = studentService.creditList(query);
		int total = studentService.creditCount(query);
		PageUtils pageUtils = new PageUtils(creditList, total);
		return pageUtils;
	}
	
}
