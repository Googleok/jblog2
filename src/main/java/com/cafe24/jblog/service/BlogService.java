package com.cafe24.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogDao blogDao;

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private PostDao postDao;

	public BlogVo getBlogById(String id) {
		return blogDao.getBlogById(id);
	}

	public List<CategoryVo> getCategoryList(String id) {
		return categoryDao.getCategoryList(id);
	}

	public List<PostVo> getPostList(Long categoryNo) {
		return postDao.getPostList(categoryNo);
	}

	public Boolean update(BlogVo vo) {
		return blogDao.update(vo);
	}

	public boolean addCategory(String id, String name, String description) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("name", name);
		map.put("description", description);
		return categoryDao.insert(map);
	}


}
