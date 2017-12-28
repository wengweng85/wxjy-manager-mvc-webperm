package com.insigma.mvc.controller.WEBPERM_001_001.role;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.WEBPERM_001_001.role.WebRoleService;

/**
 * 角色管理及角色角色分配管理
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/web/role")
public class WebRoleController extends MvcHelper  {
	
	@Resource
	private WebRoleService webRoleService;
	/**
	 * 页面初始化
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/role/webRoleIndex");
        return modelAndView;
	}
	
	/**
	 * 角色列表查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/querylist")
	@ResponseBody
	
	public HashMap<String,Object> querylist(HttpServletRequest request,Model model,SRole srole) throws Exception {
		return webRoleService.getAllRoleList(srole);
	}
	
	
	/**
	 * 通过角色编号获取角色数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRoleData/{id}")
	
	@ResponseBody
	public AjaxReturnMsg getPermDataByid(HttpServletRequest request, HttpServletResponse response,Model model,@PathVariable String id) throws Exception {
		return webRoleService.getRoleDataById(id);
	}
	
	/**
	 * 删除角色相关数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteRoleDataById/{id}")
	@ResponseBody
	public AjaxReturnMsg deleteRoleDataById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return  webRoleService.deleteRoleDataById(id);
	}
	
	/**
	 * 更新或保存角色
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveorupdate")
	@ResponseBody
	public AjaxReturnMsg saveorupdate(HttpServletRequest request,Model model,@Valid SRole srole,BindingResult result) throws Exception {
		//检验输入
		if (result.hasErrors()){
			return validate(result);
		}
		return webRoleService.saveOrUpdateRoleData(srole);
	}
	
	/**
	 * 角色-权限树加载
	 * @param request
	 * @return
	 */
	@RequestMapping("/treedata")
	@ResponseBody
	public  List<SRole> treedata(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		String id=request.getParameter("id");
		return webRoleService.getRolePermTreeData(id);
	}
	
	
	/**
	 * 更新或保存权限
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveroleperm")
	@ResponseBody
	public AjaxReturnMsg saveroleperm(HttpServletRequest request,Model model,SRole srole) throws Exception {
		return webRoleService.saveRolePermData(srole);
	}
}
