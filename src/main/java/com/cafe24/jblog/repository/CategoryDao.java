package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;
	
	public List<CategoryVo> getCategoryList(String id) {
		return sqlSession.selectList("category.getCategoryList", id);
	}
}
