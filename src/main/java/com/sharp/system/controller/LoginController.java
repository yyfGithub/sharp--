package com.sharp.system.controller;

import com.sharp.common.annotation.Log;
import com.sharp.common.controller.BaseController;
import com.sharp.common.domain.FileDO;
import com.sharp.common.domain.Tree;
import com.sharp.common.service.FileService;
import com.sharp.common.utils.MD5Utils;
import com.sharp.common.utils.R;
import com.sharp.common.utils.ShiroUtils;
import com.sharp.system.domain.DeptDO;
import com.sharp.system.domain.MenuDO;
import com.sharp.system.domain.UserDO;
import com.sharp.system.service.DeptService;
import com.sharp.system.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class LoginController extends BaseController {
	@Override
	public Long getUserId() {
		return super.getUserId();
	}

	@Override
	public UserDO getUser() {
		return super.getUser();
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MenuService menuService;
	@Autowired
	FileService fileService;
	@Autowired
	private DeptService sysDeptService;
	@GetMapping({ "/", "" })
	String welcome(Model model) {

		return "redirect:/login";
	}

	@Log("请求访问主页")
	@GetMapping({ "/index" })
	String index(Model model) {
		List<Tree<MenuDO>> menus = menuService.listMenuTree(getUserId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getUser().getName());
		FileDO fileDO = fileService.get(getUser().getPicId());
		if(fileDO!=null&&fileDO.getUrl()!=null){
			if(fileService.isExist(fileDO.getUrl())){
				model.addAttribute("picUrl",fileDO.getUrl());
			}else {
				model.addAttribute("picUrl","/img/photo_s.jpg");
			}
		}else {
			model.addAttribute("picUrl","/img/photo_s.jpg");
		}
		model.addAttribute("username", getUser().getUsername());
		return "index_v1";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@Log("登录")
	@PostMapping("/login")
	@ResponseBody
	R ajaxLogin(String username, String password) {

		password = MD5Utils.encrypt(username, password);
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);

			List<Long> depts = new ArrayList<>();
			UserDO user  = (UserDO) subject.getPrincipal();
			depts = getDepts(user.getDeptId());
			subject.getSession().setAttribute("depts", depts);
			return R.ok();
		} catch (AuthenticationException e) {
			return R.error("用户或密码错误");
		}
	}

	@GetMapping("/logout")
	String logout() {
		ShiroUtils.logout();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	private  List<Long> getDepts (Long pid) {

		List<Long> deptIds = new ArrayList<>();
		deptIds.add(pid);
		List<DeptDO> sysDepts = sysDeptService.list(new HashMap<String,Object>(16));

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
