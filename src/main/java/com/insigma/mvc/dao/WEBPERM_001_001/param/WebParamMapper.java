package com.insigma.mvc.dao.WEBPERM_001_001.param;

import java.util.List;

import com.insigma.mvc.model.SysParam;



/**
 * wengsh
 * @author wengsh
 *
 */
public interface WebParamMapper {
	
	List<SysParam> getList();
	List<SysParam> getPageList(SysParam sParam);
	SysParam getParamByid(String paramid);
	int addparam(SysParam sParam);
	int updateparam(SysParam sParam);
	int deleteparambyid(String paramid);
}
