package com.insigma.mvc.serviceimp.WEBPERM_001_001.userrole;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.common.util.MD5Util;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.WEBPERM_001_001.userrole.WebUserRoleMapper;
import com.insigma.mvc.model.SGroup;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.model.SysUser;
import com.insigma.mvc.service.WEBPERM_001_001.userrole.WebUserRoleService;

/**
 * 用户角色管理
 * @author wengsh
 *
 */
@Service
public class WebUserRoleServiceImpl extends MvcHelper implements WebUserRoleService {

	@Resource
    private WebUserRoleMapper webUserRoleMapper;
	
	/**
	 * 获取机构信息树
	 */
	@Override
	public List<SGroup> getAllGroupList(String parentid) {
		 return webUserRoleMapper.getAllGroupList(parentid);
	}
	
	
	/**
	 * 获取机构信息
	 */
	@Override
	public AjaxReturnMsg getGroupDataById(String groupid) {
		return this.success(webUserRoleMapper.getGroupDataById(groupid));
	}
	
	
	/**
	 * 获取机构下人员信息
	 */
	@Override
	public HashMap<String,Object> getUserListByGroupid(SGroup sgroup) {
		PageHelper.offsetPage(sgroup.getOffset(), sgroup.getLimit());
		List<SysUser> list=webUserRoleMapper.getUserListByGroupid(sgroup.getGroupid());
		PageInfo<SysUser> pageinfo = new PageInfo<SysUser>(list);
		return this.success_hashmap_response(pageinfo);
	}


	/**
	 * 通过人员id获取角色选中状态
	 */
	@Override
	public HashMap<String,Object> getUserRoleByUserId(SRole srole) {
		PageHelper.offsetPage(srole.getOffset(), srole.getLimit());
		List<SRole> list=webUserRoleMapper.getUserRoleByUserId(srole.getUserid());
		PageInfo<SRole> pageinfo = new PageInfo<SRole>(list);
		return this.success_hashmap_response(pageinfo);
	}


	/**
	 * 保存用户角色
	 */
	@Override
	public AjaxReturnMsg saveUserRole(SRole srole) {
		webUserRoleMapper.deleteUserRoleByUserId(srole.getUserid());
		String[] selectnodes= srole.getSelectnodes().split(",");
		for(int i=0;i<selectnodes.length;i++){
			SRole temp=new SRole();
			temp.setUserid(srole.getUserid());
			temp.setRoleid(selectnodes[i]);
			webUserRoleMapper.saveUserRole(temp);
		}
		return this.success("用户授权成功");
	}
	
	/**
	 * 保存用户信息
	 */
	@Override
	public AjaxReturnMsg saveAddSysUser(SysUser suser) {
		if(!"".equals(suser.getUsername())||suser.getUsername()!=null){
			//查询管理机构用户表
			SysUser suser_ = webUserRoleMapper.getSysUserInfoById(suser.getUsername());
		}
		suser.setPassword(MD5Util.MD5Encode("123456"));
		suser.setEnabled("1");
		suser.setUsertype("2");
		//保存系统用户表
		int insertNum = webUserRoleMapper.saveSysUserInfo(suser);
		//写入用户用户组关联表
		SysUser suserGroup  = new SysUser();
		suserGroup.setUserid(suser.getUserid());
		suserGroup.setGroupid(suser.getGroupid());
		int insertGroupUser = webUserRoleMapper.saveSGroupUser(suserGroup);
		
		if(insertNum==1 && insertGroupUser == 1){
			return this.success("系统用户创建成功");
		}else {
			return this.error("系统用户创建失败");
		}
		
	}

	/**
	 * 保存系统机构信息
	 */
	@Override
	public AjaxReturnMsg saveAddSysAgency(SGroup sgroup) {
		if(!"".equals(sgroup.getName())||sgroup.getName()!=null){
			SGroup sgroup_ = webUserRoleMapper.getSysAgencyById(sgroup.getName());
			if(sgroup_!=null){
				return this.error("该系统机构名称已存在,请换一个试试");
			}
		}
		sgroup.setType("2");
		sgroup.setStatus("1");
		int insertNum = webUserRoleMapper.saveSysAgency(sgroup);
		if(insertNum ==1){
			return this.success("系统机构创建成功");
		}else {
			return this.error("系统机构创建失败");
		}
	}
	
	/**
	 * 删除系统机构信息
	 */
	@Override
	public AjaxReturnMsg deleteSysAgency(String groupid) {
		int deleteNum =0;
		List<SGroup> list = webUserRoleMapper.getSysAgencyForChildById(groupid);
		if(list.size()>0){
			return this.error("该机构下有子节点，不能删除!");
		}else {
			List<SysUser> listUser = webUserRoleMapper.getUserListByGroupid(groupid);
			if(listUser.size()>0){
				return this.error("该机构下有用户信息，请先删除机构下的用户!");
			}else {
				deleteNum =webUserRoleMapper.deleteSysAgencyInfoByGroupId(groupid);
			}
		}
		if(deleteNum==1){
			return this.success("系统机构删除成功!");
		}else {
			return this.error("系统机构删除失败!");
		}
	}
}
