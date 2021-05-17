package com.web.blog.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.blog.daos.NoticeDao;
import com.web.blog.model.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	NoticeDao noticeDao;

	@Override
	public List<Notice> getNoticeList(int is_hidden) throws SQLException {
		//System.out.println("NoticeService - getNoticeList] type : " + (is_hidden == 1 ? "All" : (is_hidden == 2) ? "user" : "hidden"));
		return noticeDao.getNoticeList(is_hidden);
	}

	@Override
	public Notice getNotice(int nid) throws SQLException {
		//System.out.println("NoticeService - getNotice] nid : " + nid);
		noticeDao.hitNotice(nid);
		return noticeDao.getNotice(nid);
	}

	@Override
	public int writeNotice(Notice notice) throws SQLException {
		//System.out.println("NoticeService - writeNotice] notice : " + notice);
		return noticeDao.writeNotice(notice);
	}

	@Override
	public int modifyNotice(Notice notice) throws SQLException {
		//System.out.println("NoticeService - modifyNotice] notice : " + notice);
		return noticeDao.modifyNotice(notice);
	}

	@Override
	public int deleteNotice(int nid) throws SQLException {
		//System.out.println("NoticeService - deleteNotice] nid : " + nid);
		return noticeDao.deleteNotice(nid);
	}

	@Override
	public int changeMain(Notice notice) throws SQLException {
		//System.out.println("NoticeService - changeMain] notice : " + notice);
		return noticeDao.changeMain(notice);
	}

	@Override
	public List<Notice> getMain() throws SQLException {
		//System.out.println("NoticeService - getMain]  " );
		return noticeDao.getMain();
	}
	
	
	
	
	
	
}
