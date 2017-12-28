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
 * ��ɫ������ɫ��ɫ�������
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/web/role")
public class WebRoleController extends MvcHelper  {
	
	@Resource
	private WebRoleService webRoleService;
	/**
	 * ҳ���ʼ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/role/webRoleIndex");
        return modelAndView;
	}
	
	/**
	 * ��ɫ�б��ѯ
	 * @param request
	 * @return
	 */
	@RequestMapping("/querylist")
	@ResponseBody
	
	public HashMap<String,Object> querylist(HttpServletRequest request,Model model,SRole srole) throws Exception {
		return webRoleService.getAllRoleList(srole);
	}
	
	
	/**
	 * ͨ����ɫ��Ż�ȡ��ɫ����
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRoleData/{id}")
	
	@ResponseBody
	public AjaxReturnMsg getPermDataByid(HttpServletRequest request, HttpServletResponse response,Model model,@PathVariable String id) throws Exception {
		return webRoleService.getRoleDataById(id);
	}
	
	/**
	 * ɾ����ɫ�������
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteRoleDataById/{id}")
	@ResponseBody
	public AjaxReturnMsg deleteRoleDataById(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		return  webRoleService.deleteRoleDataById(id);
	}
	
	/**
	 * ���»򱣴��ɫ
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveorupdate")
	@ResponseBody
	public AjaxReturnMsg saveorupdate(HttpServletRequest request,Model model,@Valid SRole srole,BindingResult result) throws Exception {
		//��������
		if (result.hasErrors()){
			return validate(result);
		}
		return webRoleService.saveOrUpdateRoleData(srole);
	}
	
	/**
	 * ��ɫ-Ȩ��������
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
	 * ���»򱣴�Ȩ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveroleperm")
	@ResponseBody
	public AjaxReturnMsg saveroleperm(HttpServletRequest request,Model model,SRole srole) throws Exception {
		return webRoleService.saveRolePermData(srole);
	}
}
