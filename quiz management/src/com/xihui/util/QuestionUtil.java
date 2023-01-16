package com.xihui.util;
import com.xurui.dormain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.math.*;

public class QuestionUtil {
	 
	public DBAccess db = new DBAccess();
	
	public ArrayList getQuestion(String course,int count,int type)//�õ�ѡ����ķ���
	{
		ArrayList al = new ArrayList();
		int totalCount = this.getCount(type,course);//�õ�ѡ��������
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int temp = (int)(Math.random()*totalCount);//�������һ��0-������֮����������
		try{
			conn = db.getConnection();
			for(int i = 0;i<count;i++)//ȡ��
			{
				int tempCount = (temp+i)%totalCount;
				//System.out.println(tempCount);
				String sql = "select top 1 * from Question where qtype="+type+" and course='"+course+"' and Qid not in(select top "+tempCount+" Qid from Question where qtype="+type+" and course='"+course+"')";
				ps = conn.prepareStatement(sql);
				//ps.setInt(1, type);
				//ps.setInt(2, tempCount);
				//ps.setInt(3, type);
				rs = ps.executeQuery();
				Question ques = new Question();
				if(rs.next())
				{
				  ques.setId(rs.getInt(1));
				  ques.setType(1);
				  ques.setQues(rs.getString(4));
				  ques.setKeyA(rs.getString(5));
				  ques.setKeyB(rs.getString(6));
				  ques.setKeyC(rs.getString(7));
				  ques.setKeyD(rs.getString(8));
				  ques.setAnswer(rs.getString(9));
				  al.add(ques);
				}
				
			  	
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
	
	public int getCount(int type,String course)//�õ����ݿ�����Ŀ����
	{
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = db.getConnection();
			String sql = "select count(*) from Question where qtype=? and course=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, type);
			ps.setString(2, course);
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
	public int getCount()//�õ����ݿ�����Ŀ����
	{
		int count = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = db.getConnection();
			String sql = "select count(*) from Question";
			ps = conn.prepareStatement(sql);
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
    public Question getQuestionById(int Qid)
    {
    	Connection conn = null;
    	PreparedStatement ps= null;
    	ResultSet rs = null;
    	Question ques = null;
    	try{
    		conn = db.getConnection();
    		String sql = "Select * from Question where Qid=?";
    		ps = conn.prepareStatement(sql);
    		ps.setInt(1, Qid);
    		rs = ps.executeQuery();
    		if(rs.next())
    		{
    		  	ques = new Question();
    		  	ques.setId(Qid);
    		  	ques.setType(rs.getInt(2));
    		  	ques.setCourse(rs.getString(3));
    		  	ques.setQues(rs.getString(4));
    		  	ques.setKeyA(rs.getString(5));
    		  	ques.setKeyB(rs.getString(6));
    		  	ques.setKeyC(rs.getString(7));
    		  	ques.setKeyD(rs.getString(8));
    		  	ques.setAnswer(rs.getString(9));
    		}
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		
    	}finally{
    		db.CloseDB(conn, ps, rs);
    		return ques;
    	}
    	
    }
    public ArrayList getAllQuestion(int pageSize,int current)//�õ�ָ��ҳ������Ŀ��Ϣ
	{
		Connection conn = db.getConnection();
		Statement sm = null;
		ResultSet rs = null;
		ArrayList al = new ArrayList();
		try{
			conn = db.getConnection();
			String sql = "select top "+pageSize+" * from Question where Qid not in (select top "+pageSize*(current-1)+"  Qid from Question) order by Qid";
			sm = conn.createStatement();
			rs = sm.executeQuery(sql);
			while(rs.next())
			{
				Question question = new Question();
			    question.setId(rs.getInt(1));
			    question.setType(rs.getInt(2));
			    question.setCourse(rs.getString(3));
			    question.setQues(rs.getString(4));
			    question.setKeyA(rs.getString(5));
			    question.setKeyB(rs.getString(6));
			    question.setKeyC(rs.getString(7));
			    question.setKeyD(rs.getString(8));
			    question.setAnswer(rs.getString(9));
	            al.add(question);
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
    public boolean insertQuestioin(Question ques)//���������Ϣ
	{
		boolean success = false;
		int count = 0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "insert into Question(qtype,course,ques,keyA,keyB,keyC,keyD,answer) values(?,?,?,?,?,?,?,?)";
		    ps = conn.prepareStatement(sql);
		    ps.setInt(1, ques.getType());
		    ps.setString(2, ques.getCourse());
		    ps.setString(3, ques.getQues());
		    ps.setString(4, ques.getKeyA());
		    ps.setString(5, ques.getKeyB());
		    ps.setString(6, ques.getKeyC());
		    ps.setString(7, ques.getKeyD());
		    ps.setString(8, ques.getAnswer());
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
    
    public boolean updateQuestion(Question ques)//�޸�������Ϣ
	{
		boolean success = false;
		int count = 0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql="update Question set qtype=?,course=?,ques=?,keyA=?,keyB=?,keyC=?,keyD=?,answer=? where Qid=?";
		    ps = conn.prepareStatement(sql);
		    ps.setInt(1, ques.getType());
		    ps.setString(2, ques.getCourse());
		    ps.setString(3, ques.getQues());
		    ps.setString(4, ques.getKeyA());
		    ps.setString(5, ques.getKeyB());
		    ps.setString(6, ques.getKeyC());
		    ps.setString(7, ques.getKeyD());
		    ps.setString(8, ques.getAnswer());
		    ps.setInt(9,ques.getId());
		    //System.out.println(sql);
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
    
    public boolean deleteTest(int Qid)//ɾ��������Ϣ
	{
		boolean flag = false;
		int count=0;
		Connection conn = db.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			String sql = "delete from Question where Qid=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Qid);
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
    public ArrayList getCourse()//��ȡ��Ŀ
	{
		ArrayList al = new ArrayList();
		Connection conn = db.getConnection();
		Statement st = null;
		ResultSet rs = null;
		try
		{
			st = conn.createStatement();
			String sql = "select distinct course from Question";
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

}
