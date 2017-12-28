package com.insigma.mvc.controller.WEBPERM_001_001.codetype;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.WEBPERM_001_001.codetype.WebCodeTypeService;
import com.insigma.resolver.AppException;

/**
 * Created by wengsh on 2015-01-14.
 */
@Controller
@RequestMapping("/web/codetype")
public class WebCodeTypeController extends MvcHelper<CodeValue> {

	@Resource
	private WebCodeTypeService webCodeTypeService;
	
	/**
	 * ��ת����������ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/toCodeValuesuggest")
	public ModelAndView toCodeValuesuggest(HttpServletRequest request, HttpServletResponse response,CodeValue codevalue) throws AppException {
		String callback_fun_name=request.getParameter("callback_fun_name");
		String codetype=request.getParameter("codetype");
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/codeValueSelect");
        modelAndView.addObject("callback_fun_name", callback_fun_name);
        modelAndView.addObject("codetype", codetype);
        return modelAndView;
	}
	
	/**
	 *codevalue ������
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/treedata/{code_type}")
	@ResponseBody
	public List<CodeValue> treedata(HttpServletRequest request, HttpServletResponse response,@PathVariable String code_type) throws AppException {
		String id=request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			id="000000";
		}
		CodeValue codevalue=new CodeValue();
		codevalue.setPar_code_value(id);
		codevalue.setCode_type(code_type.toUpperCase());
		return webCodeTypeService.getCodeValueTree(codevalue);
	}
	
	/**
	 * ���ݴ������ͼ����븸������ȡ����ֵ
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/queryByCodeTypeAndParent")
	@ResponseBody
	public List<CodeValue> queryByCodeTypeAndParent(HttpServletRequest request, HttpServletResponse response,CodeValue codevalue) throws AppException {
		return webCodeTypeService.queryCodeValueByCodeTypeAndParent(codevalue);
	}
	
	
	/**
	 * �ӻ����л�ȡ����ֵ
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/getCodeValueFromCache")
	@ResponseBody
	public HashMap<String,List<CodeValue>> getCodeValueFromCache(HttpServletRequest request, HttpServletResponse response,CodeValue codevalue) throws AppException {
		return webCodeTypeService.getCodeValueFromCache(codevalue);
	}
	
	
	
	/**
	 * ��ת���������ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,Model model,CodeType codetype) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeIndex");
		modelAndView.addObject("codetype",codetype);  
        return modelAndView;
	}
	
	
	/**
	 *codetype ������
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/codetype_treedata")
	@ResponseBody
	public List<CodeType> codetype_treedata(HttpServletRequest request, HttpServletResponse response,CodeType codetype) throws AppException {
		return webCodeTypeService.getCodeTypeTreeData(codetype);
	}
	
	
	/**
	 *��ת������ֵ���༭ҳ��
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/toCodeValueTreePage/{id}")
	@ResponseBody
	public ModelAndView toCodeValueTreePage(HttpServletRequest request, HttpServletResponse response,@PathVariable String id) throws AppException {
		CodeType codetype=webCodeTypeService.getCodeTypeInfo(id);
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeEdit");
		modelAndView.addObject("codetype", codetype);
        return modelAndView;
	}
	
	/**
	 *codevalue ������
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws com.insigma.resolver.AppException
	 */
	@RequestMapping(value = "/codevalue_treedata")
	@ResponseBody
	public List<CodeType> codevalue_treedata(HttpServletRequest request, HttpServletResponse response,CodeType  codetype) throws AppException {
		return webCodeTypeService.getCodeValueTreeData(codetype);
	}
	
	
	
	/**
	 * ��ת���������������༭ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCodeTypeEdit/{id}")
	public ModelAndView toCodeTypeEdit(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		CodeType codetype=webCodeTypeService.getCodeTypeInfo(id);
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeInfoEdit");
		modelAndView.addObject("codetype", codetype);
        return modelAndView;
	}
	
	
	/**
	 * ��ת��������������ҳ��
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCodeTypeAdd")
	public ModelAndView toCodeTypeAdd(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeInfoAdd");
        return modelAndView;
	}
	
	
	/**
	 * ���»򱣴��������
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCodeType")
	@ResponseBody
	public AjaxReturnMsg<String> saveOrUpdateCodeTypedata(HttpServletRequest request,Model model,@Valid CodeType codetype,BindingResult result) throws Exception {
		//��������
		if (result.hasErrors()){
			return validate(result);
		}
		return webCodeTypeService.saveOrUpdateCodeType(codetype);
	}
	
	
	/**
	 * ��ת������ֵ��ϸ�༭ҳ��
	 * @param request
	 * @return
	 */
	@RequestMapping("/toCodeTypeDetailEdit/{id}")
	public ModelAndView toCodeTypeDetailEdit(HttpServletRequest request,Model model,@PathVariable String id) throws Exception {
		CodeValue codevalue=webCodeTypeService.getCodeTypeDetailInfo(id);
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeDetailInfoEdit");
		modelAndView.addObject("codevalue", codevalue);
        return modelAndView;
	}
	
	
	/**
	 * ��ת������ֵ��ϸҳ��
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCodeTypeDetailAddFromRoot/{code_type}")
	public ModelAndView toCodeTypeDetailAddFromRoot(HttpServletRequest request,Model model,@PathVariable String code_type) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeDetailInfoAdd");
		//ͨ���������ͻ�ȡ������ϸ
		CodeType codetype=webCodeTypeService.getCodeTypeInfo(code_type);
		CodeValue codevalue=new CodeValue();
	    //�ڸ�����������Ӵ���ֵ��ϸʱĬ�ϵĸ��ڵ����ֵΪ�������ͱ��е�code_root_value
		codevalue.setCode_type(code_type);
		codevalue.setPar_code_value(codetype.getCode_root_value());
		modelAndView.addObject("codevalue", codevalue);
        return modelAndView;
	}
	
	/**
	 * ��ת������ֵ��ϸҳ��
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCodeTypeDetailAddFromNode/{par_code_seq}")
	public ModelAndView toCodeTypeDetailAddFromNode(HttpServletRequest request,Model model,@PathVariable String par_code_seq) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeDetailInfoAdd");
		CodeValue codevalue=webCodeTypeService.getCodeTypeDetailInfo(par_code_seq);
		//���õ�ǰ�ڵ�ĸ������ϢΪѡ�еĽ�����Ϣ
		codevalue.setPar_code_value(codevalue.getCode_value());
		codevalue.setPar_code_name(codevalue.getCode_name());
		modelAndView.addObject("codevalue", codevalue);
        return modelAndView;
	}
	
	
	/**
	 * ���»򱣴��������
	 * @param request
	 * @return
	 */
	@RequestMapping("/saveOrUpdateCodeTypeDetail")
	@ResponseBody
	public AjaxReturnMsg<String> saveOrUpdateCodeTypeDetail(HttpServletRequest request,Model model,@Valid CodeValue codevalue,BindingResult result) throws Exception {
		//��������
		if (result.hasErrors()){
			return validate(result);
		}
		return webCodeTypeService.saveOrUpdateCodeTypeDetail(codevalue);
	}
	
	
	/**
	 * ɾ����������
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCodeType/{code_type}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteCodeType(HttpServletRequest request,Model model,@PathVariable String code_type) throws Exception {
		return webCodeTypeService.deleteCodeType(code_type);
	}
	
	
	/**
	 * ɾ������ֵ
	 * @param request
	 * @return
	 */
	@RequestMapping("/deleteCodeValue/{code_seq}")
	@ResponseBody
	public AjaxReturnMsg<String> deleteCodeValue(HttpServletRequest request,Model model,@PathVariable String code_seq) throws Exception {
		return webCodeTypeService.deleteCodeValue(code_seq);
	}
	
	
	/**
	 * ��ת���������Ͳ�ѯҳ��
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCodeTypeQuery")
	public ModelAndView toCodeTypeQuery(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeInfoQuery");
        return modelAndView;
	}
	
	
	/**
	 * ��ת���������Ͳ�ѯҳ��
	 * @param request
	 * @param model
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/toCodeValueQuery")
	public ModelAndView toCodeValueQuery(HttpServletRequest request,Model model) throws Exception {
		ModelAndView modelAndView=new ModelAndView("webperm/WEBPERM_001_001/codevalue/webCodeTypeDetailInfoQuery");
        return modelAndView;
	}
	
	  
	 /**
	  * ͨ���������͡�����������ȡ���� 
	  * @param request
	  * @param response
	  * @param codevalue
	  * @return
	  * @throws AppException
	  */
	 @RequestMapping(value = "/getCodeValueList")
	 @ResponseBody
	 public List<CodeValue> getCodeValueList(HttpServletRequest request, HttpServletResponse response,CodeType codetype) throws AppException {
		   return webCodeTypeService.getInitCodeValueList(codetype);
	 } 
	
	
}