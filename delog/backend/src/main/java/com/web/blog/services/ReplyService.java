package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.Reply;
import com.web.blog.model.ReplyUser;
import com.web.blog.model.post.LikeList;

public interface ReplyService {

	public List<ReplyUser> getReplyList(Reply info) throws SQLException;

	public List<ReplyUser> getReReplyList(Reply info) throws SQLException;
	
	public int writeReply(Reply reply) throws SQLException;

	public int modifyReply(Reply reply) throws SQLException;

	public int deleteReply(int rid) throws SQLException;
	
	public int likeReply(LikeList list) throws SQLException;
	
	public int hateReply(LikeList list) throws SQLException;
	
	public int unlikeReply(LikeList list) throws SQLException;
	
	public int unhateReply(LikeList list) throws SQLException;
	
	public Reply getReply(int rid) throws SQLException;
	
	public int likeCheck(LikeList likelist) throws SQLException;

	public int hateCheck(LikeList likelist) throws SQLException;
}
