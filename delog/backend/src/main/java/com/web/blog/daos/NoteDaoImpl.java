package com.web.blog.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.Note;
import com.web.blog.model.NoteUser;

@Repository
public class NoteDaoImpl implements NoteDao {

	@Autowired
	private SqlSession sqlSession;


	@Override
	public List<NoteUser> getSendList(int uid) throws SQLException {
		//System.out.println("NoteDaoImpl - getSendList] ");
		List<NoteUser> list = new ArrayList<NoteUser>();
		list = sqlSession.selectList("NOTE.selectAllSendNote", uid);
		//System.out.println("NoteDaoImpl - getSendList] list : " + list );
		return list;
	}

	@Override
	public List<NoteUser> getReciveList(int uid) throws SQLException {
		//System.out.println("NoteDaoImpl - getReciveList] ");
		List<NoteUser> list = new ArrayList<NoteUser>();
		list = sqlSession.selectList("NOTE.selectAllReciveNote", uid);
		//System.out.println("NoteDaoImpl - getReciveList] list : " + list );
		return list;
	}

	@Override
	public int writeNote(Note note) throws SQLException {
		//System.out.println("NoteDaoImpl - writeNote] note : " + note );
		int cnt = sqlSession.insert("NOTE.sendNote", note );
		//System.out.println("NoteDaoImpl - writeNote] cnt : " + cnt );
		return cnt;
	}

	@Override
	public NoteUser getNote(int nid) throws SQLException {
		//System.out.println("NoteDaoImpl - getNote] nid : " + nid);
		NoteUser note = sqlSession.selectOne("NOTE.selectNote", nid);
		sqlSession.update("NOTE.updateReaded");
		//System.out.println("NoteDaoImpl - getNote] nid : " + note);
		return note;
	}


	@Override
	public int trashNote(int nid) throws SQLException {
		//System.out.println("NoteDaoImpl - trashNote] nid : " + nid );
		int cnt = sqlSession.insert("NOTE.trashNote", nid );
		//System.out.println("NoteDaoImpl - trashNote] cnt : " + cnt );
		return cnt;
	}

	@Override
	public int deleteNote(int nid) throws SQLException {
		//System.out.println("NoteDaoImpl - deleteNote] nid : " + nid );
		int cnt = sqlSession.insert("NOTE.deleteNote", nid );
		//System.out.println("NoteDaoImpl - deleteNote] cnt : " + cnt );
		return cnt;
	}
	

}
