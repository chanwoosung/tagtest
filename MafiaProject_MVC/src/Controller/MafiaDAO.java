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
   //1.학생등록하는 함수
   public static int  insertAccountData(Account account) {
      //1.1 데이타베이스에  학생테이블에 입력하는 쿼리문
      StringBuffer insertAccount=new StringBuffer();
      insertAccount.append("insert into mafiatbl ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("(id,password,address,gender,weight,height,hair,top,bottom,shoes) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("values ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
      insertAccount.append("(?,?,?,?,?,?,?,?,?,?) ");//띄어쓰기 중요!!!!!!!!!!!!!!!!!!!!!
      
//      String insertAccount = "insert into Account "+
//      "(no,name,grade,ban,gender,kor,eng,mat,sci,soc,mus,total,avg,date) " +
//      "values "+
//      "(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
      
      //1.2  데이타베이스 Connetion을 가져와야 한다. 
      Connection con= null;
      //1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
      PreparedStatement psmt= null;
      int count=0;

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(insertAccount.toString());
         //1.4 쿼리문에 실제데이타를 연결한다.  
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
         
         
         
         //1.5 실제데이타를 연결한 쿼리문을 실행하라.
         count=psmt.executeUpdate();
         if(count == 0) {
            RootController.callAlert("삽입 쿼리실패 : 삽입 쿼리문실패 점검바람.");
            return count;
         }
         
      } catch (SQLException e) {
         RootController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
      } finally {
         //1.6 자원객체를 닫아야한다. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
         }   
      }
      return count; 
   }

   //2.테이블에서 전체내용을 모두 가져오는함수
   public static ArrayList<Account> getAccountTotalData(){
      //1.1 데이타베이스  학생테이블에 있는 레코드를 모두가져오는 쿼리문
      String selectAccount = "select * from mafiatbl";
      
      //1.2  데이타베이스 Connetion을 가져와야 한다. 
      Connection con= null;
      //1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
      PreparedStatement psmt= null;
      //1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
      ResultSet rs = null;
      

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(selectAccount);
         
         //1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
         //executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
         //executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
         rs=psmt.executeQuery();
         if(rs == null) {
            RootController.callAlert("select 실패 :  select 쿼리문실패 점검바람.");
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
         RootController.callAlert("삽입 실패 : 데이타베이스 삽입실패했어요.. 점검바람.");
      } finally {
         //1.6 자원객체를 닫아야한다. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
         }   
      }      
      return dbArrayList;
   }

   //3. 테이블뷰에서 선택한 레코드를 데이타베이스에서 삭제하는 함수. 
   public static int deleteAccountData(String no) {
      //1.1 데이타베이스  학생테이블에 있는 레코드를 삭제하는 쿼리문
      String deleteAccount = "delete from Account where no = ? ";
      
      //1.2  데이타베이스 Connetion을 가져와야 한다. 
      Connection con= null;
      //1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
      PreparedStatement psmt= null;
      //1.4 쿼리문을 실행하고나서 가져와야할 레코드를 담고있는 보자기 객체
      int count =0;

      try {
         con=DBUtillity.getConnection();
         psmt = con.prepareStatement(deleteAccount);
         psmt.setString(1, no);
         
         //1.5 실제데이타를 연결한 쿼리문을 실행하라.(번개를 치는것)
         //executeQuery() 쿼리문을 실행해서 결과를 가져올때 사용하는 번개문
         //executeUpdate() 쿼리문을 실행해서 테이블에 저장을 할때 사용하는 번개문
         count=psmt.executeUpdate();
         if(count == 0) {
            RootController.callAlert("delete 실패 :  delete 쿼리문실패 점검바람.");
            return count;
         }

      } catch (SQLException e) {
         RootController.callAlert("delete 실패 : 데이타베이스 delete실패했어요.. 점검바람.");
      } finally {
         //1.6 자원객체를 닫아야한다. 
          try {
            if(psmt != null) {psmt.close();}
            if(con != null) { con.close(); }
         } catch (SQLException e) {
            RootController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
         }   
      }   
      return count;
   }

   //4. 테이블뷰에서 수정한 레코드를 데이타베이스 테이블에 수정하는 함수. 
   public static int updateAccountData(Account account) {
      //1.1 데이타베이스에  학생테이블에 수정하는 쿼리문
         StringBuffer updateAccount =new StringBuffer();
         updateAccount.append("update Account set ");
         updateAccount.append("kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=?, imagepath=?, where no=? ");
      
//            String updateAccount = "update Account set "+
//            "kor=?,eng=?,mat=?,sci=?,soc=?,mus=?,total=?,avg=?,date=? where no=? ";
            
            //1.2  데이타베이스 Connetion을 가져와야 한다. 
            Connection con= null;
            //1.3 쿼리문을 실행해야말 Statement를 만들어야한다. 
            PreparedStatement psmt= null;
            int count=0;

            try {
               con=DBUtillity.getConnection();
               psmt = con.prepareStatement(updateAccount.toString());
               //1.4 쿼리문에 실제데이타를 연결한다.  
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
               
               //1.5 실제데이타를 연결한 쿼리문을 실행하라.
               count=psmt.executeUpdate();
               if(count == 0) {
                  RootController.callAlert("수정 쿼리실패 : 수정 쿼리문실패 점검바람.");
                  return count;
               }
               
            } catch (SQLException e) {
               RootController.callAlert("수정 실패 : 데이타베이스 수정실패했어요.. 점검바람.");
            } finally {
               //1.6 자원객체를 닫아야한다. 
                try {
                  if(psmt != null) {psmt.close();}
                  if(con != null) { con.close(); }
               } catch (SQLException e) {
                  RootController.callAlert("자원닫기실패 : psmt, con 닫기실패.");
               }   
            }
      return count;
   }
}