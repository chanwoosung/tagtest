package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;

import javax.management.openmbean.OpenDataException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameClient implements Initializable {

	public Stage clientstage;
	Socket socket;
	@FXML
	TextArea textClientServer;
	@FXML
	TextField clientTextField;
	@FXML
	Button btnSay;
	@FXML
	Button btnSuicide;
	@FXML
	Button btnHow;
	@FXML
	Button btnReady;
	@FXML
	ListView<String> clientList;
	@FXML
	ListView<String> clientDead;
	@FXML
	ProgressBar timeBar;
	@FXML
	private Label timerLabel;
	private BufferedReader br;
	private PrintWriter pw;
	private static Integer STARTTIME = 30;
	boolean flag =false;
	boolean mafia=false;
	boolean die=false;
	List<String>cliList=new ArrayList<>();
	List<String>mafiList=new ArrayList<>();
	ObservableList<String>clientobList=FXCollections.observableArrayList();
	ObservableList<String>mafiaobList=FXCollections.observableArrayList();
	
	String randString=null;

	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME * 100);

	private Timeline timeline;
	private int interval;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnReady.setOnAction(e -> {
			startClient();
		});
		btnSay.setOnAction(e -> {
			send(clientTextField.getText());
		});
		clientTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					if(clientTextField.getText().length()<3) {event.consume();}else {
					send(clientTextField.getText());
					clientTextField.clear();}
				}

			}
		});
		textClientServer.setEditable(false);
		btnSuicide.setOnAction(e -> {
			sendtarget();
		});
		btnHow.setDisable(true);
		btnSuicide.setDisable(true);
		btnHow.setOnAction(e->{sendwhoismafia();});
		
		
	}

	private void sendtarget() {

		String votehuman=clientList.getSelectionModel().getSelectedItem().toString();
		send("Tar"+votehuman);
		btnHow.setDisable(true);
		btnSuicide.setDisable(true);
		flag=true;
		
	}

	private void sendwhoismafia() {
		String votehuman=clientList.getSelectionModel().getSelectedItem().toString();
		send("%2%"+votehuman);
		btnHow.setDisable(true);
		flag=true;
	}


	/*public void timer(int interval) {
		this.interval=interval;
		Runnable runnable =new Runnable() {
			@Override
				public void run() {
						for(int i=0; i<= interval; i++) {
							double progress =  (double)i / interval;
							timeBar.setProgress(progress);
							try {
								Thread.sleep(1000);
								if(i==interval) {
									Platform.runLater(()->{
										btnHow.setDisable(true);
										flag=true;
									});
									send("Nig");
								}
							} catch (InterruptedException e) {
								RootController.callAlert("���ǾƸ� ������� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
							}
						}
			};
		};
	//	timeBar.progressProperty().bind(task.progressProperty());
	//	timerLabel.textProperty().bind(task.messageProperty());
		Thread thread=new Thread(runnable);
		
		thread.start();
	}
	public void morningtimer(int interval) {
		this.interval=interval;
		Runnable runnable =new Runnable() {
			@Override
				public void run() {
						for(int i=0; i<= interval; i++) {
							double progress =  (double)i / interval;
							timeBar.setProgress(progress);
							try {
								Thread.sleep(1000);
								if(i==interval) {
									Platform.runLater(()->{
										send("Deb");
										textClientServer.appendText("��ǥ�� �����մϴ�.");
									});
									
								}
							} catch (InterruptedException e) {
								RootController.callAlert("���ǾƸ� ������� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
							}
						}
			};
		};
	//	timeBar.progressProperty().bind(task.progressProperty());
	//	timerLabel.textProperty().bind(task.messageProperty());
		Thread thread=new Thread(runnable);
		
		thread.start();
	}
	public void nighttimer(int interval) {
		this.interval=interval;
		Runnable runnable =new Runnable() {
			@Override
				public void run() {
						for(int i=0; i<= interval; i++) {
							double progress =  (double)i / interval;
							timeBar.setProgress(progress);
							try {
								Thread.sleep(1000);
								if(i==interval) {
									Platform.runLater(()->{
										textClientServer.appendText("Ÿ���� ���߽��ϴ�.");
										btnSuicide.setDisable(true);
										
									});
									send("MEV");
								}
							} catch (InterruptedException e) {
								RootController.callAlert("���ǾƸ� ������� : ����ȭ������ ��ȯ�Ǿ����ϴ�.");
							}
						}
			};
		};
	//	timeBar.progressProperty().bind(task.progressProperty());
	//	timerLabel.textProperty().bind(task.messageProperty());
		Thread thread=new Thread(runnable);
		
		thread.start();
	}*/

	/*private void openVote() {
		try {
		Stage votestage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/vote.fxml"));
		Parent root = loader.load();
		
		Vote voteController = loader.getController();//
		voteController.votestage = votestage;
		
		Scene scene = new Scene(root);
		votestage.setScene(scene);
		// primaryStage.close();//�α����Ѹ� ó��â�� �ݴ´�.
		
		votestage.show();// ���ο� â�� ����.
	
		}catch(Exception e) {
			RootController.callAlert("ȭ����ȯ���� : ȭ����ȯ����");
	}
	}
*/
	public void send(String sendMessage) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);

					pw.println(RootController.id +":"+ sendMessage);
					
					pw.flush();
					/*Platform.runLater(() -> {
						 textClientServer.appendText(sendMessage);
					});*/
				} catch (IOException e) {
					Platform.runLater(() -> {
						textClientServer.appendText("���� �Ұ� \n");
					});
					stopClient();
				}

			}
		};// end of runnable

		Thread thread = new Thread(runnable);
		thread.start();
	}
	/*public void protocallSend(String number,String protocallMessage) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				try {
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os);

					pw.println("protocall"+number+protocallMessage);
					System.out.println();
					pw.flush();
					Platform.runLater(() -> {
						protocallSend(number, protocallMessage);
					});
				} catch (Exception e) {
					Platform.runLater(() -> {
						textClientServer.appendText("���� �Ұ� \n");
					});
					stopClient();
				}

			}
		};// end of runnable

		Thread thread = new Thread(runnable);
		thread.start();
	}*/

	public void stopClient() {
		if (!socket.isClosed() && socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
		Platform.runLater(() -> {
			textClientServer.appendText("���� ���� �ߴ� \n");

		});
	}
	public void RandomPicked() {
		
		double randnumber=Math.random();
		int intrand=(int)(randnumber*(1000-1+1))+1;
		send("%1%"+intrand);
		/*for(Client client:list) {
			client.send(randString);
		}*/
	}
	private void startClient() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				socket = new Socket();
				try {
					socket.connect(new InetSocketAddress(RoomAccess.ip, 5004));
					Platform.runLater(() -> {
						// textClientServer.appendText("����Ϸ�" + socket.getRemoteSocketAddress());

					});
				} catch (IOException e) {
					if (!socket.isClosed()) {
						stopClient();
					}
					return;// ��ŸƮ Ŭ���̾�Ʈ�� ����������.
				}
				// �����͸� �б� ���� ����̴�.
				while (true) {
					try {
						InputStream is = socket.getInputStream();
						InputStreamReader isr = new InputStreamReader(is);
						BufferedReader br = new BufferedReader(isr);
						String receiveMessage = br.readLine();
						System.out.println(receiveMessage+"++++++");
						
						String order = receiveMessage.substring(0,4);
						System.out.println(order);
						btnReady.setDisable(true);
						switch (order) {
						
						case "Mafi":
							String mafianame1=receiveMessage.substring(4,8);
							String mafianame2=receiveMessage.substring(8,12);
							System.out.println(mafianame1);
							System.out.println(mafianame2);
							mafiList.add(mafianame1);
							mafiList.add(mafianame2);
							for(String str:mafiList) {
								mafiaobList.add(str);
							}
							if(RootController.id.equals(mafianame1)||RootController.id.equals(mafianame2)) {
								mafia=true;
								System.out.println(mafia);
								Platform.runLater(()->{
									textClientServer.appendText("����� ���Ǿ��Դϴ�.\n�ù������̰�, ���ø� �տ� ��������.\n");
									
									clientDead.setItems(mafiaobList);
									clientDead.setVisible(true);
								});
							}else {
								mafia=false;
								System.out.println(mafia);
								Platform.runLater(() -> {
									textClientServer.appendText("����� �ù��Դϴ�.\n���ǾƸ� ã�� ó���ϰ�, ��Ƴ�������.\n");
									clientDead.setItems(mafiaobList);
									clientDead.setVisible(false);
									
								});
								
								
							}
							System.out.println(die);
							System.out.println(mafia);
							break;
					

						case "GAME":
							Platform.runLater(() -> {
								textClientServer.appendText("������ ���۵˴ϴ�.\n");
								textClientServer.appendText("ä���� 3���� �̻���� Ÿ���ΰ����մϴ�.\n");
								textClientServer.appendText("������ ������ ���Ǿư� ���˸� ���������ֽ��ϴ�\n�ùΰ� ����� ���� ���ǾƸ� �����ϰ�,\n���ǾƸ� ó���ϰ� ��Ƴ�������.\n");
								System.out.println(RootController.id);
								btnReady.setText("NowGaming");
								RandomPicked();
								btnHow.setDisable(false);
							});
							System.out.println(die);
							
							break;
						case "List":
							String Listname1=receiveMessage.substring(4,8);
							String Listname2=receiveMessage.substring(8,12);
							String Listname3=receiveMessage.substring(12,16);
							String Listname4=receiveMessage.substring(16,20);
							String Listname5=receiveMessage.substring(20,24);
							String Listname6=receiveMessage.substring(24,28);
							String Listname7=receiveMessage.substring(28,32);
							
							System.out.println(Listname1);
							System.out.println(Listname2);
							System.out.println(Listname3);
							cliList.add(Listname1);
							cliList.add(Listname2);
							cliList.add(Listname3);
							cliList.add(Listname4);
							cliList.add(Listname5);
							cliList.add(Listname6);
							cliList.add(Listname7);
							
							for(String str:cliList) {
								clientobList.add(str);
							}
							Platform.runLater(()->{
							clientList.setItems(clientobList);
							});
							System.out.println(die);
							break;
						case "EndM":
							Platform.runLater(()->{
								send("EDM");
							});
							System.out.println(die);
							
							break;
						case "EndV":
							Platform.runLater(()->{
								send("EDV");
							});
							System.out.println(die);
							
							break;
						case "Kill":
							String killmanname=receiveMessage.substring(4);
							System.out.println(die);
							System.out.println(mafia);
						
							if(RootController.id.equals(killmanname)) {
								die=true;
								
								Platform.runLater(()->{
									textClientServer.appendText(killmanname+"�� �ִ� ��ǥ�� �޾ҽ��ϴ�.\n");
									textClientServer.appendText("���� �Ǿ����ϴ�. ���Ǿƴ� ����� ���� Ÿ���� ����ּ���.\n");
									clientTextField.setDisable(true);
									btnSay.setDisable(true);
									btnSuicide.setDisable(true);
									btnHow.setDisable(true);
									send("Con");
								});
							}else if(!(RootController.id.equals(killmanname))) {
								die=false;
								if(mafia==true) {
								Platform.runLater(()->{
									textClientServer.appendText(killmanname+"�� �ִ� ��ǥ�� �޾ҽ��ϴ�.\n");
									textClientServer.appendText("���� �Ǿ����ϴ�. ���Ǿƴ� ����� ���� Ÿ���� ����ּ���.\n");
									send("Con");
									btnSuicide.setDisable(false);
									btnHow.setDisable(true);
									});
								}else if(mafia==false) {
									Platform.runLater(()->{
										textClientServer.appendText(killmanname+"�� �ִ� ��ǥ�� �޾ҽ��ϴ�.\n");
										textClientServer.appendText("���� �Ǿ����ϴ�. ���Ǿƴ� ����� ���� Ÿ���� ����ּ���.\n");
										send("Con");
										btnSuicide.setDisable(true);
										btnHow.setDisable(true);
										});
									
								}
							}
								
							
							
							if(cliList.contains(killmanname)) {
								cliList.remove(killmanname);
							}
							
							break;
							
						case "Revo":
							if(die==false) {
							Platform.runLater(()->{
								textClientServer.appendText("��ǥ��� ��ǥ���� �ߺ��Ǿ� ����ǥ����\n");
								btnHow.setDisable(false);
								btnSuicide.setDisable(true);
							});
							}
							break;
						
						case "whis":
							String mafiamessage=receiveMessage.substring(4);
							System.out.println(mafia);
							if(mafia==true) {
								Platform.runLater(()->{
									textClientServer.appendText(mafiamessage+"\n");
								});
							}else if(mafia==false) {
								Platform.runLater(()->{
									textClientServer.appendText("\n");
								});
							}
							break;
						
						case "END!":
							Platform.runLater(()->{
								textClientServer.appendText("���Ǿ��� �¸��� ������ �������ϴ�!\n�÷��� ���ּż� �����մϴ�!");
								btnHow.setText("Playing");
								btnReady.setText("Thanks");
								btnSuicide.setText("for");
								btnReady.setDisable(true);
								btnSuicide.setDisable(true);
							});
							break;
						case "!END":
							Platform.runLater(()->{
								textClientServer.appendText("�ù��� �¸��� ������ �������ϴ�!\n�÷��� ���ּż� �����մϴ�!");
								btnHow.setText("Playing");
								btnReady.setText("Thanks");
								btnSuicide.setText("for");
								btnReady.setDisable(true);
								btnSuicide.setDisable(true);
							});
							break;
						case "ReMu":
						if(mafia==true) {
							Platform.runLater(()->{
								textClientServer.appendText("���Ǿ��� Ÿ�� ������ ������ Ÿ���� �缳���մϴ�.\n");
								btnHow.setDisable(true);
								btnSuicide.setDisable(false);
							});
							}else if(mafia==false){
								Platform.runLater(()->{
									textClientServer.appendText("���Ǿ��� Ÿ�� ������ ������ Ÿ���� �缳���մϴ�.\n");
									btnHow.setDisable(true);
									btnSuicide.setDisable(true);
								});
								
							}
							
							break;
						case "DieC":
							String muderedname=receiveMessage.substring(4);
							if(RootController.id.equals(muderedname)) {
								die=true;
								Platform.runLater(()->{
									textClientServer.appendText(muderedname+"�� ����߽��ϴ�.\n");
									textClientServer.appendText("GAME OVER\n");
									clientTextField.setDisable(true);
									btnSay.setDisable(true);
									btnHow.setDisable(true);
									btnSuicide.setDisable(true);
									send("Con");
								});
							}else if(!(RootController.id.equals(muderedname))) {
								if(die==false) {
								Platform.runLater(()->{
									textClientServer.appendText(muderedname+"�� ���Ǿƿ� ���� ���ش��߽��ϴ�.\n");
									textClientServer.appendText("���� �������ϴ�.\n");
									btnHow.setDisable(false);
									btnSuicide.setDisable(true);
									
								});
								}else if(die==true) {
									Platform.runLater(()->{
										clientTextField.setDisable(true);
										btnSay.setDisable(true);
										btnHow.setDisable(true);
										btnSuicide.setDisable(true);
										
									});
									
								}
							}
							
							if(cliList.contains(muderedname)) {
								cliList.remove(muderedname);
							}
					
							
							break;
						
						default:
							Platform.runLater(() -> {
								textClientServer.appendText(receiveMessage + "\n");
							});
							break;

						}
					} catch (IOException e) {
						Platform.runLater(() -> {
							textClientServer.appendText("[������ ����� �ȵ˴ϴ�.]");
						});
						stopClient();
						break;
					}
				} // end of while
			}// end of run
		};// end of runnable
		Thread thread = new Thread(runnable);
		thread.start();
	}
}
