package com.insigma.mvc.serviceimp.WEBPERM_001_001.perm;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.WEBPERM_001_001.perm.WebPermMapper;
import com.insigma.mvc.model.SPermission;
import com.insigma.mvc.service.WEBPERM_001_001.perm.WebPermService;
import com.mysql.jdbc.StringUtils;


/**
 * 管理功能之权限管理service impl 
 * @author wengsh
 *
 */

@Service
public class WebPermServiceImpl extends MvcHelper implements WebPermService {

	@Resource
	private WebPermMapper webPermMapper;
	
	
	/**
	 * 获取权限数据
	 */
	@Override
	public List<SPermission> getPermTreeList() {
		// TODO Auto-generated method stub
		return  webPermMapper.getPermTreeList();
	}

	/**
	 * 通过权限id获取权限数据
	 */
	@Override
	public AjaxReturnMsg getPermDataById(String id) {
		return this.success(webPermMapper.getPermDataById(id));
	}

	
    /**
     * 保存或更新权限数据
     */
	@Override
	@Transactional
	public AjaxReturnMsg saveOrUpdatePermData(SPermission spermission) {
		   SPermission isPermsionCodeexist=webPermMapper.isPermCodeExist(spermission);
		   if(isPermsionCodeexist!=null){
			   return this.error("此权限"+spermission.getCode()+"编号已经存在,请重新输入一个新的权限编号");
		   }
		   
		   SPermission isPermsionUrlexist=webPermMapper.isPermUrlExist(spermission);
		   if(isPermsionUrlexist!=null){
			   return this.error("此权限"+spermission.getUrl()+"路径地址已经存在,请重新输入一个新的路径地址");
		   }
		   
		  //判断是否更新操作
		  if(StringUtils.isNullOrEmpty(spermission.getPermissionid())){
				 int insertnum=webPermMapper.savePermissionData(spermission);
				 if(insertnum==1){
					 return this.success("新增成功");
				 }else{
					 return this.error("新增失败");
				 }
		 }else{
				 int updatenum=webPermMapper.updatePermissionData(spermission);
				 if(updatenum==1){
					 return this.success("更新成功");
				 }else{
					 return this.error("更新失败");
				 }
		  }
	}

	/**
	 * 通过父节点获取权限子节点数据
	 */
	@Override
	public AjaxReturnMsg getPermListDataByParentid(String parentid) {
		return this.success( webPermMapper.getPermListDataByParentid(parentid));
	}

	
	/**
	 * 通过权限id删除权限相关数据
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deletePermDataById(String id) {
		if(webPermMapper.getPermListDataByParentid(id).size()>0){
			return this.error("当前权限还存在子权限数据,请先删除子权限数据");
		}else{
			int deletenum=webPermMapper.deletePermDataById(id);
			if(deletenum==1){
				return this.success("删除成功");
			}else{
				return this.success("删除失败");
			}
			
		}
	}

	
	/**
	 * 节点移动
	 */
	@Override
	public AjaxReturnMsg<String> moveNode(String id) {
		String[]  ids=id.split("_");
		//要移动的节点id
		String movetreenodeid=ids[0];
		//目标节点id
		String targettreenodeid=ids[1];
		//要移动的节点
		SPermission moveSpermission=webPermMapper.getPermDataById(movetreenodeid);
		//目标节点
		SPermission targetSpermission=webPermMapper.getPermDataById(targettreenodeid);
		
		//同级移动,只修改排序号
		if(moveSpermission.getParentid().equals(targetSpermission.getParentid())){
			moveSpermission.setSortnum(targetSpermission.getSortnum());
		}
		//非同级移动、修改当前节点的父节点为目标节点id
		else{
			moveSpermission.setParentid(targetSpermission.getPermissionid());
		}
		webPermMapper.updatePermissionData(moveSpermission);
		return this.success("成功");
	}

	/**
	 * 批量删除权限
	 */
	@Override
	public AjaxReturnMsg batdeletePermData(SPermission spermission) {
		// TODO Auto-generated method stub
		String[] selectnodes= spermission.getSelectnodes().split(",");
		webPermMapper.batdeletePermData(selectnodes);
		return this.success("成功");
	}
}
