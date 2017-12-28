package com.insigma.mvc.serviceimp.WEBPERM_001_001.param;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.insigma.dto.AjaxReturnMsg;
import com.insigma.mvc.MvcHelper;
import com.insigma.mvc.dao.WEBPERM_001_001.param.WebParamMapper;
import com.insigma.mvc.model.SysParam;
import com.insigma.mvc.service.WEBPERM_001_001.param.WebParamService;

/**
 * ϵͳ����service
 * @author wengsh
 *
 */
@Service
public class WebParamServiceImpl extends MvcHelper implements WebParamService {

	@Resource
	private WebParamMapper webParamMapper;

	@Override
	public List<SysParam> getList(){
		return webParamMapper.getList();
	}
	
	
	@Override
	public HashMap<String, Object> getPageList(SysParam sParam) {
		PageHelper.offsetPage(sParam.getOffset(), sParam.getLimit());
		List<SysParam> list=webParamMapper.getPageList(sParam);
		PageInfo<SysParam> pageinfo = new PageInfo<SysParam>(list);
		return this.success_hashmap_response(pageinfo);
	}


	@Override
	public AjaxReturnMsg<String> updateparam(SysParam sParam) {
		SysParam issparamexist=webParamMapper.getParamByid(sParam.getParamid());
		//����
		if(issparamexist!=null){
			webParamMapper.updateparam(sParam);
			return this.success("���³ɹ�");
		}
	    //����
		{
			int addnum=webParamMapper.addparam(sParam);
			return this.success("�����ɹ�");
		}
	}


	@Override
	public AjaxReturnMsg<String> deleteparambyid(String paramid) {
		// TODO Auto-generated method stub
		int deletenum=webParamMapper.deleteparambyid(paramid);
		return this.success("ɾ���ɹ�");
	}

}