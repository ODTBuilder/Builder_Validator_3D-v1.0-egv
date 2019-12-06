/**
 * 
 */
package com.gitrnd.qaproducer.geogig.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gitrnd.gdsbuilder.geogig.type.GeogigBranch;
import com.gitrnd.gdsbuilder.geogig.type.GeogigCheckout;
import com.gitrnd.gdsbuilder.geogig.type.GeogigMerge;
import com.gitrnd.gdsbuilder.geogig.type.GeogigTransaction;
import com.gitrnd.gdsbuilder.geoserver.DTGeoserverManager;
import com.gitrnd.qaproducer.controller.AbstractController;
import com.gitrnd.qaproducer.geogig.service.GeogigBranchService;
import com.gitrnd.qaproducer.geogig.service.GeogigRepositoryService;
import com.gitrnd.qaproducer.geogig.service.GeogigTransactionService;
import com.gitrnd.qaproducer.user.domain.User;
import com.gitrnd.qaproducer.user.domain.User.EnUserType;

@Controller
@RequestMapping("/geogig")
public class GeogigBranchController extends AbstractController {

	@Autowired
	@Qualifier("transactionService")
	GeogigTransactionService transactionService;

	@Autowired
	@Qualifier("reposService")
	GeogigRepositoryService reposService;

	@Autowired
	@Qualifier("branchService")
	GeogigBranchService branchService;

	@RequestMapping(value = "/checkoutBranch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigCheckout checkoutBranch(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		GeogigTransaction transaction = transactionService.beginTransaction(geoserverManager, repoName);
		String transactionId = transaction.getTransaction().getId();
		return branchService.checkoutBranch(geoserverManager, repoName, transactionId, branchName);
	}

	@RequestMapping(value = "/statusBranch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONObject statusBranch(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName,
			@RequestParam(value = "transactionId", required = false) String transactionId) {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return branchService.statusBranch(geoserverManager, serverName, repoName, transactionId, branchName);
	}

	@RequestMapping(value = "/createBranch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigBranch createBranch(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName,
			@RequestParam(value = "source", required = false) String source) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return branchService.createBranch(geoserverManager, repoName, branchName, source);
	}

	@RequestMapping(value = "/branchList.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigBranch branchList(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return branchService.listBranch(geoserverManager, repoName);
	}

	@RequestMapping(value = "/mergeBranch.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GeogigMerge mergeBranch(HttpServletRequest request,
			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "branchName", required = false) String branchName,
			@RequestParam(value = "transactionId", required = false) String transactionId) throws JAXBException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		return branchService.mergeBranch(geoserverManager, repoName, transactionId, branchName);
	}

	@RequestMapping(value = "/resolveConflict.do", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<GeogigCheckout> resolveConflict(HttpServletRequest request,

			@RequestParam(value = "serverName", required = false) String serverName,
			@RequestParam(value = "repoName", required = false) String repoName,
			@RequestParam(value = "features", required = false) String features,
			@RequestParam(value = "transactionId", required = false) String transactionId)
			throws JAXBException, ParseException {
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
		JSONParser parser = new JSONParser();
		JSONArray featuresArr = (JSONArray) parser.parse(features);
		return branchService.resolveConflict(geoserverManager, repoName, transactionId, featuresArr);
	}

}
