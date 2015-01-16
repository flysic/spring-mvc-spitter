package com.springinaction.txexample;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	@Autowired
	private SpitterService spitterService;
	public static final int DEFAULT_SPITTLES_PER_PAGE = 25;
	private int spittlesPerPage = DEFAULT_SPITTLES_PER_PAGE;
	
	public int getSpittlesPerPage() {
		return spittlesPerPage;
	}

	public void setSpittlesPerPage(int spittlesPerPage) {
		this.spittlesPerPage = spittlesPerPage;
	}
	
	@RequestMapping(value={"/", "/home"}, method=RequestMethod.GET)
	public String showHomePage(Map<String, Object> model) {
		model.put("spittles", spitterService.getRecentSpittles(spittlesPerPage));
		return "home";
	}
	
	
}
