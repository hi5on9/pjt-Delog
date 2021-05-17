package com.web.blog.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.Notice;
import com.web.blog.model.post.PostUser;
import com.web.blog.model.user.Follow;
import com.web.blog.model.user.FollowUser;
import com.web.blog.model.user.Login;
import com.web.blog.model.user.User;

@Repository
public class NoticeDaoImpl implements NoticeDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Notice> getNoticeList(int is_hidden) throws SQLException {
		//System.out.println("NoticeDaoImpl - getNoticeList] " );
		List<Notice> list = new ArrayList<Notice>();
		list = sqlSession.selectList("NOTICE.selectAllNotice", is_hidden);
		//System.out.println("NoticeDaoImpl - getNoticeList] list : " + list );
		return list;
	}

	@Override
	public Notice getNotice(int nid) throws SQLException {
		//System.out.println("NoticeDaoImpl - getNotice] pid : " + nid );
		Notice vo = sqlSession.selectOne("NOTICE.selectNotice", nid);
		//System.out.println("NoticeDaoImpl - getNotice] vo : " + nid );
		return vo;
	}

	@Override
	public int writeNotice(Notice notice) throws SQLException {
		//System.out.println("NoticeDaoImpl - writeNotice] post : " + notice );
		int cnt = sqlSession.insert("NOTICE.createNotice", notice);
		//System.out.println("NoticeDaoImpl - writeNotice] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int modifyNotice(Notice notice) throws SQLException {
		//System.out.println("NoticeDaoImpl - modifyNotice] post : " + notice );
		int cnt = sqlSession.update("NOTICE.updateNotice", notice);
		//System.out.println("NoticeDaoImpl - modifyNotice] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int deleteNotice(int nid) throws SQLException {
		//System.out.println("NoticeDaoImpl - deleteNotice] nid : " + nid );
		int cnt = sqlSession.delete("NOTICE.deleteNotice", nid);
		//System.out.println("NoticeDaoImpl - deleteNotice] cnt : " + cnt );
		return cnt;
	}
	
	@Override
	public int hitNotice(int nid) throws SQLException {
		//System.out.println("NoticeDaoImpl - hitNotice] nid : " + nid );
		int cnt = sqlSession.update("NOTICE.updateHit", nid);
		//System.out.println("NoticeDaoImpl - hitNotice]");
		return cnt;
	}

	@Override
	public int changeMain(Notice notice) throws SQLException {
		//System.out.println("NoticeDaoImpl - changeMain] post : " + notice );
		int cnt = sqlSession.update("NOTICE.DownMain");
		cnt = sqlSession.update("NOTICE.UpMain", notice);
		//System.out.println("NoticeDaoImpl - changeMain] cnt : " + cnt );
		return cnt;
	}

	@Override
	public List<Notice> getMain() throws SQLException {
		//System.out.println("NoticeDaoImpl - getMain] " );
		List<Notice> list = new ArrayList<Notice>();
		list = sqlSession.selectList("NOTICE.selectMain");
		//System.out.println("NoticeDaoImpl - getMain] list : " + list );
		return list;
	}
	
	
	

}
