package com.web.blog.hashtag;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.web.blog.model.Hashtag;
import com.web.blog.model.PostHasHashtag;

@Repository
public class HashtagDaoImpl implements HashtagDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	// Hashtag Table에서 해당 title이 있는지 확인
	@Override
	public Hashtag selectHashTagByTitle(String title) throws SQLException {
		//System.out.println("HashtagDaoImpl - selectHashTagByTitle]  title : " + title );
		Hashtag hashtag = sqlSession.selectOne("HASHTAG.selectHashTagByTitle", title);
		//System.out.println("HashtagDaoImpl - selectHashTagByTitle]  hashtag : " + hashtag );
		return hashtag;
	}

	@Override
	public PostHasHashtag selectPostHasHashTag(PostHasHashtag data) throws SQLException {
		//System.out.println("HashtagDaoImpl - selectPostHasHashTag]  data : " + data );
		PostHasHashtag posthashtag = sqlSession.selectOne("HASHTAG.selectPostHasHashTag", data);
		//System.out.println("HashtagDaoImpl - selectPostHasHashTag]  posthashtag : " + posthashtag );
		return posthashtag;
	}

	@Override
	public int writeHashtag(String title) throws SQLException {
		//System.out.println("HashtagDaoImpl - writeHashtag]  title : " + title );
		int cnt = sqlSession.insert("HASHTAG.writeHashtag", title);
		//System.out.println("HashtagDaoImpl - writeHashtag]  cnt : " + cnt );
		return cnt;
	}

	@Override
	public int writePostHasHashTag(PostHasHashtag data) throws SQLException {
		//System.out.println("HashtagDaoImpl - writePostHasHashTag]  data : " + data );
		int cnt = sqlSession.insert("HASHTAG.writePostHasHashTag", data);
		//System.out.println("HashtagDaoImpl - writePostHasHashTag]  cnt : " + cnt );
		return cnt;
	}

	
	@Override
	public int increaseHashtagCount(String title) throws SQLException {
		//System.out.println("HashtagDaoImpl - increaseHashtagCount]  title : " + title );
		int cnt = sqlSession.insert("HASHTAG.increaseHashtagCount", title);
		//System.out.println("HashtagDaoImpl - increaseHashtagCount]  cnt : " + cnt );
		return cnt;
	}

	@Override
	public int decreaseHashtagCount(String title) throws SQLException {
		//System.out.println("HashtagDaoImpl - decreaseHashtagCount]  title : " + title );
		int cnt = sqlSession.insert("HASHTAG.decreaseHashtagCount", title);
		//System.out.println("HashtagDaoImpl - decreaseHashtagCount]  cnt : " + cnt );
		return cnt;
	}

	@Override
	public int deleteHashtagByTitle(String title) throws SQLException {
		//System.out.println("HashtagDaoImpl - deleteHashtagByTitle]  title : " + title );
		int cnt = sqlSession.insert("HASHTAG.deleteHashtagByTitle", title);
		//System.out.println("HashtagDaoImpl - deleteHashtagByTitle]  cnt : " + cnt );
		return cnt;
	}

	@Override
	public int deletePostHasHashTag(PostHasHashtag data) throws SQLException {
		//System.out.println("HashtagDaoImpl - deletePostHasHashTag]  data : " + data );
		int cnt = sqlSession.insert("HASHTAG.deletePostHasHashTag", data);
		//System.out.println("HashtagDaoImpl - deletePostHasHashTag]  cnt : " + cnt );
		return cnt;
	}
	
	
	
	
}
