package com.web.blog.dao.post;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.DailyPostCount;
import com.web.blog.model.Likes;
import com.web.blog.model.PostHashtag;
import com.web.blog.model.page.ListPage;
import com.web.blog.model.page.PostPage;
import com.web.blog.model.post.Post;
import com.web.blog.model.post.PostUser;

public interface PostDao {

	public List<PostUser> getPostList() throws SQLException;

	public PostUser getPost(int pid) throws SQLException;

	public int writePost(Post post) throws SQLException;

	public int modifyPost(Post post) throws SQLException;

	public int deletePost(int pid) throws SQLException;
	
	public List<PostUser> getMyPostList(ListPage pageing) throws SQLException;

	public List<PostUser> getListByTitle(PostPage paging) throws SQLException;

	public List<PostUser> getListByContents(PostPage paging) throws SQLException;

	public List<PostUser> getListByNickname(PostPage paging) throws SQLException;

	public List<PostHashtag> getListByHashtag(PostPage paging) throws SQLException;

	public int hitPost(int pid) throws SQLException;
	
	public int likePost(Likes likes) throws SQLException;

	public int unlikePost(Likes likes) throws SQLException;
	
	public Likes likeCheck(Likes like) throws SQLException;
	
    public List<PostUser> getLikeList(ListPage page) throws SQLException;
    
    public List<PostUser> getRecentList() throws SQLException;
	
	// 임시 저장
	public List<PostUser> getTempList(int uid) throws SQLException;
	
	public int writeTempPost(Post post) throws SQLException;

	public int modifyTempPost(Post post) throws SQLException;

	public int deleteTempPost(int pid) throws SQLException;
	
	public int changeTempPost(int pid) throws SQLException;
	
	// 글 숨기기
	
	public List<PostUser> getHideList(int uid) throws SQLException;
	
	public int hidePost(int pid) throws SQLException;
	
	public int unhidePost(int pid) throws SQLException;
	
	// search 개수
	
	public int getTitleCount(String title) throws SQLException;
	
	public int getContentsCount(String contents) throws SQLException;
	
	public int getNicknameCount(String nickname) throws SQLException;
	
	public int getHashtagCount(String hashtag) throws SQLException;
	
	public List<DailyPostCount> getDailyPostCount(int uid) throws SQLException;

}
