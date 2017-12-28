package com.insigma.mvc.dao.WEBPERM_001_001.userrole;

import java.util.List;

import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SysUser;


/**
 * 管理功能之机构、用户角色管理
 * @author wengsh
 *
 */
public interface WebUserRoleMapper {
	
	public List<SGroup> getAllGroupList(String parentid);
	
	public SGroup getGroupDataById(String groupid);
	
	public List<SysUser> getUserListByGroupid(String groupid);
	
	public List<SRole> getUserRoleByUserId(String userid);
	
	public int deleteUserRoleByUserId(String userid);
	
	public int saveUserRole(SRole srole);
	
	public int saveSysUserInfo(SysUser suser);
	
	public int saveSGroupUser(SysUser suser);
	
	public SysUser getSysUserInfoById(String username);
	
	public int saveSysAgency(SGroup sgroup);
	
	public SGroup getSysAgencyById(String name);
	
	public List<SGroup> getSysAgencyForChildById(String groupid);
	
	public int deleteSysAgencyInfoByGroupId(String groupid);
}
