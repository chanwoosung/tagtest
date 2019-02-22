package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.Account;
import Model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MafiaDAO {
   public static ArrayList<Account> dbArrayList = new ArrayList<>();
   //1.�л�����ϴ� �Լ�
   public static int  insertAccountData(Account account) {
      //1.1 ����Ÿ���̽���  �л����̺� �Է��ϴ� ������
      StringBuffer insertAccount=new StringBuffer();
      insertAccount.append("insert into mafiatbl ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("(id,password,address,gender,weight,height,hair,top,bottom,shoes) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("values ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("(?,?,?,?,?,?,?,?,?,?) ");//���� �߿�!!!!!!!!!!!!!!!!!!!!!
      
//      String insertAccount = "insert into Account "+
//      "(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date) " +
//      "values "+
//      "(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      
      //1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
      Connection con= null;
      //1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
      PreparedStatement psmt= null;
      int count=0;

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(insertAccount.toString());
         //1.4 �������� ��������Ÿ�� �����Ѵ�.  
         psmt.setString(1, account.getAccount());
         psmt.setString(2, account.getPassword());
         psmt.setString(3, account.getAddress());
         psmt.setString(4, account.getGender());
         psmt.setString(5, account.getWeight());
         psmt.setString(6, account.getHeight());
         psmt.setString(7, account.getHair());
         psmt.setString(8, account.getTop());
         psmt.setString(9, account.getBottom());
         psmt.setString(10, account.getShoes());
         
         
         
         //1.5 ��������Ÿ�� ������ �������� �����϶�.
         count=psmt.executeUpdate();
         if(count == 0) {
            RootController.callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
            return count;
         }
         
      } catch (SQLException e) {
         RootController.callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
      } finally {
         //1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
         }   
      }
      return count; 
   }

   //2.���̺��� ��ü������ ��� ���������Լ�
   public static ArrayList<Account> getAccountTotalData(){
      //1.1 ����Ÿ���̽�  �л����̺� �ִ� ���ڵ带 ��ΰ������� ������
      String selectAccount = "select * from mafiatbl";
      
      //1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
      Connection con= null;
      //1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
      PreparedStatement psmt= null;
      //1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
      ResultSet rs = null;
      

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(selectAccount);
         
         //1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
         //executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
         //executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
         rs=psmt.executeQuery();
         if(rs == null) {
            RootController.callAlert("select ���� :  select ���������� ���˹ٶ�.");
            return null;
         }
         while(rs.next()) {
            Account Account=new Account(
                  rs.getString(1),
                  rs.getString(2),
                  rs.getString(3),
                  rs.getString(4),
                  rs.getString(5),
                  rs.getString(6),
                  rs.getString(7),
                  rs.getString(8),
                  rs.getString(9),
                  rs.getString(10)
                  
                    );
            dbArrayList.add(Account);
         }
         
         
      } catch (SQLException e) {
         RootController.callAlert("���� ���� : ����Ÿ���̽� ���Խ����߾��.. ���˹ٶ�.");
      } finally {
         //1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
         }   
      }      
      return dbArrayList;
   }

   //3. ���̺�信�� ������ ���ڵ带 ����Ÿ���̽����� �����ϴ� �Լ�. 
   public static int deleteAccountData(String no) {
      //1.1 ����Ÿ���̽�  �л����̺� �ִ� ���ڵ带 �����ϴ� ������
      String deleteAccount = "delete from Account where no = ? ";
      
      //1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
      Connection con= null;
      //1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
      PreparedStatement psmt= null;
      //1.4 �������� �����ϰ��� �����;��� ���ڵ带 ����ִ� ���ڱ� ��ü
      int count =0;

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(deleteAccount);
         psmt.setString(1, no);
         
         //1.5 ��������Ÿ�� ������ �������� �����϶�.(������ ġ�°�)
         //executeQuery() �������� �����ؼ� ����� �����ö� ����ϴ� ������
         //executeUpdate() �������� �����ؼ� ���̺� ������ �Ҷ� ����ϴ� ������
         count=psmt.executeUpdate();
         if(count == 0) {
            RootController.callAlert("delete ���� :  delete ���������� ���˹ٶ�.");
            return count;
         }

      } catch (SQLException e) {
         RootController.callAlert("delete ���� : ����Ÿ���̽� delete�����߾��.. ���˹ٶ�.");
      } finally {
         //1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
         }   
      }   
      return count;
   }

   //4. ���̺�信�� ������ ���ڵ带 ����Ÿ���̽� ���̺� �����ϴ� �Լ�. 
   public static int updateAccountData(Account account) {
      //1.1 ����Ÿ���̽���  �л����̺� �����ϴ� ������
         StringBuffer updateAccount =new StringBuffer();
         updateAccount.append("update Account set ");
         updateAccount.append("kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=?, imagepath=?, where no=? ");
      
//            String updateAccount = "update Account set "+
//            "kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
            
            //1.2  ����Ÿ���̽� Connetion�� �����;� �Ѵ�. 
            Connection con= null;
            //1.3 �������� �����ؾ߸� Statement�� �������Ѵ�. 
            PreparedStatement psmt= null;
            int count=0;

            try {
               con=DBUtillity.getConnection();
               psmt = con.prepareStatement(updateAccount.toString());
               //1.4 �������� ��������Ÿ�� �����Ѵ�.  
               psmt.setString(1, account.getAccount());
               psmt.setString(2, account.getPassword());
               psmt.setString(3, account.getAddress());
               psmt.setString(4, account.getGender());
               psmt.setString(5, account.getWeight());
               psmt.setString(6, account.getHeight());
               psmt.setString(7, account.getHair());
               psmt.setString(8, account.getTop());
               psmt.setString(9, account.getBottom());
               psmt.setString(10, account.getShoes());
               
               //1.5 ��������Ÿ�� ������ �������� �����϶�.
               count=psmt.executeUpdate();
               if(count == 0) {
                  RootController.callAlert("���� �������� : ���� ���������� ���˹ٶ�.");
                  return count;
               }
               
            } catch (SQLException e) {
               RootController.callAlert("���� ���� : ����Ÿ���̽� ���������߾��.. ���˹ٶ�.");
            } finally {
               //1.6 �ڿ���ü�� �ݾƾ��Ѵ�. 
                try {
                  if(psmt != null) {psmt.close();}
                  if(con != null) { con.close(); }
               } catch (SQLException e) {
                  RootController.callAlert("�ڿ��ݱ���� : psmt, con �ݱ����.");
               }   
            }
      return count;
   }
}