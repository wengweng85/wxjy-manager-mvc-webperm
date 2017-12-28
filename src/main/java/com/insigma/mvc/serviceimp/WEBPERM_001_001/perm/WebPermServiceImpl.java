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
 * ������֮Ȩ�޹���service impl 
 * @author wengsh
 *
 */

@Service
public class WebPermServiceImpl extends MvcHelper implements WebPermService {

	@Resource
	private WebPermMapper webPermMapper;
	
	
	/**
	 * ��ȡȨ������
	 */
	@Override
	public List<SPermission> getPermTreeList() {
		// TODO Auto-generated method stub
		return  webPermMapper.getPermTreeList();
	}

	/**
	 * ͨ��Ȩ��id��ȡȨ������
	 */
	@Override
	public AjaxReturnMsg getPermDataById(String id) {
		return this.success(webPermMapper.getPermDataById(id));
	}

	
    /**
     * ��������Ȩ������
     */
	@Override
	@Transactional
	public AjaxReturnMsg saveOrUpdatePermData(SPermission spermission) {
		   SPermission isPermsionCodeexist=webPermMapper.isPermCodeExist(spermission);
		   if(isPermsionCodeexist!=null){
			   return this.error("��Ȩ��"+spermission.getCode()+"����Ѿ�����,����������һ���µ�Ȩ�ޱ��");
		   }
		   
		   SPermission isPermsionUrlexist=webPermMapper.isPermUrlExist(spermission);
		   if(isPermsionUrlexist!=null){
			   return this.error("��Ȩ��"+spermission.getUrl()+"·����ַ�Ѿ�����,����������һ���µ�·����ַ");
		   }
		   
		  //�ж��Ƿ���²���
		  if(StringUtils.isNullOrEmpty(spermission.getPermissionid())){
				 int insertnum=webPermMapper.savePermissionData(spermission);
				 if(insertnum==1){
					 return this.success("�����ɹ�");
				 }else{
					 return this.error("����ʧ��");
				 }
		 }else{
				 int updatenum=webPermMapper.updatePermissionData(spermission);
				 if(updatenum==1){
					 return this.success("���³ɹ�");
				 }else{
					 return this.error("����ʧ��");
				 }
		  }
	}

	/**
	 * ͨ�����ڵ��ȡȨ���ӽڵ�����
	 */
	@Override
	public AjaxReturnMsg getPermListDataByParentid(String parentid) {
		return this.success( webPermMapper.getPermListDataByParentid(parentid));
	}

	
	/**
	 * ͨ��Ȩ��idɾ��Ȩ���������
	 */
	@Override
	@Transactional
	public AjaxReturnMsg deletePermDataById(String id) {
		if(webPermMapper.getPermListDataByParentid(id).size()>0){
			return this.error("��ǰȨ�޻�������Ȩ������,����ɾ����Ȩ������");
		}else{
			int deletenum=webPermMapper.deletePermDataById(id);
			if(deletenum==1){
				return this.success("ɾ���ɹ�");
			}else{
				return this.success("ɾ��ʧ��");
			}
			
		}
	}

	
	/**
	 * �ڵ��ƶ�
	 */
	@Override
	public AjaxReturnMsg<String> moveNode(String id) {
		String[]  ids=id.split("_");
		//Ҫ�ƶ��Ľڵ�id
		String movetreenodeid=ids[0];
		//Ŀ��ڵ�id
		String targettreenodeid=ids[1];
		//Ҫ�ƶ��Ľڵ�
		SPermission moveSpermission=webPermMapper.getPermDataById(movetreenodeid);
		//Ŀ��ڵ�
		SPermission targetSpermission=webPermMapper.getPermDataById(targettreenodeid);
		
		//ͬ���ƶ�,ֻ�޸������
		if(moveSpermission.getParentid().equals(targetSpermission.getParentid())){
			moveSpermission.setSortnum(targetSpermission.getSortnum());
		}
		//��ͬ���ƶ����޸ĵ�ǰ�ڵ�ĸ��ڵ�ΪĿ��ڵ�id
		else{
			moveSpermission.setParentid(targetSpermission.getPermissionid());
		}
		webPermMapper.updatePermissionData(moveSpermission);
		return this.success("�ɹ�");
	}

	/**
	 * ����ɾ��Ȩ��
	 */
	@Override
	public AjaxReturnMsg batdeletePermData(SPermission spermission) {
		// TODO Auto-generated method stub
		String[] selectnodes= spermission.getSelectnodes().split(",");
		webPermMapper.batdeletePermData(selectnodes);
		return this.success("�ɹ�");
	}
}
