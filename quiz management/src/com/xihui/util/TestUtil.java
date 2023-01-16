package com.xihui.util;
import java.sql.*;
import com.xurui.dormain.*;
import java.util.*;

public class TestUtil {
	DBAccess db = new DBAccess();
	public boolean insertTest(ArrayList al)
	{
		boolean flag = false;
		
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			conn.setAutoCommit(false);//������(�ر��Զ��ύ)
		    String sql = "insert into test(Uid,Qid,Tnumber,course,Qtype,answer,score) values(?,?,?,?,?,?,?)";
		    ps = conn.prepareStatement(sql);
		
	    	for(int i=0;i < al.size();i++)
		    {
			 Test ts = (Test)al.get(i);
			 ps.setString(1, ts.getUid());
			 ps.setInt(2, ts.getQid());
			 ps.setInt(3, ts.getTnumber());
			 ps.setString(4, ts.getCourse());
			 ps.setInt(5, ts.getType());
			 ps.setString(6, ts.getStuAnswer());
			 ps.setFloat(7, ts.getScore());
		     ps.addBatch();
		    }
	    	int[] is = ps.executeBatch();
	    	conn.commit();//�ύ����
	    	if(is!=null)
	    	{
	    		flag = true;
	    	}
		}catch(Exception e)
		{
			conn.rollback();//�����쳣�ع�����
			e.printStackTrace();
			
		}finally{
		db.CloseDB(conn, ps, rs);	
		return flag;
		}
	}
	
	public ArrayList getStudent(String course)
	{
	   Connection  conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   ArrayList al = new ArrayList();
	   try{
		   String sql = "select distinct b.name,a.Uid  from test as a left outer join UserInfo as b on a.Uid = b.Uid where a.course=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, course);
		   rs = ps.executeQuery();
		   while(rs.next())
		   {
			   User user = new User();
			   user.setName(rs.getString(1));
			   user.setId(rs.getString(2));
			   al.add(user);
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
	
	public ArrayList getTest(String Uid,String course,int type)//����Ծ������Ӧ���͵���
	{
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		try{
			String sql = "select * from test where Uid=? and course=? and Qtype=? order by Tnumber";
			ps = conn.prepareStatement(sql);
			ps.setString(1, Uid);
			ps.setString(2, course);
			ps.setInt(3, type);
			rs = ps.executeQuery();
			while(rs.next())
			{
				Test ts = new Test();
				ts.setUid(Uid);
				ts.setCourse(course);
				ts.setQid(rs.getInt(2));
				ts.setStuAnswer(rs.getString(3));
				ts.setScore(rs.getFloat(5));
				ts.setType(type);
				ts.setTnumber(rs.getInt(7));
				al.add(ts);
				
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
	
	public boolean updateTest(String Uid,int Qid,float score)//�޸��Ծ������Ӧ����ķ���
	{
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean flag = false;
		try{
			String sql = "update Test set score=? where Uid=? and Qid=?";
			ps = conn.prepareStatement(sql);
			ps.setFloat(1, score);
			ps.setString(2, Uid);
			ps.setInt(3, Qid);
			int i = ps.executeUpdate();
			if(i!=0)
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
