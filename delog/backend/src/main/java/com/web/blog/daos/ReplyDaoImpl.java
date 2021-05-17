package com.web.blog.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.Notice;
import com.web.blog.model.Reply;
import com.web.blog.model.ReplyUser;
import com.web.blog.model.post.LikeList;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.User;

@Repository
public class ReplyDaoImpl implements ReplyDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ReplyUser> getReplyList(Reply info) throws SQLException {
		//System.out.println("ReplyDaoImpl - getReplyList] ");
		List<ReplyUser> list = new ArrayList<ReplyUser>();
		list = sqlSession.selectList("REPLY.selectAllReply", info);
		//System.out.println("ReplyDaoImpl - getReplyList] list : " + list );
		return list;
	}

	@Override
	public List<ReplyUser> getReReplyList(Reply info) throws SQLException {
		//System.out.println("ReplyDaoImpl - getReReplyList] ");
		List<ReplyUser> list = new ArrayList<ReplyUser>();
		list = sqlSession.selectList("REPLY.selectReReply", info);
		//System.out.println("ReplyDaoImpl - getReReplyList] list : " + list );
		return list;
	}

	@Override
	public int writeReply(Reply reply) throws SQLException {
		//System.out.println("ReplyDaoImpl - writeReply] reply : " + reply );
		int cnt = sqlSession.insert("REPLY.createReply", reply );
		//System.out.println("ReplyDaoImpl - writeReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int modifyReply(Reply reply) throws SQLException {
		//System.out.println("ReplyDaoImpl - modifyReply] reply : " + reply);
		int cnt = sqlSession.update("REPLY.updateReply", reply);
		//System.out.println("ReplyDaoImpl - modifyReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int deleteReply(int rid) throws SQLException {
		//System.out.println("ReplyDaoImpl - deleteReply] rid : " + rid);
		int cnt = sqlSession.delete("REPLY.deleteReply", rid);
		//System.out.println("ReplyDaoImpl - deleteReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int likeReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - likeReply] likelist : " + likelist);
		int cnt = sqlSession.update("REPLY.likesReply", likelist);
		sqlSession.update("REPLY.likesReplyUp", likelist);
		//System.out.println("ReplyDaoImpl - likeReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int hateReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - hateReply] rid : " + likelist);
		int cnt = sqlSession.update("REPLY.hatesReply", likelist);
		sqlSession.update("REPLY.hatesReplyUp", likelist);
		//System.out.println("ReplyDaoImpl - hateReply] cnt : " + cnt );
		return cnt;
	}
	
	@Override
	public int unlikeReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - unlikeReply] likelist : " + likelist);
		int cnt = sqlSession.update("REPLY.unlikesReply", likelist);
		sqlSession.update("REPLY.likesReplyDown", likelist);
		//System.out.println("ReplyDaoImpl - unlikeReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int unhateReply(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - unhateReply] rid : " + likelist);
		int cnt = sqlSession.update("REPLY.unhatesReply", likelist);
		sqlSession.update("REPLY.hatesReplyDown", likelist);
		//System.out.println("ReplyDaoImpl - unhateReply] cnt : " + cnt );
		return cnt;
	}

	@Override
	public Reply getReply(int rid) throws SQLException {
		//System.out.println("ReplyDaoImpl - getReply] rid : " + rid);
		Reply ru = sqlSession.selectOne("REPLY.selectReply", rid);
		//System.out.println("ReplyDaoImpl - getReply] rid : " + ru);
		return ru;
	}

	@Override
	public int likeCheck(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - likeCheck] likelist : " + likelist);
		//System.out.println("시작---");
		int info = sqlSession.selectOne("REPLY.likesCheck", likelist);
		//System.out.println("끝---");
		//System.out.println("ReplyDaoImpl - likeCheck] info : " + info );
		return info;
	}

	@Override
	public int hateCheck(LikeList likelist) throws SQLException {
		//System.out.println("ReplyDaoImpl - hateCheck] likelist : " + likelist);
		int info = sqlSession.selectOne("REPLY.hatesCheck", likelist);
		//System.out.println("ReplyDaoImpl - hateCheck] info : " + info );
		return info;
	}
	

}
