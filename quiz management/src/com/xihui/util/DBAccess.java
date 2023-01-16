package com.xihui.util;
import java.io.PrintWriter;
import java.sql.*;

public class DBAccess {
	private  Connection conn=null;                          //���ݿ����Ӷ���
    private PrintWriter out = null;

    static{
    	try{
    		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	}catch(ClassNotFoundException e)
    	{
    		throw new ExceptionInInitializerError(e);
    	}
    }
    public Connection getConnection()
    {
    		
    	try{ 
    		 conn=DriverManager.getConnection("jdbc:odbc:ExamOnline");  //������ݿ�����
    	   }catch(Exception e)
             {
            	 e.printStackTrace();
            	 out.print(e.toString());
             }
    		 return conn;
    }

    public void CloseDB(Connection conn,Statement sm,ResultSet rs)
    {
    	try {
    		if(rs!=null)
    		{
    			rs.close();
    		}
		}
    	catch (Exception e) 
		{
			e.printStackTrace();
			out.print("���ݿ�ر�ʧ�ܣ�");
		}finally
		{
			try{
				if(sm!=null)
				{
					sm.close();
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				out.print("���ݿ�ر�ʧ�ܣ�");
			}finally
			{
				try{
					if(conn!=null)
					{
						conn.close();
					}
					
				}catch(Exception e)
				{
					e.printStackTrace();
					out.print("���ݿ�ر�ʧ�ܣ�");
				}
			}
		}

   }

}
