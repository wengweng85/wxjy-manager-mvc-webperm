package com.insigma.mvc.dao.WEBPERM_001_001.perm;

import java.util.List;

import com.insigma.mvc.model.SPermission;


/**
 * 管理功能之权限管理
 * @author wengsh
 *
 */
public interface WebPermMapper {
	
	public List<SPermission> getPermTreeList();
	
	public SPermission getPermDataById(String id);
	
	public SPermission isPermCodeExist(SPermission spermission);
	
	public SPermission isPermUrlExist(SPermission spermission);
	
	public int savePermissionData(SPermission spermission);
	
	public int updatePermissionData(SPermission spermission);
	
	public List<SPermission> getPermListDataByParentid(String parentid);
	
	public int deletePermDataById(String id);
	
	public int batdeletePermData(String []  ids);
	
	

}
