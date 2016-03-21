package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String displayForm() {
		return "file_upload_form";
	}

    @RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,Model model) {
		if (error != null) {
			model.addAttribute("error", "Invalid username and password!");
		}else if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}
		return "login";
	}
	
    @RequestMapping(value={"/ui","/ui/{path}"})
    String managerPage() {
        return "managerpage";
    }
    
    @RequestMapping(value={"/admin","/admin/{path}"})
    String adminPage() {
        return "adminpage";
    }
    
    @RequestMapping(value={"/usr","/usr/{path}"})
    String userPage() {
        return "userpage";
    }
    
    @RequestMapping(value={"/srmgr","/srmgr/{path}"})
    String srManagerPage() {
        return "srmgrpage";
    }
   
    @RequestMapping(value={"/"})
    String userPage1() {
        return "managerpage";
    }
}
