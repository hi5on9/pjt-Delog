package com.web.blog.file;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FileDaoImpl implements FileDao {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int updateProfile(UserProfile userProfileInfo) throws SQLException {
		//System.out.println("FileDaoImpl - updateProfile] userProfileInfo : " + userProfileInfo);
		int cnt = sqlSession.update("USER.updateProfile", userProfileInfo);
		//System.out.println("FileDaoImpl - updateProfile] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int insertFile(PostFiles postFileInfo) throws SQLException {
		//System.out.println("FileDaoImpl - insertFile] postFileInfo : " + postFileInfo);
		int cnt = sqlSession.update("POST.insertFile", postFileInfo);
		//System.out.println("FileDaoImpl - insertFile] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int updateThumbnail(PostThumbnail postThumbnailInfo) throws SQLException {
		//System.out.println("FileDaoImpl - updateThumbnail] updateThumbnail : " + postThumbnailInfo);
		int cnt = sqlSession.update("USER.updateThumbnail", postThumbnailInfo);
		//System.out.println("FileDaoImpl - updateThumbnail] cnt : " + cnt);
		return cnt;
	}
	

}
