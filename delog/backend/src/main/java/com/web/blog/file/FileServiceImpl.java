package com.web.blog.file;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService{

	@Autowired
	FileDao fileDao;
	
	@Override
	public int updateProfile(UserProfile userProfileInfo) throws SQLException {
		//System.out.println("FileServiceImpl - updateProfile] userProfileInfo : " + userProfileInfo);
		int cnt = fileDao.updateProfile(userProfileInfo);
		//System.out.println("FileServiceImpl - updateProfile] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int insertFile(PostFiles postFileInfo) throws SQLException {
		//System.out.println("FileServiceImpl - insertFile] postFileInfo : " + postFileInfo);
		int cnt = fileDao.insertFile(postFileInfo);
		//System.out.println("FileServiceImpl - insertFile] cnt : " + cnt);
		return cnt;
	}

	@Override
	public int updateThumbnail(PostThumbnail postThumbnailInfo) throws SQLException {
		//System.out.println("FileServiceImpl - updateThumbnail] postThumbnailInfo : " + postThumbnailInfo);
		int cnt = fileDao.updateThumbnail(postThumbnailInfo);
		//System.out.println("FileServiceImpl - updateThumbnail] cnt : " + cnt);
		return cnt;
	}

	
}
