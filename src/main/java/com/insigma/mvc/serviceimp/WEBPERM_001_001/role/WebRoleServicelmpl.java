package com.insigma.mvc.serviceimp.WEBPERM_001_001.role;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.WEBPERM_001_001.role.WebRoleMapper;
import com.insigma.mvc.model.SRole;
import com.insigma.mvc.service.WEBPERM_001_001.role.WebRoleService;
import com.mysql.jdbc.StringUtils;


/**
 * 管理功能之角色管理service impl 
 * @author wengsh
 *
 */

@Service
public class WebRoleServicelmpl extends MvcHelper  implements WebRoleService {

	@Resource
	private WebRoleMapper webRoleMapper;

	/**
	 * 获取所有角色数据
	 */
	@Override
	public HashMap<String,Object> getAllRoleList( SRole srole) {
		PageHelper.offsetPage(srole.getOffset(), srole.getLimit());
		List<SRole> list=webRoleMapper.getAllRoleList();
		PageInfo<SRole> pageinfo = new PageInfo<SRole>(list);
		return this.success_hashmap_response (pageinfo);
	}
 
	/**
	 * 通过角色id获取角色数据
	 */
	@Override
	public AjaxReturnMsg getRoleDataById(String id) {
		return this.success(webRoleMapper.getRoleDataById(id));
	}

	/**
	 * 保存或更新角色数据
	 */
	@Override
	@Transactional
	public AjaxReturnMsg saveOrUpdateRoleData(SRole srole) {
		SRole ispermsionexist=webRoleMapper.isRoleCodeExist(srole);
		if(ispermsionexist!=null){
			   return this.error("此角色"+srole.getCode()+"编号已经存在,请重新输入一个新的角色编号");
		}else{
			//判断是否更新操作
			if(StringUtils.isNullOrEmpty(srole.getRoleid())){
				webRoleMapper.saveRoleData(srole);
				 return this.success("新增成功");
			}else{
				webRoleMapper.updateRoleData(srole);
				return this.success("更新成功");
			}
		}
	}

	/**
	 * 通过角色id删除角色数据
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deleteRoleDataById(String id) {
		if(webRoleMapper.isRoleUsedbyUser(id)!=null){
			return this.error("当前角色已经被用户绑定使用，不允许删除,请确认");
		}else{
			webRoleMapper.deleteRoleDataById(id);
			webRoleMapper.deleteRolePermbyRoleid(id);
			return this.success("删除成功");
		}
	}
	

	/**
	 * 获取角色权限树
	 */
	@Override
	public List<SRole> getRolePermTreeData(String roleid) {
		return webRoleMapper.getRolePermTreeData(roleid);
	}

	/**
	 * 保存角色权限树数据
	 */
	@Transactional
	@Override
	public AjaxReturnMsg saveRolePermData(SRole srole) {
		//先删除角色对应历史数据
		webRoleMapper.deleteRolePermbyRoleid(srole.getRoleid());
		String[] selectnodes= srole.getSelectnodes().split(",");
		for(int i=0;i<selectnodes.length;i++){
			SRole temp=new SRole();
			temp.setRoleid(srole.getRoleid());
			temp.setPermissionid(selectnodes[i]);
			webRoleMapper.saveRolePermData(temp);
		}
		return this.success("角色增加权限成功");
	}
}
