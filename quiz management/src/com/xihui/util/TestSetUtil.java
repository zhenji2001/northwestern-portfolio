package com.xihui.util;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import com.xurui.dormain.*;

public class TestSetUtil {

	public DBAccess db = new DBAccess();
	
	public TestSet getTestSet(String course)//���ݿ�Ŀ�õ�����������Ϣ
	{
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		TestSet ts = null;
		try{
			conn = db.getConnection();
			String sql = "select * from test_set where course=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, course);
			rs = ps.executeQuery();
			if(rs.next())
			{
				ts =new TestSet();
				int sinChCount = Integer.parseInt(rs.getString(2));
				int perSinScore = Integer.parseInt(rs.getString(3));
				int mulChCount = Integer.parseInt(rs.getString(4));
				int perMulScore = Integer.parseInt(rs.getString(5));
				int judgeCount = Integer.parseInt(rs.getString(6));
				int perJudScore = Integer.parseInt(rs.getString(7));
				int jdCount = Integer.parseInt(rs.getString(8));
				int perJdScore = Integer.parseInt(rs.getString(9));
				int programCount = Integer.parseInt(rs.getString(10));
				int perProScore = Integer.parseInt(rs.getString(11));
				int totaltime = Integer.parseInt(rs.getString(12));
				ts.setCourse(course);
				ts.setSinChCount(sinChCount);
				ts.setPerSinScore(perSinScore);
				ts.setMulChCount(mulChCount);
				ts.setPerMulScore(perMulScore);
				ts.setJudgeCount(judgeCount);
				ts.setPerJudScore(perJudScore);
				ts.setJdCount(jdCount);
				ts.setPerJdScore(perJdScore);
				ts.setProgramCount(programCount);
				ts.setPerProScore(perProScore);
				ts.setTotaltime(totaltime);
			}
			
		}catch(Exception e)
		{
		  e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, ps, rs);
			return ts;
		}
	}
	
	public int getTotalScore(TestSet ts)//�����ܷ�
	{
		int totalScore=0;
		if(ts!=null)
		{
		 int sinChCount = ts.getSinChCount();
		 int perSinScore = ts.getPerSinScore();
		 int mulChCount = ts.getMulChCount();
		 int perMulScore = ts.getPerMulScore();
		 int judgeCount = ts.getJudgeCount();
		 int perJudScore = ts.getPerJudScore();
		 int jdCount = ts.getJdCount();
		 int perJdScore = ts.getPerJdScore();
		 int programCount = ts.getProgramCount();
		 int perProScore = ts.getPerProScore();	
		 totalScore = sinChCount*perSinScore+mulChCount*perMulScore+judgeCount*perJudScore+jdCount*perJdScore+programCount*perProScore;		
	    }
       return totalScore;
	}
	public ArrayList getCourse()//��ȡ��Ŀ
	{
		ArrayList al = new ArrayList<String>();
		Connection conn = db.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = conn.createStatement();
			String sql = "select distinct course from test_set";
			rs = st.executeQuery(sql);
			while(rs.next())
			{
				String course = rs.getString(1);
				al.add(course);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, st, rs);
			return al;
		}
	}
	
	public int getCount()//��ȡ����
	{
		int count=0;
		Connection conn = db.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = conn.createStatement();
			String sql = "select count(*) from test_set";
			rs = st.executeQuery(sql);
			if(rs.next())
			{
				count = rs.getInt(1);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, st, rs);
			return count;
		}
	}
	
	public ArrayList getAllTestSet(int pageSize,int current)//�õ�ָ��ҳ�Ŀ�����Ϣ
	{
		Connection conn = db.getConnection();
		Statement sm = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		try{
			conn = db.getConnection();
			String sql = "select top "+pageSize+" * from test_set where course not in (select top "+pageSize*(current-1)+"  course from test_set) order by course";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while(rs.next())
			{
				TestSet ts = new TestSet();
				ts.setCourse(rs.getString(1).trim());
				ts.setSinChCount(rs.getInt(2));
				ts.setPerSinScore(rs.getInt(3));
				ts.setMulChCount(rs.getInt(4));
				ts.setPerMulScore(rs.getInt(5));
				ts.setJudgeCount(rs.getInt(6));
				ts.setPerJudScore(rs.getInt(7));
				ts.setJdCount(rs.getInt(8));
				ts.setPerJdScore(rs.getInt(9));
				ts.setProgramCount(rs.getInt(10));
				ts.setPerProScore(rs.getInt(11));
			    ts.setTotaltime(rs.getInt(12));
	            al.add(ts);
			}
			
		}catch(Exception e)
		{
		  e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, sm, rs);
			return al;
		}
	}
	public boolean insertOrUpdate(TestSet ts,int flag)//��ӻ��޸Ŀ�������,flag=0Ϊ��ӣ�flag=1Ϊ�޸�
	{
		boolean success = false;
		int count = 0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "";
		    if(flag==0)
		    {
		    	sql = "insert into test_set(sinc,persin,mulc,permul,judge,perju,jd,perjd,program,perpro,totaltime,course) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		    }else if(flag==1)
		    {
		    	sql="update test_set set sinc=?,persin=?,mulc=?,permul=?,judge=?,perju=?,jd=?,perjd=?,program=?,perpro=?,totaltime=? where course=?";
		    }
		    ps = conn.prepareStatement(sql);
		    ps.setInt(1, ts.getSinChCount());
		    ps.setInt(2, ts.getPerSinScore());
		    ps.setInt(3, ts.getMulChCount());
		    ps.setInt(4, ts.getPerMulScore());
		    ps.setInt(5, ts.getJudgeCount());
		    ps.setInt(6, ts.getPerJudScore());
		    ps.setInt(7, ts.getJdCount());
		    ps.setInt(8, ts.getPerJdScore());
		    ps.setInt(9, ts.getProgramCount());
		    ps.setInt(10, ts.getPerProScore());
		    ps.setInt(11, ts.getTotaltime());
		    ps.setString(12, ts.getCourse());
			count = ps.executeUpdate();
			if(count!=0)
			{
				success=true;
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, ps, rs);
			return success;
		}
	}
	public boolean deleteTest(String course)//ɾ������������Ϣ
	{
		boolean flag = false;
		int count=0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "delete from test_set where course=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, course);
			count = ps.executeUpdate();
			if(count!=0)
			{
				flag = true;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			db.CloseDB(conn, ps, rs);
			return flag;
		}
	}
}
