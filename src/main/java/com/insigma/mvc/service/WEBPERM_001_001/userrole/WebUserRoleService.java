package com.insigma.mvc.service.WEBPERM_001_001.userrole;

import java.util.HashMap;
import java.util.List;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SysUser;




/**
 * 管理功能之权限管理service
 * @author wengsh
 *
 */
public interface WebUserRoleService {
	
    public List<SGroup>  getAllGroupList(String parentid);
    
    public AjaxReturnMsg getGroupDataById(String groupid);
	
	public HashMap<String,Object> getUserListByGroupid(SGroup sgroup);
	
	public HashMap<String,Object> getUserRoleByUserId(SRole srole);
	
	public AjaxReturnMsg saveUserRole(SRole srole);
	
	public AjaxReturnMsg saveAddSysUser(SysUser suser);
	
	public AjaxReturnMsg saveAddSysAgency(SGroup sgroup);
	
	public AjaxReturnMsg deleteSysAgency(String groupid);
	
}
