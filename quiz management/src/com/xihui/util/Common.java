package com.xihui.util;

public class Common {
     public static String getSubString(String str,int i)//��ȡ�ָ���
     {
    	 if(str.length()<=i)
    	 {
    		 return str; 
    	 }else
    	 {
    		 str = str.substring(0, i);
    		 return str+"...";
    	 }
    	 
     }
     public static String getType(int type)//�õ������ָ���
     {
    	  String str_type = "";
          if(type==1)
          {
            str_type="������";
          }else if(type==2)
          {
            str_type="��ѡ��";
          }else if(type==3)
          {
            str_type="�ж���";
          }else if(type==4)
          {
            str_type="�����";
          }else if(type==5)
          {
            str_type="�����";
          }
    	 return str_type;
     }
}
