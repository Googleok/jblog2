package com.cafe24.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {

	@Autowired
	private SqlSession sqlSession;

	public List<PostVo> getPostList(Long categoryNo) {
		return sqlSession.selectList("post.getPostList", categoryNo);
	}
}
