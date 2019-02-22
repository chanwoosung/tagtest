package application;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import Controller.GameClient;
import Controller.MafiaDAO;
import Controller.RootController;
import Controller.Server;

import Model.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		FXMLLoader loader= new FXMLLoader(getClass().getResource("../View/login.fxml"));
		Parent root=loader.load();
		RootController rootController=loader.getController();
		rootController.primaryStage=primaryStage;
		Scene scene=new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("add.css").toString());
		primaryStage.setScene(scene);
		primaryStage.setTitle("MAFIA GAME");
		primaryStage.show();

	}
/*try {
		
		Stage waitingstage=new Stage();
		FXMLLoader loader=new FXMLLoader(getClass().getResource("../View/waitingroom.fxml"));
		Parent root = loader.load();
		
		WaitingRoom waitingRoomController=loader.getController();//
		waitingRoomController.waitingstage=waitingstage;
		
		Scene scene=new Scene(root);
		waitingstage.setScene(scene);
		//primaryStage.close();//�α����Ѹ� ó��â�� �ݴ´�.
		primaryStage.close();
		waitingstage.show();//���ο� â�� ����.
		RootController.callAlert("ȭ����ȯ���� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
		Stage serverstage=new Stage();
		FXMLLoader loader1=new FXMLLoader(getClass().getResource("../View/server.fxml"));
		Parent root1 = loader1.load();
		
		Server serverController=loader1.getController();//
		serverController.serverStage=serverstage;
		
		Scene scene1=new Scene(root1);
		serverstage.setScene(scene1);
		//primaryStage.close();//�α����Ѹ� ó��â�� �ݴ´�.
		primaryStage.close();
		serverstage.show();//���ο� â�� ����.
		RootController.callAlert("ȭ����ȯ���� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
		
		Stage clientstage=new Stage();
		FXMLLoader loader2=new FXMLLoader(getClass().getResource("../View/client.fxml"));
		Parent root2 = loader2.load();
		Client clientController=loader2.getController();//
		clientController.clientstage=clientstage;
		
		Scene scene2=new Scene(root2);
		clientstage.setScene(scene2);
		//primaryStage.close();//�α����Ѹ� ó��â�� �ݴ´�.
		primaryStage.close();
		clientstage.show();//���ο� â�� ����.
		RootController.callAlert("ȭ����ȯ���� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
		
		Socket socket=new Socket();
		socket.connect(new InetSocketAddress("192.168.0.221", 5004));
		InputStream is=socket.getInputStream();
		InputStreamReader isr=new InputStreamReader(is);
		BufferedReader br=new BufferedReader(isr);
		OutputStream os=socket.getOutputStream();
		PrintWriter pw=new PrintWriter(os, true);
		
	} catch (Exception e) { RootController.callAlert("ȭ����ȯ ���� : ȭ�� ��ȯ�� ������ �ֽ��ϴ�. ���� �ٶ�");}*/
}
