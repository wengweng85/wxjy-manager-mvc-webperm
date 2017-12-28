package com.insigma.mvc.serviceimp.WEBPERM_001_001.codetype;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Element;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.insigma.common.util.EhCacheUtil;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.WEBPERM_001_001.codetype.WebCodeTypeMapper;
import com.insigma.mvc.model.CodeType;
import com.insigma.mvc.model.CodeValue;
import com.insigma.mvc.service.WEBPERM_001_001.codetype.WebCodeTypeService;

/**
 * ϵͳ����֮�������
 * @author wengsh
 *
 */
@Service
public class WebCodeTypeServiceImpl extends MvcHelper<CodeValue> implements WebCodeTypeService {

	@Resource
	private WebCodeTypeMapper webCodeTypeMapper;
	
	
	public List<CodeValue> queryCodeValueByCodeTypeAndParent(CodeValue codevalue){
		return webCodeTypeMapper.queryCodeValueByCodeTypeAndParent(codevalue);
	}
	
	public HashMap<String,List<CodeValue>> getCodeValueFromCache(CodeValue codevalue){
		Object object=EhCacheUtil.getParamFromCache(codevalue.getCode_type().toUpperCase());
		List<CodeValue> list = (List<CodeValue>) object;
		HashMap map=new HashMap();
		map.put("value", list);
		return map;
	}


	
	@Override
	public List<CodeValue> getCodeValueTree(CodeValue codevalue) {
		 return webCodeTypeMapper.getCodeValueTree(codevalue);
	}

	@Override
	public List<CodeType> getInitcodetypeList() {
		// TODO Auto-generated method stub
	   return webCodeTypeMapper.getInitcodetypeList();
	}

	@Override
	public List<CodeValue> getInitCodeValueList(CodeType codetype) {
		// TODO Auto-generated method stub
		return webCodeTypeMapper.getInitCodeValueList(codetype);
	}

	@Override
	public List<CodeType> getCodeTypeTreeData(CodeType codetype) {
		return webCodeTypeMapper.getCodeTypeTreeData(codetype);
	}

	@Override
	public List<CodeType> getCodeValueTreeData(CodeType codetype) {
		//���μ���
		if(codetype.getId()==null&& codetype.getCode_root_value()==null){
			return webCodeTypeMapper.getCodeValueByType(codetype);
		}else{
			if(codetype.getId()!=null){
				codetype.setCode_root_value(codetype.getId());
			}
			return webCodeTypeMapper.getCodeValueByTypeAndRoot(codetype);
		}
	}

	@Override
	public CodeType getCodeTypeInfo(String code_type) {
		return webCodeTypeMapper.getCodeTypeInfo(code_type);
	}

	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeType(CodeType codetype) {
		//����
		if(codetype.getIsupdate() .equals("1")){
			webCodeTypeMapper.updateCodeType(codetype);
			return this.success_obj("�޸ĳɹ�",codetype);
		}
		//����
		else{
			//�ж�codetype�Ƿ��ظ�
			if(webCodeTypeMapper.getCodeTypeInfo(codetype.getCode_type())!=null){
                  return this.error("�Ѿ����ڴ�������Ϊ"+codetype.getCode_type()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			webCodeTypeMapper.addCodeType(codetype);
			return this.success_obj("�����ɹ�",codetype);
		}
	}
	
	
	@Override
	public CodeValue getCodeTypeDetailInfo(String code_seq) {
		return webCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
	}
	
	@Override
	public AjaxReturnMsg<String> saveOrUpdateCodeTypeDetail(CodeValue codevalue) {
		//����
		if(codevalue.getCode_seq() .equals("")){
			//�ж�codetype�Ƿ��ظ�
			if(webCodeTypeMapper.getCodeTypeDetailByValue(codevalue)!=null){
                  return this.error("�Ѿ����ڴ���ֵΪ"+codevalue.getCode_value()+"�Ĵ���,�����ظ�,��ȷ������");				
			}
			
			webCodeTypeMapper.addCodeTypeDetail(codevalue);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success_obj("�����ɹ�",codevalue);
		}
		//�޸�
		else{
			webCodeTypeMapper.updateCodeTypeDetail(codevalue);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success_obj("�޸ĳɹ�",codevalue);
		}
	}
	
	
	@Override
	@Transactional
	public AjaxReturnMsg<String> deleteCodeType(String code_type){
		webCodeTypeMapper.deleteCodeTypeByType(code_type);
		webCodeTypeMapper.deleteCodeValueByType(code_type);
		//���´��뻺��
		setSelectCacheData(code_type);
		return this.success("ɾ������"+code_type+"�ɹ�");
	}
	
	
	@Override
	public AjaxReturnMsg<String> deleteCodeValue(String code_seq){
		CodeValue codevalue=webCodeTypeMapper.getCodeTypeDetailInfo(code_seq);
		if(codevalue!=null){
			webCodeTypeMapper.deleteCodeValueBySeq(code_seq);
			//���´��뻺��
			setSelectCacheData(codevalue.getCode_type());
			return this.success("ɾ��"+codevalue.getCode_type()+"��"+codevalue.getCode_name()+"����ɹ�");
		}else{
			return this.error("ɾ��ʧ��,");
		}
		
	}
	
	/**
	 * �޸Ĵ��뻺��
	 */
	@Override
	public  void setSelectCacheData(String code_type){
		CodeType codetype=new CodeType();
		codetype.setCode_type(code_type);
		List<CodeValue> list_code_value =getInitCodeValueList(codetype);
		if (list_code_value.size() > 0) {
			try{
				//������μӼ��ص�ehcache������
				EhCacheUtil.getManager().getCache("webcache").put(new Element(code_type,list_code_value));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public CodeValue getCodeValueByValue(CodeValue codevalue) {
		return webCodeTypeMapper.getCodeValueByValue(codevalue);
	}
	
}