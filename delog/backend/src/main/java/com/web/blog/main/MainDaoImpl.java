package com.web.blog.main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.UserAndCount;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.FollowPost;

@Repository
public class MainDaoImpl implements MainDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<FollowPost> getfollowingsRecentPost(int uid) throws SQLException {
		//System.out.println("MainDaoImpl - getfollowingsRecentPost] " );
		List<FollowPost> list = new ArrayList<>();
		list = sqlSession.selectList("MAIN.selectFollowingsRecentPost", uid);
		//System.out.println("MainDaoImpl - getfollowingsRecentPost] list : " + list );
		return list;
	}

	@Override
	public List<UserAndCount> getFolloweringUidsOrderByFollowerCount() throws SQLException {
		//System.out.println("MainDaoImpl - getFolloweringUidsOrderByFollowerCount] " );
		List<UserAndCount> list = new ArrayList<>();
		list = sqlSession.selectList("MAIN.selectFolloweringUidsOrderByFollowerCount");
		//System.out.println("MainDaoImpl - getFolloweringUidsOrderByFollowerCount] list : " + list );
		return list;
	}

	@Override
	public List<UserAndCount> getUidsOrderByManyPostCount() throws SQLException {
		//System.out.println("MainDaoImpl - getUidsOrderByManyPostCount] " );
		List<UserAndCount> list = new ArrayList<>();
		list = sqlSession.selectList("MAIN.selectUidsOrderByManyPostCount");
		//System.out.println("MainDaoImpl - getUidsOrderByManyPostCount] list : " + list );
		return list;
	}

	@Override
	public List<PostUser> getPostsOrdeyByManyHit() throws SQLException {
		//System.out.println("MainDaoImpl - getPostsOrdeyByManyHit] " );
		List<PostUser> list = new ArrayList<>();
		list = sqlSession.selectList("MAIN.selectPostsOrdeyByManyHit");
		//System.out.println("MainDaoImpl - getPostsOrdeyByManyHit] list : " + list );
		return list;
	}

	@Override
	public List<PostUser> getRecentPosts() throws SQLException {
		//System.out.println("MainDaoImpl - getRecentPosts] " );
		List<PostUser> list = new ArrayList<>();
		list = sqlSession.selectList("MAIN.selectRecentPosts");
		//System.out.println("MainDaoImpl - getRecentPosts] list : " + list );
		return list;
	}
	
	
	
}
