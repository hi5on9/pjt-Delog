package com.web.blog.daos;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.Note;
import com.web.blog.model.NoteUser;

public interface NoteDao {
	
	public List<NoteUser> getSendList(int uid) throws SQLException;

	public List<NoteUser> getReciveList(int uid) throws SQLException;
	
	public int writeNote(Note note) throws SQLException;
	
	public NoteUser getNote(int nid) throws SQLException;
	
	public int trashNote(int nid) throws SQLException;
	
	public int deleteNote(int nid) throws SQLException;
		
}
