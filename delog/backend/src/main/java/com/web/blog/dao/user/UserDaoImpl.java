package com.web.blog.dao.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.hashtag.UserHashtag;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public User getUserByUid(int uid) throws SQLException {
		//System.out.println("UserDaoImpl - getUserByUid] uid : " + uid);
		User vo = sqlSession.selectOne("USER.selectUserByUid", uid);
		//System.out.println("UserDaoImpl - getUserByUid] vo : " + vo);
		return vo;
	}

	@Override
	public User getUserByEmail(String email) throws SQLException {
		//System.out.println("UserDaoImpl - getUserByEmail] email : " + email);
		User vo = sqlSession.selectOne("USER.selectUserByEmail", email);
		//System.out.println("UserDaoImpl - getUserByEmail] vo : " + vo);
		return vo;
	}

	@Override
	public User getUserByNickname(String nickname) throws SQLException {
		//System.out.println("UserDaoImpl - getUserByNickname] nickname : " + nickname);
		User vo = sqlSession.selectOne("USER.selectUserByNickname", nickname);
		//System.out.println("UserDaoImpl - getUserByNickname] vo : " + vo);
		return vo;
	}

	@Override
	// public Optional<User> findUserByEmailAndPassword(User user) throws
	// SQLException {
	public User login(Login login) throws SQLException {
		//System.out.println("UserDaoImpl - findUserByEmailAndPassword] login : " + login);
		// Optional<User> vo = sqlSession.selectOne("user.selectByEmailAndPassword",
		// user);
		User user = sqlSession.selectOne("USER.selectByEmailAndPassword", login);
		//System.out.println("UserDaoImpl - findUserByEmailAndPassword] user : " + user);

		return user;
	}

	@Override
	public int insertUser(User user) throws SQLException {
		//System.out.println("UserDaoImpl - insertUser] user : " + user);
		int cnt = sqlSession.insert("USER.insertUser", user);
		//System.out.println("UserDaoImpl - insertUser] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int deleteUser(int uid) throws SQLException {
		//System.out.println("UserDaoImpl - deleteUser] uid : " + uid);
		int cnt = sqlSession.delete("USER.deleteUser", uid);
		return cnt;
	}

	@Override
	public int modifyNickNameUser(User info) throws SQLException {
		//System.out.println("UserDaoImpl - modifyNickNameUser] info : " + info);
		int cnt = sqlSession.update("USER.modifyNickNameUser", info);
		return cnt;
	}

	@Override
	public int modifyPassWordUser(User user) throws SQLException {
		//System.out.println("UserDaoImpl - modifyPassWordUser] info : " + user);
		int cnt = sqlSession.update("USER.modifyPassWordUser", user);
		return cnt;
	}

	@Override
	public int follow(Follow info) throws SQLException {
		//System.out.println("UserDaoImpl - follow] info : " + info);
		int cnt = sqlSession.insert("FOLLOW.follow", info);
		//System.out.println("UserDaoImpl - follow] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int unFollow(Follow info) throws SQLException {
		//System.out.println("UserDaoImpl - unFollow] info : " + info);
		int cnt = sqlSession.delete("FOLLOW.unFollow", info);
		//System.out.println("UserDaoImpl - unFollow] cnt : " + cnt);
		return cnt;
	}

	@Override
	public List<FollowUser> getFollower(int uid) throws SQLException {
		//System.out.println("UserDaoImpl - getFollower] ");
		List<FollowUser> list = new ArrayList<>();
		list = sqlSession.selectList("FOLLOW.selectAllFollower", uid);
		//System.out.println("PostDaoImpl - getFollower] list : " + list);
		return list;
	}

	@Override
	public List<FollowUser> getFollowing(int uid) throws SQLException {
		//System.out.println("UserDaoImpl - getFollowing] ");
		List<FollowUser> list = new ArrayList<>();
		list = sqlSession.selectList("FOLLOW.selectAllFollowing", uid);
		//System.out.println("PostDaoImpl - getFollowing] list : " + list);
		return list;
	}

	// f_uid가 s_uid를 팔로우 했냐
	@Override
	public Follow followerCheck(Follow info) throws SQLException {
		//System.out.println("UserDaoImpl - followerCheck] info : " + info);
		Follow follow = sqlSession.selectOne("FOLLOW.followerCheck", info);
		//System.out.println("UserDaoImpl - followerCheck] cnt : " + follow);
		return follow;
	}

	// s_uid가 f_uid를 팔로우 했냐
	@Override
	public Follow followingCheck(Follow info) throws SQLException {
		//System.out.println("UserDaoImpl - followingCheck] info : " + info);
		Follow follow = sqlSession.selectOne("FOLLOW.followingCheck", info);
		//System.out.println("UserDaoImpl - followingCheck] cnt : " + follow);
		return follow;
	}

	@Override
	public User getKakaoUser(User info) throws SQLException {
		//System.out.println("UserDaoImpl - getKakaoUser] info : " + info);
		User user = sqlSession.selectOne("USER.getKakaoUser", info);
		//System.out.println("UserDaoImpl - getKakaoUser] user : " + user);
		return user;
	}


	@Override
	public List<User> getUserList() throws SQLException {
		//System.out.println("UserDaoImpl - getUserList]");
		List<User> list = new ArrayList<>();
		list = sqlSession.selectList("USER.selectAll");
		return list;
	}

	@Override
	public UserHashtag getHashtagFields(int uid) throws SQLException {
		System.out.println("UserDaoImpl - getHashtagFields]");
		UserHashtag userHashtag = new UserHashtag();
		
		List<String> title = new ArrayList<>();
		title = sqlSession.selectList("USER.selectHashtagFields", uid);
		System.out.println("UserDaoImpl - getHashtagFields] title : " + title);
		userHashtag.setTitle(title);
		userHashtag.setUser(sqlSession.selectOne("USER.selectUserByUid", uid));
		System.out.println("UserDaoImpl - getHashtagFields] userHashtag : " + userHashtag);
		return userHashtag;
	}

	@Override
	public String getUserPassword(Login login) throws SQLException {
		//System.out.println("UserDaoImpl - getPassword]");
		String password = sqlSession.selectOne("USER.selectUserPassword", login);
		//System.out.println("UserDaoImpl - getPassword] password : " + password);
		return password;
	}
	
	

	
}
