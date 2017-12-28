package com.insigma.mvc.service.WEBPERM_001_001.role;

import java.util.HashMap;
import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SRole;




/**
 * 管理功能之权限管理service
 * @author wengsh
 *
 */
public interface WebRoleService {
	
    public HashMap<String,Object>  getAllRoleList( SRole role);
	
	public AjaxReturnMsg getRoleDataById(String id);
	
	public AjaxReturnMsg saveOrUpdateRoleData(SRole srole);
	
	public AjaxReturnMsg deleteRoleDataById(String id);
	
	public List<SRole> getRolePermTreeData(String roleid);
	
	public AjaxReturnMsg saveRolePermData(SRole srole);

}
