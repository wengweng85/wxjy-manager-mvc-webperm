package com.insigma.mvc.service.WEBPERM_001_001.param;

import java.util.HashMap;
import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SysParam;


/**
 *  系统参数service
 * @author wengsh
 *
 */
public interface WebParamService {
	
	public List<SysParam> getList();
	
	public HashMap<String, Object> getPageList(SysParam sParam);
	
	public AjaxReturnMsg<String>  updateparam(SysParam sParam);
	
	public AjaxReturnMsg<String>  deleteparambyid(String  paramid);
	
	
}
