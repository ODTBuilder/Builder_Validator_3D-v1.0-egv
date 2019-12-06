package com.gitrnd.qaproducer.qa.repository;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.json.simple.JSONArray;
import org.springframework.stereotype.Repository;

import com.gitrnd.qaproducer.qa.domain.ValidationResult;
import com.gitrnd.qaproducer.qa.mapper.ValidationResultMapper;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("validationResultRepository")
public class ValidationResultRepository extends EgovAbstractMapper{

	@Resource(name = "validationResultMapper")
	private ValidationResultMapper validationResultMapper;

	public int countValidationResultByUidx(int idx) {
		return validationResultMapper.countValidationResultByUidx(idx);
	}

	public JSONArray retrieveValidationResultByUidx(int draw, int start, int length, int order_idx, String order_direct,
			int idx) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start", start);
		params.put("length", length);
		params.put("order_idx", order_idx);
		params.put("order_direct", order_direct);
		params.put("idx", idx);

		return validationResultMapper.retrieveValidationResultByUidx(params);
	}

	public ValidationResult retrieveValidationResultByPidx(int idx) {
		return validationResultMapper.retrieveValidationResultByPidx(idx);
	}

	public boolean deleteValidationResult(ValidationResult result) throws RuntimeException {
		boolean flag = false;

		int response = validationResultMapper.deleteValidationResult(result);
		if (response > 0) {
			flag = true;
		}
		return flag;
	}
}
