package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.daos.NoteDao;
import com.web.blog.model.Note;
import com.web.blog.model.NoteUser;
import com.web.blog.model.Reply;
import com.web.blog.model.ReplyUser;
import com.web.blog.model.post.LikeList;

@Service
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	NoteDao noteDao;

	@Override
	public List<NoteUser> getSendList(int uid) throws SQLException {
		//System.out.println("NoteService - getSendList] uid : " + uid);
		return noteDao.getSendList(uid);
	}

	@Override
	public List<NoteUser> getReciveList(int uid) throws SQLException {
		//System.out.println("NoteService - getReciveList] uid : " + uid);
		return noteDao.getReciveList(uid);
	}

	@Override
	public int writeNote(Note note) throws SQLException {
		//System.out.println("NoteService - writeNote] note : " + note);
		return noteDao.writeNote(note);
	}

	@Override
	public NoteUser getNote(int nid) throws SQLException {
		//System.out.println("NoteService - getNote] nid" + nid);
		return noteDao.getNote(nid);
	}

	

	@Override
	public int trashNote(int nid) throws SQLException {
		//System.out.println("NoteService - trashNote] nid : " + nid);
		return noteDao.trashNote(nid);
	}

	@Override
	public int deleteNote(int nid) throws SQLException {
		//System.out.println("NoteService - deleteNote] nid : " + nid);
		return noteDao.deleteNote(nid);
	}
	
}
