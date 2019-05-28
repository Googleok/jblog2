package com.cafe24.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.jblog.service.UserService;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "/user/join";
	}

	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {

		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "/user/join";
		}
		userService.join(userVo);
		
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/joinsuccess")
	public String joinSuccess() {
		return "/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/user/login";
	}

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) {

//		model.addAttribute("authUser", userService.getUser(authUser.getNo()));
		
		return "/user/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute UserVo userVo) {
		boolean result = userService.update(userVo);
		if (result == false) {
			return "redirect:/user/update";
		}

		return "redirect:/user/updatesuccess";
	}

	@RequestMapping(value = "/updatesuccess")
	public String updateSuccess() {
		return "/user/updatesuccess";
	}

}