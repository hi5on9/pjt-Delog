package com.web.blog.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.Prison;
import com.web.blog.model.Report;
import com.web.blog.model.ReportList;
import com.web.blog.model.ReportUser;

@Repository
public class ReportDaoImpl implements ReportDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<ReportUser> getReporterList(int uid) throws SQLException {
		//System.out.println("ReportDaoImpl - getReporterList] ");
		List<ReportUser> list = new ArrayList<ReportUser>();
		list = sqlSession.selectList("REPORT.selectMyReport", uid);
		//System.out.println("ReportDaoImpl - getReporterList] list : " + list );
		return list;
	}

	@Override
	public int sendReport(Report report) throws SQLException {
		int cnt = sqlSession.insert("REPORT.createReport", report);
		return cnt;
	}

	@Override
	public int deleteReport(int rid) throws SQLException {
		int cnt = sqlSession.delete("REPORT.deleteReport", rid);
		return cnt;
	}
	
	@Override
	public List<Report> getReport(Report report) throws SQLException {
		List<Report> info = sqlSession.selectList("REPORT.selectReport", report);
		return info;
	}
	
	@Override
	public List<ReportList> getAllReportList() throws SQLException {
		List<ReportList> list = new ArrayList<>();
		list = sqlSession.selectList("REPORT.selectAllReport");
		return list;
	}

	@Override
	public int sendPrison(Prison prison) throws SQLException {
		//System.out.println("--------------------------------" + prison.getRid());
		int cnt = sqlSession.insert("REPORT.createPrison", prison);
		return cnt;
	}

	@Override
	public int deletePrison() throws SQLException {
		int cnt = sqlSession.delete("REPORT.deletePrison");
		return cnt;
	}

	@Override
	public Prison checkPrison(int uid) throws SQLException {
		Prison prison = sqlSession.selectOne("REPORT.checkPrison", uid);
		return prison;
	}

	@Override
	public Report getOneReport(int rid) throws SQLException {
		Report report = sqlSession.selectOne("REPORT.selectReportByRid", rid);
		return null;
	}

	
	
	

	

}
