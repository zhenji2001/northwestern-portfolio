package com.xihui.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.xurui.dormain.Test;
import com.xurui.dormain.TestQuestion;

public class TestQuestionUtil {
	DBAccess db = new DBAccess();
	public ArrayList getTest(String Uid,String course,int type)//����Ծ������Ӧ���͵���
	{
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		try{
			String sql = "select t.*,q.ques,q.keyA,q.keyB,q.keyC,q.keyD,q.answer from test as t inner join Question as q  on t.Qid = q.Qid where t.Uid=? and t.course=? and t.Qtype=? order by Tnumber";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Uid);
			ps.setString(2, course);
			ps.setInt(3, type);
			rs = ps.executeQuery();
			while(rs.next())
			{
				TestQuestion tq = new TestQuestion();
				tq.setUid(Uid);
				tq.setQid(rs.getInt(2));
				tq.setStuAnswer(rs.getString(3));
				tq.setCourse(course);
				tq.setScore(rs.getFloat(5));
				tq.setType(type);
				tq.setTnumber(rs.getInt(7));
				tq.setQues(rs.getString(8));
				tq.setKeyA(rs.getString(9));
				tq.setKeyB(rs.getString(10));
				tq.setKeyC(rs.getString(11));
				tq.setKeyD(rs.getString(12));
				tq.setAnswer(rs.getString(13));
				al.add(tq);
				
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
			
		}finally
		{
			db.CloseDB(conn, ps, rs);
			return al;
		}
		
	}

}
