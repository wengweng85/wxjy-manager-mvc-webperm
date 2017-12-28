package com.insigma.mvc.service.WEBPERM_001_001.perm;

import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SPermission;




/**
 * 管理功能之权限管理service
 * @author wengsh
 *
 */
public interface WebPermService {
	
	public List<SPermission>  getPermTreeList();
	
	public AjaxReturnMsg getPermDataById(String id);
	
	public AjaxReturnMsg saveOrUpdatePermData(SPermission spermission);
	
    public AjaxReturnMsg getPermListDataByParentid(String parentid);
	
	public AjaxReturnMsg deletePermDataById(String id);
	
	public AjaxReturnMsg batdeletePermData(SPermission spermission); 
	
	public AjaxReturnMsg<String> moveNode(String id);

}
