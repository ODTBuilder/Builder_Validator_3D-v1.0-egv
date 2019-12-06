/**
 * 
 */
package com.gitrnd.qaproducer.qa.controller;

import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gitrnd.gdsbuilder.geoserver.DTGeoserverManager;
import com.gitrnd.qaproducer.controller.AbstractController;
import com.gitrnd.qaproducer.filestatus.domain.FileStatus;
import com.gitrnd.qaproducer.filestatus.service.FileStatusService;
import com.gitrnd.qaproducer.preset.domain.Preset;
import com.gitrnd.qaproducer.preset.service.PresetService;
import com.gitrnd.qaproducer.qa.domain.QACategory;
import com.gitrnd.qaproducer.qa.domain.QAProgress;
import com.gitrnd.qaproducer.qa.service.QACategoryService;
import com.gitrnd.qaproducer.qa.service.QAProgressService;
import com.gitrnd.qaproducer.qa.service.QAWebService;
import com.gitrnd.qaproducer.user.domain.User;
import com.gitrnd.qaproducer.user.domain.User.EnUserType;

/**
 * Geoserver 검수 요청 Controller
 * 
 * @author DY.Oh
 *
 */
@Controller
@RequestMapping("/web")
public class QAWebController extends AbstractController {

	@Resource(name = "presetService")
	PresetService presetService;

	protected static int FILEUPLOAD = 1;
	protected static String GEOSERVERQA = "GEOSERVER";

	@Resource(name = "fileStatusService")
	private FileStatusService fileStatusService;

	@Resource(name = "qaProgressService")
	private QAProgressService qapgService;

	@Resource(name = "qaCategoryService")
	private QACategoryService qaCatService;

	@Resource(name = "qaWebService")
	private QAWebService qaService;

	@RequestMapping(value = "/validate.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject validate(HttpServletRequest request, @RequestBody String jsonStr) throws Exception {


		boolean isSuccess = true;
		
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());

		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(jsonStr);

		JSONObject geoserver = (JSONObject) jsonObject.get("geoserver");
		String crs = (String) jsonObject.get("crs");
		String prid = (String) jsonObject.get("prid");

		String qaVer = (String) jsonObject.get("qaVer");
		String qaType = (String) jsonObject.get("qaType");

		Preset prst = null;
		if (prid.equals("nonset")) {
			if (qaVer.equals("qa1")) {
				switch (qaType) {
				case "nm1":
					break;
				case "nm5":
					prst = presetService.retrievePridByBasePreset(1);
					break;
				case "nm25":
					break;
				case "ug1":
					break;
				case "ug5":
					prst = presetService.retrievePridByBasePreset(3);
					break;
				case "ug25":
					break;
				case "fr1":
					break;
				case "fr5":
					prst = presetService.retrievePridByBasePreset(5);
					break;
				case "fr25":
					break;
				default:
					break;
				}
			} else if (qaVer.equals("qa2")) {
				switch (qaType) {
				case "nm1":
					break;
				case "nm5":
					prst = presetService.retrievePridByBasePreset(2);
					break;
				case "nm25":
					break;
				case "ug1":
					break;
				case "ug5":
					prst = presetService.retrievePridByBasePreset(4);
					break;
				case "ug25":
					break;
				case "fr1":
					break;
				case "fr5":
					prst = presetService.retrievePridByBasePreset(5);
					break;
				case "fr25":
					break;
				default:
					break;
				}
			}
		} else {
			prst = presetService.retrieveCatByPreset(Integer.parseInt(prid));
		}

		// int nowCat = prst.getCat();
		// String nowAuthString = "";
		// if (nowCat == 1 || nowCat == 2) {
		// nowAuthString = "DIGITAL";
		// } else if (nowCat == 3 || nowCat == 4) {
		// nowAuthString = "UNDERGROUND";
		// } else if (nowCat == 5) {
		// nowAuthString = "FOREST";
		// }
		// GrantedAuthority digital = new SimpleGrantedAuthority("DIGITAL");
		// GrantedAuthority under = new SimpleGrantedAuthority("UNDERGROUND");
		// GrantedAuthority forest = new SimpleGrantedAuthority("FOREST");
		// GrantedAuthority allpass = new SimpleGrantedAuthority("ALLPASS");
		// GrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
		//
		// boolean isAuthorized = false;
		//
		// if (nowAuthString.equals("DIGITAL") &&
		// (loginUser.getAuthorities().contains(digital)
		// || loginUser.getAuthorities().contains(allpass) ||
		// loginUser.getAuthorities().contains(admin))) {
		// isAuthorized = true;
		// } else if (nowAuthString.equals("UNDERGROUND") &&
		// (loginUser.getAuthorities().contains(under)
		// || loginUser.getAuthorities().contains(allpass) ||
		// loginUser.getAuthorities().contains(admin))) {
		// isAuthorized = true;
		// } else if (nowAuthString.equals("FOREST") &&
		// (loginUser.getAuthorities().contains(forest)
		// || loginUser.getAuthorities().contains(allpass) ||
		// loginUser.getAuthorities().contains(admin))) {
		// isAuthorized = true;
		// }
		//
		// if (isAuthorized) {
		// 옵션또는 파일이 제대로 넘어오지 않았을때 강제로 예외발생
		if (qaVer == null || qaType == null || prid == null || prst == null) {
			throw new Exception("인자가 부족합니다. 다시 요청해주세요.");
		} else {
			String serverName = (String) geoserver.get("servername");
			JSONObject layers = (JSONObject) geoserver.get("layers");
			DTGeoserverManager geoserverManager = super.getGeoserverManagerToSession(request, loginUser, serverName);
			String serverURL = geoserverManager.getRestURL();

			FileStatus fileStatus = new FileStatus();
			String fname = "";
			Set set = layers.keySet();
			Iterator iter = set.iterator();
			int i = 0;
			while (iter.hasNext()) {
				String wsName = (String) iter.next();
				if (i == 0) {
					fname += wsName;
				}
			}
			int size = set.size();
			if (size > 2) {
				fname += "_and_others";
			}
			fileStatus.setFname(fname);
			fileStatus.setStatus(1);
			fileStatus.setUidx(loginUser.getIdx());
			fileStatusService.createFileStatus(fileStatus);
			Long catetoryIdx = (long) prst.getCat();
			int cIdx = catetoryIdx.intValue();
			QACategory qaCat = qaCatService.retrieveQACategoryByIdx(cIdx);
			String qaTitle = qaCat.getTitle();

			QAProgress progress = new QAProgress();
			progress.setuIdx(loginUser.getIdx());
			progress.setQaType(qaTitle);
			progress.setPrid(prst.getPid());

			progress.setQaState(FILEUPLOAD);
			progress.setOriginName(fileStatus.getFname());
			progress.setfIdx(fileStatus.getFid());
			progress.setuIdx(loginUser.getIdx());
			progress.setQaType(qaTitle);
			progress.setPrid(prst.getPid());
			progress.setFileType(GEOSERVERQA);
			qapgService.insertQARequest(progress);

			int pid = progress.getpIdx();
			JSONObject json = new JSONObject();
			json.put("serverURL", serverURL);
			json.put("layersMap", layers);
			json.put("category", prst.getCat());
			json.put("qaVer", qaVer);
			json.put("qaType", qaType);
			json.put("fid", fileStatus.getFid());
			json.put("pid", pid);
			json.put("prid", prid);
			json.put("crs", crs);
			json.put("type", "web");

			try {
				qaService.validate(json);
			} catch (Throwable e) {
				isSuccess = false;
				e.printStackTrace();
			}
		}
		JSONObject json = new JSONObject();
		json.put("success", isSuccess);
		return json;
	}
}
