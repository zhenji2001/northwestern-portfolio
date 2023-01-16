package com.xihui.util;

import java.sql.*;
import java.util.ArrayList;

import com.xurui.dormain.*;
public class RecordUtil {
	public DBAccess db = new DBAccess();
	
   public Record getRecord(String Uid,String course)//��ȡ�÷���Ϣ
   {
	   Record record = null;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select * from record where Uid=? and course=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, Uid);
		   ps.setString(2, course);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			   record = new Record();
			   record.setUid(rs.getString(1));
			   record.setCourse(rs.getString(2));
			   record.setScore(rs.getFloat(3));
			   record.setSingle(rs.getFloat(4));
			   record.setMultiply(rs.getFloat(5));
			   record.setJudge(rs.getFloat(6));
			   record.setJd(rs.getFloat(7));
			   record.setProgram(rs.getFloat(8));
		   }
		   
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }finally
	   {
		   db.CloseDB(conn, ps, rs);
		   return record;
	   }
	   
   }
   
   public boolean getUser(String Uid,String course)//�ж�ѧ���ɼ��Ƿ����
   {
	   boolean flag = false;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select count(*) from record where Uid=? and course=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, Uid);
		   ps.setString(2, course);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			   if(rs.getInt(1)!=0)
			   {
				   flag = true;
			   }
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
   
   public boolean updateRecord(Record record)
   {
	   boolean flag = false;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "";
		   if(this.getUser(record.getUid(), record.getCourse()))//�û�����
		   {
			sql = "update record set score=?,single=?,multiply=?,judge=?,jd=?,program=? where Uid=? and course=?";
		   }else//�û�������
		   {
			sql = "insert into record(score,single,multiply,judge,jd,program,Uid,course) values(?,?,?,?,?,?,?,?)";  
		   }
		   ps = conn.prepareStatement(sql);
		   ps.setFloat(1, record.getScore());
		   ps.setFloat(2, record.getSingle());
		   ps.setFloat(3, record.getMultiply());
		   ps.setFloat(4, record.getJudge());
		   ps.setFloat(5, record.getJd());
		   ps.setFloat(6, record.getProgram());
		   ps.setString(7, record.getUid());
		   ps.setString(8, record.getCourse());
		   int i = ps.executeUpdate();
		   if(i!=0)
		   {
			   flag = true;
		   }else
		   {
			   flag = false;
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
  
   public int getPass(String course)//��ȡ��������
   {
	   int count = 0;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select count(*) from record where course=? and score>=60";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, course);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			 count = rs.getInt(1);  
		   }
		   
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }finally
	   {
		   db.CloseDB(conn, ps, rs);
		   return count;
	   }
	   
   }

   public int getCount(String course)//��ȡ������
   {
	   int count = 0;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select count(*) from record where course=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, course);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			 count = rs.getInt(1);  
		   }
		   
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }finally
	   {
		   db.CloseDB(conn, ps, rs);
		   return count;
	   }
	   
   }
   
   public int getCountByScore(String course,int score)//�����������
   {
	   int count = 0;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select count(*) from record where course=? and score>=? and score<?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, course);
		   ps.setInt(2, score);
		   ps.setInt(3, score+10);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			 count = rs.getInt(1);  
		   }
		   
	   }catch(Exception e)
	   {
		   e.printStackTrace();
	   }finally
	   {
		   db.CloseDB(conn, ps, rs);
		   return count;
	   }
	   
   }
   
   public ArrayList getAll(String Uid)//�õ�ѧ����ȫ���ɼ�
   {
	   ArrayList al = new ArrayList();
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try
	   {
		   String sql = "select * from record where Uid=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, Uid);
		   rs = ps.executeQuery();
		   while(rs.next())
		   {
			   Record record = new Record();
			   record.setUid(rs.getString(1));
			   record.setCourse(rs.getString(2));
			   record.setScore(rs.getFloat(3));
			   record.setSingle(rs.getFloat(4));
			   record.setMultiply(rs.getFloat(5));
			   record.setJudge(rs.getFloat(6));
			   record.setJd(rs.getFloat(7));
			   record.setProgram(rs.getFloat(8));
			   al.add(record);
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
