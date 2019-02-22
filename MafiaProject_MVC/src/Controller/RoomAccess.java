package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RoomAccess implements Initializable{
	@FXML Button btnAccess;
	@FXML TextField textfieldroomaccess;
	@FXML ComboBox<String> cmbUser;
	public Stage roomAccessstage;
	public static ArrayList<Account> anotherdbArrayList = MafiaDAO.getAccountTotalData();
	List<Account>accountList=new ArrayList<>();
	ObservableList<Account>obcomboList=FXCollections.observableArrayList();
	public static String id=null;
	public static String ip=null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		btnAccess.setOnAction(e->{handleBtnAccess();});
	}
	private void handleBtnAccess() {
		
		int i=0;
		for(Account account:anotherdbArrayList) {
			accountList.add(account);
			id=account.getAccount();
			ip=account.getAddress();
			if(textfieldroomaccess.getText().equals(id)) {
				try {
					Stage serverstage=new Stage();
					FXMLLoader loader1=new FXMLLoader(getClass().getResource("../View/server.fxml"));
					Parent root1 = loader1.load();
					
					Server serverController=loader1.getController();//
					serverController.serverStage=serverstage;
					
					Scene scene1=new Scene(root1);
					serverstage.setScene(scene1);
					
					
					serverstage.show();//���ο� â�� ����.
					serverstage.hide();
					Stage clientstage=new Stage();
					FXMLLoader loader2=new FXMLLoader(getClass().getResource("../View/client.fxml"));
					Parent root2 = loader2.load();
					GameClient clientController=loader2.getController();//
					clientController.clientstage=clientstage;
					
					Scene scene2=new Scene(root2);
					clientstage.setScene(scene2);
					
					
					clientstage.show();//���ο� â�� ����.
					roomAccessstage.close();
					
				} catch (Exception e) { RootController.callAlert("ȭ����ȯ ���� : ȭ�� ��ȯ�� ������ �ֽ��ϴ�. ���� �ٶ�");
				}
				i=1;
				break;
		}
	}if(i==0) {
		RootController.callAlert("������ ���� : ������ �̸��� Ȯ�����ּ���.");
		textfieldroomaccess.clear();
		return;
	}
	}

}
