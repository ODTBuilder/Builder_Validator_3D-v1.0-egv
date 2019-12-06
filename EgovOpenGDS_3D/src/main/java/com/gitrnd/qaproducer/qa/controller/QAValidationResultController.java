/**
 * 
 */
package com.gitrnd.qaproducer.qa.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gitrnd.qaproducer.controller.AbstractController;
import com.gitrnd.qaproducer.qa.domain.ServerSideVO;
import com.gitrnd.qaproducer.qa.service.ValidationResultService;
import com.gitrnd.qaproducer.user.domain.User;
import com.gitrnd.qaproducer.user.domain.User.EnUserType;

/**
 * @author GIT
 *
 */
@Controller
@RequestMapping("/result")
public class QAValidationResultController extends AbstractController {

	@Resource(name = "validationResultService")
	ValidationResultService validationResultService;

	/**
	 * @Description DataTable server side. 페이징 처리 기능, 검색 기능.
	 * @author HC.Kim
	 * @Date 2018. 8. 16. 오후 3:20:28
	 * @param request
	 * @param response
	 * @param loginUser
	 * @return JSONObject
	 */
	@RequestMapping(value = "/getValidationResult.ajax", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject GetValidationResult(HttpServletRequest request, HttpServletResponse response) {

		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());

		// 반환할 테이블 데이터
		JSONObject data = new JSONObject();
		// 세부 옵션
		HashMap<String, Object> detail = new HashMap<String, Object>();
		ServerSideVO serverSideVO = new ServerSideVO();
		serverSideVO.setParam(request);
		data = validationResultService.retrieveValidationResultByUidx(detail, serverSideVO, loginUser.getIdx());
		return data;
	}
}
