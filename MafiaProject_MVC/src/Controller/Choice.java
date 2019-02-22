package Controller;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Choice implements Initializable{
public Stage choicestage;
@FXML ImageView imageChoiceMaster;
@FXML ImageView imageChoiceCustomer;
List<GameClient> list = new Vector<GameClient>();
private Thread mainThread;
public Stage serverStage;
private ServerSocket serversocket;
@Override
public void initialize(URL location, ResourceBundle resources) {
	imageChoiceMaster.setOnMouseClicked(e->{makeroom();});
	imageChoiceCustomer.setOnMouseClicked(e->{enterroom();});
}
private void enterroom() {
	try {
	Stage roomAccessstage=new Stage();
	FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/roomaccess.fxml"));
	Parent root = loader.load();
	
	RoomAccess roomAccessController=loader.getController();//
	roomAccessController.roomAccessstage=roomAccessstage;
	
	Scene scene=new Scene(root);
	roomAccessstage.setScene(scene);
	//primaryStage.close();//로그인한면 처음창을 닫는다.
	
	roomAccessstage.show();//새로운 창을 띄운다.
	} catch (Exception e) { RootController.callAlert("화면전환 오류 : 화면 전환에 문제가 있습니다. 검토 바람");}
}
private void makeroom() {
	try {
		
		/*Stage waitingstage=new Stage();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/waitingroom.fxml"));
		Parent root = loader.load();
		
		WaitingRoom waitingRoomController=loader.getController();//
		waitingRoomController.waitingstage=waitingstage;
		
		Scene scene=new Scene(root);
		waitingstage.setScene(scene);
		//primaryStage.close();//로그인한면 처음창을 닫는다.
		choicestage.close();
		waitingstage.show();//새로운 창을 띄운다.
		RootController.callAlert("화면전환성공 : 메인화면으로 전환되었습니다.");*/
		Stage serverstage=new Stage();
		FXMLLoader loader1=new FXMLLoader(getClass().getResource("../View/server.fxml"));
		Parent root1 = loader1.load();
		
		Server serverController=loader1.getController();//
		serverController.serverStage=serverstage;
		
		Scene scene1=new Scene(root1);
		serverstage.setScene(scene1);
		//primaryStage.close();//로그인한면 처음창을 닫는다.
		
		serverstage.show();//새로운 창을 띄운다.
		/*RootController.callAlert("화면전환성공 : 서버화면으로 전환되었습니다.");*/
		
		/*Stage clientstage=new Stage();
		FXMLLoader loader2=new FXMLLoader(getClass().getResource("../View/client.fxml"));
		Parent root2 = loader2.load();
		GameClient clientController=loader2.getController();//
		clientController.clientstage=clientstage;
		
		Scene scene2=new Scene(root2);
		clientstage.setScene(scene2);
		//primaryStage.close();//로그인한면 처음창을 닫는다.
		
		clientstage.show();//새로운 창을 띄운다.
		RootController.callAlert("화면전환성공 : 서버화면으로 전환되었습니다.");*/
		
		
	} catch (Exception e) { RootController.callAlert("화면전환 오류 : 화면 전환에 문제가 있습니다. 검토 바람");}
	
}


	

	
}