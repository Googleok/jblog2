package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.fileupload.service.FileuploadService;
import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}" )
public class BlogController {
	
	@Autowired
	private BlogService blogService;

	@Autowired
	private FileuploadService fileUploadService;

	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
			@PathVariable String id,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,
			ModelMap modelMap
			) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
		}else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		}else {
			categoryNo = 1L;
		}
		
		BlogVo blogVo = blogService.getBlogById(id);
		if(blogVo == null) {
			
			return "redirect:/main?result=fail";
		}
		System.out.println(blogVo);
//		List<CategoryVo> categoryList = blogService.getCategoryList(id);
//		List<PostVo> postList = blogService.getPostList(categoryNo);
		
		modelMap.addAttribute("blogVo", blogVo);
//		modelMap.addAttribute("categoryList", categoryList);
//		modelMap.addAttribute("postList", postList);
	
		return "/blog/blog-main";
	}
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public String basic(@PathVariable String id, Model model) {
		model.addAttribute("blogId", id);
		return "/blog/blog-admin-basic";
	}

	@RequestMapping(value = "/basic", method = RequestMethod.POST)
	public String basic(
			@RequestParam(value = "id", required = true, defaultValue = "") String id,
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "logo-file", required = false, defaultValue = "")MultipartFile multipartFile
			) {
		String logo = fileUploadService.restore(multipartFile);
		BlogVo vo = new BlogVo(id, title, logo);
		blogService.update(vo);
		return "redirect:/"+id;
	}
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@PathVariable String id) {
		
		return "/blog/blog-admin-category";
	}

	
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@PathVariable String id) {
		
		return "/blog/blog-admin-write";
	}
}
