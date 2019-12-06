package com.gitrnd.qaproducer.qa.mapper;

import com.gitrnd.qaproducer.qa.domain.QACategory;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("qaCategoryMapper")
public interface QACategoryMapper {

	public QACategory retrieveQACategoryByIdx(int idx);

}
