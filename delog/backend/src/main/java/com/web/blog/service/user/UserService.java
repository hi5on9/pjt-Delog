package com.web.blog.service.user;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.hashtag.UserHashtag;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.User;

public interface UserService {
	// User getUserByEmail(String email);
	// Optional<User> findUserByEmailAndPassword(String email, String password);

	public User getUserByUid(int uid) throws SQLException;

	public User getUserByEmail(String email) throws SQLException;

	public User getUserByNickname(String nickname) throws SQLException;

	// public Optional<User> findUserByEmailAndPassword(User user) throws
	// SQLException;
	public User login(Login login) throws SQLException;

	public int insertUser(User user) throws SQLException;

	public int deleteUser(int uid) throws SQLException;

	public int modifyNickNameUser(User user) throws SQLException;

	public int modifyPassWordUser(User user) throws SQLException;

	public int follow(Follow info) throws SQLException;

	public int unFollow(Follow info) throws SQLException;
	
	public List<FollowUser> getFollower(int uid) throws SQLException;
	
	public List<FollowUser> getFollowing(int uid) throws SQLException;
    
    public Follow followerCheck(Follow info) throws SQLException;

    public Follow followingCheck(Follow info) throws SQLException;
    
    public User kakaoLoginUser(User user) throws SQLException;
    
    public List<User> getUserList() throws SQLException;
    
    public UserHashtag getHashtagFields(int uid) throws SQLException;
}
