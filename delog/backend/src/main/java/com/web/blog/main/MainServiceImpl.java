package com.web.blog.main;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.model.UserAndCount;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.FollowPost;

@Service
public class MainServiceImpl implements MainService {

	@Autowired
	MainDao mainDao;

	@Override
	public List<FollowPost> getfollowingsRecentPost(int uid) throws SQLException {
		//System.out.println("MainServiceImpl - getfollowingsRecentPost] uid : " + uid);
		return mainDao.getfollowingsRecentPost(uid);
	}

	@Override
	public List<UserAndCount> getFolloweringUidsOrderByFollowerCount() throws SQLException {
		//System.out.println("MainServiceImpl - getFolloweringUidsOrderByFollowerCount]");
		return mainDao.getFolloweringUidsOrderByFollowerCount();
	}

	@Override
	public List<UserAndCount> getUidsOrderByManyPostCount() throws SQLException {
		//System.out.println("MainServiceImpl - getUidsOrderByManyPostCount]");
		return mainDao.getUidsOrderByManyPostCount();
	}

	@Override
	public List<PostUser> getPostsOrdeyByManyHit() throws SQLException {
		//System.out.println("MainServiceImpl - getPostsOrdeyByManyHit]");
		return mainDao.getPostsOrdeyByManyHit();
	}

	@Override
	public List<PostUser> getRecentPosts() throws SQLException {
		//System.out.println("MainServiceImpl - getRecentPosts]");
		return mainDao.getRecentPosts();
	}
	
	
	
}
