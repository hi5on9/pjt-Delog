package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.daos.ReplyDao;
import com.web.blog.model.Reply;
import com.web.blog.model.ReplyUser;
import com.web.blog.model.post.LikeList;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Autowired
	ReplyDao replyDao;

	@Override
	public List<ReplyUser> getReplyList(Reply info) throws SQLException {
		//System.out.println("ReplyService - getReplyList] info : " + info);
		return replyDao.getReplyList(info);
	}

	@Override
	public List<ReplyUser> getReReplyList(Reply info) throws SQLException {
		//System.out.println("ReplyService - getReReplyList] info : " + info);
		return replyDao.getReReplyList(info);
	}

	@Override
	public int writeReply(Reply reply) throws SQLException {
		//System.out.println("ReplyService - writeReply] reply : " + reply);
		return replyDao.writeReply(reply);
	}

	@Override
	public int modifyReply(Reply reply) throws SQLException {
		//System.out.println("ReplyService - modifyReply] reply : " + reply);
		return replyDao.modifyReply(reply);
	}

	@Override
	public int deleteReply(int rid) throws SQLException {
		//System.out.println("ReplyService - deleteReply] rid : " + rid);
		return replyDao.deleteReply(rid);
	}

	@Override
	public int likeReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - likeReply] likelist : " + likelist);
		return replyDao.likeReply(likelist);
	}

	@Override
	public int hateReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - hateReply] likelist : " + likelist);
		return replyDao.hateReply(likelist);
	}
	
	@Override
	public int unlikeReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - unlikeReply] likelist : " + likelist);
		return replyDao.unlikeReply(likelist);
	}

	@Override
	public int unhateReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - unhateReply] likelist : " + likelist);
		return replyDao.unhateReply(likelist);
	}

	@Override
	public Reply getReply(int rid) throws SQLException {
		//System.out.println("ReplyService - getReply] rid" + rid);
		return replyDao.getReply(rid);
	}

	@Override
	public int likeCheck(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - likeCheck] likelist : " + likelist);
		return replyDao.likeCheck(likelist);
	}

	@Override
	public int hateCheck(LikeList likelist) throws SQLException {
		//System.out.println("ReplyService - hateCheck] likelist : " + likelist);
		return replyDao.hateCheck(likelist);
	}
	
}
