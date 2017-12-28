package com.insigma.mvc.controller.WEBPERM_001_001.param;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.DemoAc01;
import com.insigma.mvc.model.SysParam;
import com.insigma.mvc.service.WEBPERM_001_001.param.WebParamService;


/**
 * ϵͳ��������
 * @author wengsh
 *
 */
@Controller
@RequestMapping("/web/param")
public class WebParamController extends MvcHelper<DemoAc01> {
	
	
	@Resource
	private WebParamService webParamService;
	
	/**
	 * ��ת����ѯҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/param/webParamIndex");
        return modelAndView;
	}
	
	
	/**
	 * ��ȡ�����б�
	 * @param request
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public HashMap<String,Object> getList(HttpServletRequest request,Model model,SysParam	 sParam  ) throws Exception {
			return webParamService.getPageList(sParam);
	}
	
	
	/**
	 * ��������
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateparam")
	@ResponseBody
	public AjaxReturnMsg<String> updateparam(HttpServletRequest request,Model mode,SysParam sParam) throws Exception {
		return webParamService.updateparam(sParam);
		
	}
	
	/**
	 * ���ݲ���idɾ��������Ϣ
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteparambyid/{paramid}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteparambyid(HttpServletRequest request,Model mode,@PathVariable String  paramid) throws Exception {
		return webParamService.deleteparambyid(paramid);
	}
	

}
