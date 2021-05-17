package com.web.blog.file;

import java.sql.SQLException;

public interface FileDao {

	public int updateProfile(UserProfile userProfileInfo) throws SQLException;

	
	public int updateThumbnail(PostThumbnail postThumbnailInfo) throws SQLException;

	
	public int insertFile(PostFiles postFileInfo) throws SQLException;
}
