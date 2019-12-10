package com.gitrnd.qaproducer.controller;

import java.net.MalformedURLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.gitrnd.gdsbuilder.geoserver.DTGeoserverManager;
import com.gitrnd.qaproducer.geoserver.service.GeoserverService;
import com.gitrnd.qaproducer.preset.domain.Preset;
import com.gitrnd.qaproducer.preset.service.PresetService;
import com.gitrnd.qaproducer.user.domain.User;
import com.gitrnd.qaproducer.user.domain.User.EnUserType;

@Controller
public class MainController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@Resource(name = "presetService")
	PresetService presetService;
	
	@Resource(name = "geoService")
	GeoserverService geoService;

	@RequestMapping(value = "/{locale:en|ko}/locale.do", method = RequestMethod.GET)
	public String localeMainView(HttpServletRequest request) {
		LOGGER.info("access: /locale.do");
		String redir = "redirect:/main.do";
		return redir;
	}

	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public ModelAndView mainView(HttpServletRequest request) {
		
//	DTGeoserverManager geoserverManager = null;
//	try {
//		geoserverManager = new DTGeoserverManager("http://175.116.181.32:9999/geoserver", "admin", "geoserver");
//	} catch (MalformedURLException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//		
//		try {
//			geoService.geolayerTo3DTiles(geoserverManager, "node", "nodetest", "gis_osm_buildings_3052", "admin", 50);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		
		LOGGER.info("access: /main.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		mav.setViewName("/main/main");
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		return mav;
	}

	@RequestMapping(value = "/map.do", method = RequestMethod.GET)
	public ModelAndView mapView(HttpServletRequest request) {
		LOGGER.info("access: /map.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		mav.setViewName("/map/map3d");
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		return mav;
	}

	@RequestMapping(value = "/setting.do", method = RequestMethod.GET)
	public ModelAndView settingView(HttpServletRequest request) {
		LOGGER.info("access: /setting.do");
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		LOGGER.info("login user: {}.", loginUser);
		ModelAndView mav = new ModelAndView();
		String pid = request.getParameter("pid");
		if (pid != null) {
			Preset ps = new Preset();
			ps.setPid(Integer.parseInt(pid));
			ps.setUidx(loginUser.getIdx());
			ps = presetService.retrievePresetByIdAndUidx(ps);
			System.out.println(ps);
			mav.addObject("pid", ps.getPid());
			mav.addObject("title", ps.getTitle());
			mav.addObject("name", ps.getName());
			mav.addObject("layer", ps.getLayerDef());
			mav.addObject("option", ps.getOptionDef());
		}
		mav.addObject("username", loginUser.getUid());
		mav.addObject("fname", loginUser.getFname());
		mav.addObject("lname", loginUser.getLname());
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		mav.setViewName("/user/setting");
		return mav;
	}

	@RequestMapping(value = "/settinglist.do", method = RequestMethod.GET)
	public ModelAndView settingListView(HttpServletRequest request) {
		LOGGER.info("access: /settinglist.do");

		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		LOGGER.info("login user: {}.", loginUser);
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		mav.setViewName("/user/settinglist");
		return mav;
	}

	@RequestMapping(value = "/list.do", method = RequestMethod.GET)
	public ModelAndView errListView(HttpServletRequest request) {
		LOGGER.info("access: /list.do");
		ModelAndView mav = new ModelAndView();
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		if (loginUser != null) {
			mav.addObject("username", loginUser.getUid());
			mav.addObject("fname", loginUser.getFname());
			mav.addObject("lname", loginUser.getLname());
		}
		LOGGER.info("login user: {}.", loginUser);
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		// LinkedList<ValidationResult> list =
		// validationResultService.retrieveValidationResultByUidx(loginUser.getIdx());
		// mav.addObject("list", list);
		mav.setViewName("/user/list");
		return mav;
	}

	@RequestMapping(value = "/validation.do", method = RequestMethod.GET)
	public ModelAndView validationView(HttpServletRequest request) {
		LOGGER.info("access: /validation.do");
		User loginUser = (User) getSession(request, EnUserType.GENERAL.getTypeName());
		LOGGER.info("login user: {}.", loginUser);
		ModelAndView mav = new ModelAndView();
		mav.addObject("username", loginUser.getUid());
		mav.addObject("fname", loginUser.getFname());
		mav.addObject("lname", loginUser.getLname());
		String header = request.getHeader("User-Agent");
		if (header != null) {
			if (header.indexOf("Trident") > -1) {
				mav.addObject("browser", "MSIE");
			}
		}
		List<Preset> presets = presetService.retrievePresetByUidx(loginUser.getIdx());
		mav.addObject("presets", presets);

		mav.setViewName("/user/validation");
		return mav;
	}

}
