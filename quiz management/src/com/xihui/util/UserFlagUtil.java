package com.xihui.util;
import java.sql.*;

public class UserFlagUtil {
   public DBAccess db = new DBAccess();
   public int getFlag(String Uid,String course)//�õ�������Ϣ
   {
	   int flag=2;//2Ϊ���ݿ�����޼�¼
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   try{
		   String sql = "select flag from userflag where Uid=? and course=?";
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, Uid);
		   ps.setString(2, course);
		   rs = ps.executeQuery();
		   if(rs.next())
		   {
			   flag = rs.getInt(1);
			   
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
   
   public boolean setFlag(String Uid,String course)//���ý�����Ϣ
   {
	   boolean flag=false;
	   Connection conn = db.getConnection();
	   PreparedStatement ps = null;
	   ResultSet rs = null;
	   String sql="";
	   try{
		   if(this.getFlag(Uid, course)!=2)
		   {
			   sql = "update userflag set flag=1 where Uid=? and course=?";
		   }else
		   {
		    sql = "insert into userflag(Uid,course,flag) values(?,?,1)";
		   }
		   ps = conn.prepareStatement(sql);
		   ps.setString(1, Uid);
		   ps.setString(2, course);
		   int i = ps.executeUpdate();
		   if(i>0)
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
