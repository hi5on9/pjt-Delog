package com.web.blog.hashtag;

import java.sql.SQLException;

import com.web.blog.model.Hashtag;
import com.web.blog.model.PostHasHashtag;

public interface HashtagDao {

	// getHashtag
	public Hashtag selectHashTagByTitle(String title) throws SQLException;

	// selectPost has Hashtag 
	public PostHasHashtag selectPostHasHashTag(PostHasHashtag data) throws SQLException;

	// createHashtag hashtag tavle에 write
	public int writeHashtag(String title) throws SQLException;
	
	// write Post has Hashtag
	public int writePostHasHashTag(PostHasHashtag data) throws SQLException;
	
	// increaseHashtagCount 
	public int increaseHashtagCount(String title) throws SQLException;
	
	// decreasehashtagCount
	public int decreaseHashtagCount(String title) throws SQLException;
	
	// delete hashtag in hashtag table
	public int deleteHashtagByTitle(String title) throws SQLException;

	// delete post has hashtag table
	public int deletePostHasHashTag(PostHasHashtag data) throws SQLException;
}
