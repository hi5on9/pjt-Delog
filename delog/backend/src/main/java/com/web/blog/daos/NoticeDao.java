package com.web.blog.daos;

import java.sql.SQLException;
import java.util.List;

import com.web.blog.model.Notice;

public interface NoticeDao {
	
	public List<Notice> getNoticeList(int is_hidden) throws SQLException;

	public Notice getNotice(int nid) throws SQLException;
	
	public int writeNotice(Notice notice) throws SQLException;

	public int modifyNotice(Notice notice) throws SQLException;

	public int deleteNotice(int nid) throws SQLException;
	
	public int hitNotice(int nid) throws SQLException;
	
	public int changeMain(Notice notice) throws SQLException;
	
	public List<Notice> getMain() throws SQLException;
		
}
