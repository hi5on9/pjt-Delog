package com.web.blog.main;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.UserAndCount;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.FollowPost;

public interface MainService {
	public List<FollowPost> getfollowingsRecentPost(int uid) throws SQLException;

	public List<UserAndCount> getFolloweringUidsOrderByFollowerCount() throws SQLException;

	public List<UserAndCount> getUidsOrderByManyPostCount() throws SQLException;

	public List<PostUser> getPostsOrdeyByManyHit() throws SQLException;

	public List<PostUser> getRecentPosts() throws SQLException;

}
