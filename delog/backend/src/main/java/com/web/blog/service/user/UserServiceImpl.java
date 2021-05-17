package com.web.blog.service.user;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.UserException;
import com.web.blog.dao.user.UserDao;
import com.web.blog.hashtag.UserHashtag;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.User;
import com.web.blog.util.BCryptHelper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	private BCryptHelper bCrypt;
	
	@Override
	public User getUserByUid(int uid) throws SQLException {
		//System.out.println("UserService - getUserByUid] uid : " + uid);
		return userDao.getUserByUid(uid);
	}
	
	@Override
	public User getUserByEmail(String email) throws SQLException {
		//System.out.println("UserService - getUserByEmail] email : " + email);
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserByNickname(String nickname) throws SQLException {
		//System.out.println("UserService - getUserByNickname] nickname : " + nickname);
		return userDao.getUserByNickname(nickname);
	}

	@Override
	public User login(Login login) throws SQLException {
		//System.out.println("UserService - login] login : " + login);
		User userinfo = null;
		String hasedPassword = userDao.getUserPassword(login);
		//System.out.println("UserService - login] hasedPassword : " + hasedPassword);
		if(hasedPassword == null) {
			userinfo = null;
		}
		else if(bCrypt.isMatch(login.getPassword(), hasedPassword)) {
			userinfo = 	userDao.getUserByEmail(login.getEmail());
		}
		//System.out.println("UserService - login] userinfo : " + userinfo);
		return userinfo;
	}
	
	@Override
	public int insertUser(User user) throws SQLException {
		
		if(user.getEmail() == null ||  user.getPassword() == null || user.getNickname() == null) {
			return -2;
		}
		
		if(user.getProvider() == null) {
			user.setPassword(bCrypt.encrpyt(user.getPassword()));
			user.setProvider("local");
			user.setSnsid("");
			user.setProfile("http://52.78.74.11:8080/image/profile/default.png");
		}
		//System.out.println("UserService - insertUser] user : " + user);
		int cnt = -1;
		
		cnt = userDao.insertUser(user);
		//System.out.println("UserService - insertUser] insertUser cnt  : " + cnt);
		
		return cnt;
	}

	@Override
	public int deleteUser(int uid) throws SQLException {
		int cnt = -1;
		//System.out.println("UserService - deleteUser] uid: " + uid);
		cnt = userDao.deleteUser(uid);
		
		if(cnt == -1) {
			throw new UserException("회원 정보 삭제 중 오류가 발생했습니다.");
		}
		return cnt;
	}

	@Override
	public int modifyNickNameUser(User user) throws SQLException {
		int cnt = -1;
		//System.out.println("UserService - modifyNickNameUser] user: " + user);
		cnt = userDao.modifyNickNameUser(user);
		if(cnt == -1) {
			throw new UserException("회원 정보 수정 중 오류가 발생했습니다.");
		}
		return cnt;
	}

	@Override
	public int modifyPassWordUser(User user) throws SQLException {
		int cnt = -1;
		//System.out.println("UserService - modifyPassWordUser] user: " + user);
		
		String hasedPassword = bCrypt.encrpyt(user.getPassword());
		user.setPassword(hasedPassword);
		cnt = userDao.modifyPassWordUser(user);
		if(cnt == -1) {
			throw new UserException("회원 정보 수정 중 오류가 발생했습니다.");
		}
		return cnt;
	}

	@Override
	public int follow(Follow info) throws SQLException {
		//System.out.println("UserServiceImpl - follow] info : " + info);
		return userDao.follow(info);
	}

	@Override
	public int unFollow(Follow info) throws SQLException {
		//System.out.println("UserServiceImpl - unFollow] info : " + info);
		return userDao.unFollow(info);
	}
	
	
	@Override
	public List<FollowUser> getFollower(int uid) throws SQLException {
		//System.out.println("UserServiceImpl - getFollower] ");
		return userDao.getFollower(uid);
	}

	@Override
	public List<FollowUser> getFollowing(int uid) throws SQLException {
		//System.out.println("UserServiceImpl - getFollowing] ");
		return userDao.getFollowing(uid);
	}

    @Override
    public Follow followerCheck(Follow info) throws SQLException {
        //System.out.println("UserServiceImpl - followerCheck] info : " + info);
        return userDao.followerCheck(info);
    }

    @Override
    public Follow followingCheck(Follow info) throws SQLException {
        //System.out.println("UserServiceImpl - followingCheck] info : " + info);
        return userDao.followingCheck(info);
    }

	@Override
	public User kakaoLoginUser(User user) throws SQLException {
		//System.out.println("UserServiceImpl - kakaoLoginUser] user : " + user);
		
		int cnt = -1;
		// DB에 provider이 kakao, snsId 가 일치하는 row가 있는지 확인
		User kakao = userDao.getKakaoUser(user);
		//System.out.println("UserServiceImpl - kakaoLoginUser - getKakaoUser] kakao : " + kakao);
		if(kakao != null) {
			return kakao;
		}
		else {
			// 추가 후
			cnt = userDao.insertUser(user);
			//System.out.println("UserServiceImpl - kakaoLoginUser - insertUser] kakao : " + kakao);
			kakao = userDao.getKakaoUser(user);
			//System.out.println("UserServiceImpl - kakaoLoginUser - getKakaoUser] kakao : " + kakao);
			return kakao;
		}
	}

	@Override
	public List<User> getUserList() throws SQLException {
		 //System.out.println("UserServiceImpl - getUserList] ");
	     return userDao.getUserList();
	}

	@Override
	public UserHashtag getHashtagFields(int uid) throws SQLException {
		 //System.out.println("UserServiceImpl - getHashtagFields] ");
		return userDao.getHashtagFields(uid);
	}
	
	
	
}
