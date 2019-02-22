package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.ExecutorService;

import Model.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server implements Initializable {

	public List<Client> list = new ArrayList<Client>();
	private Thread mainThread;
	public Stage serverStage;
	private ServerSocket serversocket;
	private Socket socket;

	GameClient gameClient = new GameClient();

	Client client;

	@FXML
	public TextArea textServer;
	@FXML
	Button btnStartStop;
	@FXML
	Button btnStartGame;
	@FXML
	Button btnEndMurder;
	@FXML
	Button btnEndVote;
	@FXML
	ListView<String> serverCitizenList;
	@FXML
	ListView<String> serverMafiaList;
	ExecutorService executorService;
	List<String> mafiaList = new ArrayList<>();
	List<String> citizenList = new ArrayList<>();
	List<User> userList = new ArrayList<>();
	ObservableList<String> mafiaobList = FXCollections.observableArrayList();
	ObservableList<String> citizenobList = FXCollections.observableArrayList();
	int playerScore[] = new int[8];
	int intplayer1 = 0;
	int intplayer2 = 0;
	int intplayer3 = 0;
	int intplayer4 = 0;
	int intplayer5 = 0;
	int intplayer6 = 0;
	int intplayer7 = 0;
	int intplayer8 = 0;
	String gamePlayer[] = new String[8];
	int votenumber = 0;
	int mafiavote = 0;
	int deb = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		textServer.setEditable(false);
		for (int i = 0; i < 8; i++)
			playerScore[i] = 0;
		eventButtonStartStopHandle();
		eventButtonStartGameHandle();
		eventButtonEndMurderHandle();
		eventButtonEndVoteHandle();
	}

	private void eventButtonEndMurderHandle() {
		btnEndMurder.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (Client client : list) {
					client.send("EndM");
				}
			}
		});
	}

	private void eventButtonEndVoteHandle() {
		btnEndVote.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (Client client : list) {
					client.send("EndV");
				}
			}
		});
	}

	private void eventButtonStartGameHandle() {
		btnStartGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (Client client : list) {
					client.send("GAME");
				}
			}
		});
	}

	private void eventButtonStartStopHandle() {
		btnStartStop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (btnStartStop.getText().equals("CONNECT SERVER")) {
					startServer();
				} else {
					stopServer();
				}
			}
		});
	}

	public void stopServer() {
		try {
			for (Client client : list) {
				client.socket.close();
				list.remove(client);
			}
		} catch (Exception e) {
			if (mainThread.isAlive()) {
				mainThread.stop();
			}
		}

		if (!serversocket.isClosed() && serversocket != null) {
			try {
				serversocket.close();
			} catch (IOException e1) {
			}
		}
		Platform.runLater(() -> {
			textServer.appendText("방이 닫혔습니다.");
			btnStartStop.setText("CONNECT SERVER");
		});
	}
	// 서버 소켓을 만들고-> 바인딩 하고 ->
	// [스레드로 처리함(3번 시작) 화면 수정 작업을 진행한다Platform.runnable ->
	// 대기한다 ->접속해오면 -> 클라이언트 소켓을만들고 ->클라이언트 클레스 객체를 만들고
	// ->컬렉션 프레임 워크에 저장한다. (쓰레드 완료)]
	// ->이런 과정을 스레드화 시킨다 쓰레드로 작동 시킨다.

	public void startServer() {
		try {
			serversocket = new ServerSocket();
			serversocket.bind(new InetSocketAddress(RootController.ip, 5004));
		} catch (IOException e) {
			if (serversocket.isClosed())
				;
			{
				stopServer();
			}
			return;
		}

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// 일반 쓰레드에서 javafx application Thread에서 해야될 일을 (UI수정 하려면 )
				// Platform.runLater()불러서 작업을 요청해야 된다.
				Platform.runLater(() -> {
					textServer.setText("방이 개설되었습니다." + Thread.currentThread().getName() + "\n");
					btnStartStop.setText("DISCONNECT");
				});
				while (true) {
					try {
						Socket socket = serversocket.accept();
						Platform.runLater(() -> {
							textServer.appendText("입장했습니다." + socket.getRemoteSocketAddress() + "\n");// 상대방이 어디서 오는지
																										// 알려준다.
							btnStartStop.setText("DISCONNECT");
						});

						Client client = new Client(socket);
						list.add(client);
						Platform.runLater(() -> {
							textServer.appendText("연결 개수" + list.size() + "\n");// 상대방이 어디서 오는지 알려준다.
						});
						/*
						 * for (Client client : list) { client.send("System : 현재" + list.size() +
						 * "명 입장했습니다."); } if (list.size() == 5) {// 수정 Platform.runLater(() -> {
						 * textServer.appendText("System : Game Start"); }); for (Client client : list)
						 * { client.send("GAME"); } }
						 */

						/*
						 * Runnable runnable = new Runnable() {
						 * 
						 * @Override public void run() { try { while (true) { InputStream is =
						 * socket.getInputStream(); InputStreamReader isr = new InputStreamReader(is);
						 * BufferedReader br = new BufferedReader(isr); String receiveMessage =
						 * br.readLine();
						 * 
						 * if (receiveMessage == null) { throw new IOException(); } Platform.runLater(()
						 * -> { textServer.appendText(receiveMessage + "\n");
						 * 
						 * }); for (Client client : list) { client.send(receiveMessage); }
						 * 
						 * } // end of while } catch (IOException e) { // list.remove(Client.this); }
						 * 
						 * } }; Thread thread = new Thread(runnable); thread.start();
						 */
					} catch (IOException e) {
						if (!serversocket.isClosed()) {
							stopServer();
						}
						break;
					}
				} // end of while
			}
		};// end of runnable
		mainThread = new Thread(runnable);
		mainThread.start();
	}

	class Client {
		Socket socket;

		public Client(Socket socket) {
			this.socket = socket;
			receive();
		}

		public void receive() {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						while (true) {
							InputStream is = socket.getInputStream();
							InputStreamReader isr = new InputStreamReader(is);
							BufferedReader br = new BufferedReader(isr);
							String receiveMessage = br.readLine();
							if (receiveMessage == null) {
								throw new IOException();
							}
							/*
							 * System.out.println(receiveMessage + "1=======");
							 * if(receiveMessage.length()<8) { Platform.runLater(() -> {
							 * textServer.appendText(receiveMessage + "\n"); client.send(receiveMessage);
							 * }); }
							 */
							String protomessage = receiveMessage.substring(5, 8);
							/*
							 * if(protomessage==null) { Platform.runLater(() -> {
							 * textServer.appendText(receiveMessage + "\n"); client.send(receiveMessage);
							 * }); }
							 */
							System.out.println(protomessage);

							switch (protomessage) {
							case "%1%":
								int count = 0;

								userList.add(new User(receiveMessage.substring(0, 4), count,
										Integer.parseInt(receiveMessage.substring(8))));
								System.out.println(receiveMessage.substring(0, 4));

								if (userList.size() == list.size()) {// 유저리스트 크기 수정
									setList(receiveMessage);
								}
								break;
							case "%2%":

								String str = receiveMessage.substring(8);
								String votername = receiveMessage.substring(0,4);
								for (int i = 0; i < userList.size(); i++) {
									if (str.equals(userList.get(i).getName())) {
										userList.get(i).vote++;

									}
								}
								Platform.runLater(()->{
									textServer.appendText(votername+"가"+str+"(을)를 투표하였습니다.");
								});
								votenumber++;
								System.out.println(votenumber);
								if (votenumber == userList.size()) {
									System.out.println(userList.size());
									User maxUser = userList.get(0);
									boolean isLegit = true;
									for (int i = 1; i < userList.size(); i++) {
										if (userList.get(i).getVote() > maxUser.getVote()) {
											maxUser = userList.get(i);
											isLegit = true;
										} else if (userList.get(i).getVote() == maxUser.getVote())
											isLegit = false;
									}
									if (isLegit) {// 최고점수가한명
										System.out.println(maxUser.getName() + "가" + maxUser.getVote() + "점으로 최고 점수임");
										for (Client client : list) {
											client.send("Kill" + maxUser.getName());
										}
										if (citizenList.contains(maxUser.getName())) {
											for (Client client : list) {
												client.send(maxUser.getName() + "(은)는 시민이였습니다.");
											}
											citizenList.remove(maxUser.getName());
											userList.remove(maxUser.getName());
										} else if (mafiaList.contains(maxUser.getName())) {
											for (Client client : list) {
												client.send(maxUser.getName() + "(은)는 마피아였습니다.");
											}
											mafiaList.remove(maxUser.getName());
											userList.remove(maxUser.getName());
										}
										System.out.println(userList.size());
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											votenumber = 0;
										}
									} else {// 최고점수가 중복시
										System.out.println("동점자가 두명이상임");
										for (Client client : list) {
											client.send("Revo");
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											votenumber = 0;
										}
									}
								}

							

								break;
							case "Con":
								/*int con=0;
								con++;
								if(con==list.size()) {*/
								 if (mafiaList.size() == 0) {
										for (Client client : list) {
											client.send("!END");
										}
										;

									}
							else if (citizenList.size() == mafiaList.size()) {
									for (Client client : list) {
										client.send("END!");
									}
									;
								} if(citizenList.size()>mafiaList.size()) {
									for(Client client :list) {send("남은시민수는"+citizenList.size()+"이며 남은 마피아는 "+mafiaList.size()+"입니다.");}
									/*con=0;*/
								}
								//}
								break;

							case "EDM":
								/*
								 * int edm=0; edm++; System.out.println(edm); if(edm==list.size()) {
								 */
								User maxUser2 = userList.get(0);
								boolean isLegit2 = true;
								for (int i = 1; i < userList.size(); i++) {
									if (userList.get(i).getVote() > maxUser2.getVote()) {
										maxUser2 = userList.get(i);
										isLegit2 = true;
									} else if (userList.get(i).getVote() == maxUser2.getVote())
										isLegit2 = false;
								}
								if (isLegit2) {// 최고점수가한명
									System.out.println(maxUser2.getName() + "가" + maxUser2.getVote() + "점으로 최고 점수임");

									for (Client client : list) {
										client.send("DieC" + maxUser2.getName());
									}

									if (citizenList.contains(maxUser2.getName())) {
										userList.remove(maxUser2.getName());
										citizenList.remove(maxUser2.getName());
									} else if(mafiaList.contains(maxUser2.getName())){
										userList.remove(maxUser2.getName());
										mafiaList.remove(maxUser2.getName());
									}

									for (int j = 0; j < userList.size(); j++) {
										userList.get(j).vote = 0;
										mafiavote = 0;
									}
								} else {// 최고점수가 중복시
									System.out.println("동점자가 두명이상임");
									if (maxUser2.getVote() == 0) {
										send("====\n");
									} else {
										for (Client client : list) {
											client.send("ReMu");
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											mafiavote = 0;
										}
									}
								}
								/* } */

								break;
							case "SEC":
								String mafianame = receiveMessage.substring(0, 5);
								String secret = receiveMessage.substring(8);
								for (Client client : list) {
									send("whis" + mafianame + secret);
								}
								break;
							case "EDV":
								/*int edv = 0;
								edv++;
								if (edv == list.size()) {*/
									User maxUser = userList.get(0);
									boolean isLegit = true;
									for (int i = 1; i < userList.size(); i++) {
										if (userList.get(i).getVote() > maxUser.getVote()) {
											maxUser = userList.get(i);
											isLegit = true;
										} else if (userList.get(i).getVote() == maxUser.getVote())
											isLegit = false;
									}

									if (isLegit) {// 최고점수가한명
										System.out.println(maxUser.getName() + "가" + maxUser.getVote() + "점으로 최고 점수임");
										for (Client client : list) {
											client.send("Kill" + maxUser.getName());
										}
										if (citizenList.contains(maxUser.getName())) {
											for (Client client : list) {
												client.send(maxUser.getName() + "시민이였습니다.");
											}
											citizenList.remove(maxUser.getName());
											userList.remove(maxUser.getName());
										} else if(mafiaList.contains(maxUser.getName())){
											for (Client client : list) {
												client.send(maxUser.getName() + "마피아였습니다.");
											}
											mafiaList.remove(maxUser.getName());
											userList.remove(maxUser.getName());
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											votenumber = 0;
										}
									} else {// 최고점수가 중복시
										System.out.println("동점자가 두명이상임");
										if(maxUser.getVote()==0) {send("====\n");}
										else{for (Client client : list) {
											client.send("Revo");
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											votenumber = 0;
										}
										}
									}
								//}

								break;
							case "Tar":
								String tar = receiveMessage.substring(8);
								Platform.runLater(() -> {
									textServer.appendText(receiveMessage);
								});
								for (int i = 0; i < userList.size(); i++) {
									if (tar.equals(userList.get(i).getName())) {
										userList.get(i).vote++;
									}
								}
								mafiavote++;
								if (mafiavote == mafiaList.size()) {
									User maxUser1 = userList.get(0);
									boolean isLegit1 = true;
									for (int i = 1; i < userList.size(); i++) {
										if (userList.get(i).getVote() > maxUser1.getVote()) {
											maxUser1 = userList.get(i);
											isLegit1 = true;
										} else if (userList.get(i).getVote() == maxUser1.getVote())
											isLegit1 = false;
									}
									if (isLegit1) {// 최고점수가한명
										System.out
												.println(maxUser1.getName() + "가" + maxUser1.getVote() + "점으로 최고 점수임");
										for (Client client : list) {
											client.send("DieC" + maxUser1.getName());
										}
										if (citizenList.contains(maxUser1.getName())) {
											userList.remove(maxUser1.getName());
											citizenList.remove(maxUser1.getName());
										} else if(mafiaList.contains(maxUser1.getName())){
											userList.remove(maxUser1.getName());
											mafiaList.remove(maxUser1.getName());
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											mafiavote = 0;
										}
									} else {// 최고점수가 중복시
										System.out.println("동점자가 두명이상임");
										for (Client client : list) {
											client.send("ReMu");
										}
										for (int j = 0; j < userList.size(); j++) {
											userList.get(j).vote = 0;
											mafiavote = 0;
										}
									}
									
								}

								break;

							default:
								Platform.runLater(() -> {
									textServer.appendText(receiveMessage + "\n");
									for (Client client : list) {
										client.send(receiveMessage);
									}

								});

								break;
							}
						} // end of while
					} catch (IOException e) {
						list.remove(Client.this);
					}

				}

			};
			Thread thread = new Thread(runnable);
			thread.start();
		}

		public void send(String message) {
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					try {
						OutputStream os = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(os);
						pw.println(message);
						pw.flush();
					} catch (IOException e) {
						Platform.runLater(() -> {
							textServer.setText("통신 두절" + socket.getRemoteSocketAddress() + "\n");
						});
						list.remove(Client.this);
						try {
							socket.close();
						} catch (IOException e1) {
						}
					}

				}
			};
			Thread thread = new Thread(runnable);
			thread.start();

		}

		private void setList(String receiveMessage) {
			int maxIndex = 0;
			int secondIndex = 0;
			for (int i = 0; i < userList.size(); i++) {// 수정
				if (userList.get(i).getRand() > userList.get(maxIndex).getRand()) {
					maxIndex = i;
				} else if ((userList.get(i).getRand() > userList.get(secondIndex).getRand()
						&& userList.get(i).getRand() < userList.get(maxIndex).getRand())
						|| userList.get(maxIndex).getRand() == userList.get(secondIndex).getRand()) {
					secondIndex = i;
				}
				citizenList.add(userList.get(i).getName());

			}
			citizenList.remove(userList.get(maxIndex).getName());
			citizenList.remove(userList.get(secondIndex).getName());
			mafiaList.add(userList.get(maxIndex).getName());
			mafiaList.add(userList.get(secondIndex).getName());

			for (String str : citizenList) {
				citizenobList.add(str);
			}
			serverCitizenList.setItems(citizenobList);
			for (String str : mafiaList) {
				mafiaobList.add(str);
			}
			serverMafiaList.setItems(mafiaobList);
			String mafia1 = userList.get(maxIndex).getName();
			String mafia2 = userList.get(secondIndex).getName();
			/*
			 * String citizen[] = null; for(int i=0;i<=2;i++) {//수정해야함
			 * citizen[i]=userList.get(i).getName(); }
			 */
			System.out.println(mafia1);
			System.out.println(mafia2);
			for (Client client : list) {
				client.send("Mafi" + mafia1 + mafia2);
			}

			/*
			 * for(Client client:list) {client.send("MaLi" + mafia2);} for(Client
			 * client:list) {client.send("MaLi" + mafia1); }
			 */

			for (Client client : list) {
				client.send("List" + userList.get(0).getName() + userList.get(1).getName() + userList.get(2).getName()
						+ userList.get(3).getName() + userList.get(4).getName() + userList.get(5).getName()
						+ userList.get(6).getName());
			}

			/*
			 * for(Client client:list) { client.send("List" + userList.get(0).getName());}
			 * for(Client client:list) { client.send("List" + userList.get(1).getName());}
			 * for(Client client:list) { client.send("List" + userList.get(2).getName());
			 * client.send("List" + userList.get(3).getName()); client.send("List" +
			 * userList.get(4).getName()); client.send("List" + userList.get(5).getName());
			 * client.send("List" + userList.get(6).getName()); client.send("List" +
			 * userList.get(7).getName()); client.send("List" + userList.get(8).getName());
			 * }
			 */
			/*
			 * System.out.println(mafia1); System.out.println(mafia2); }
			 */

		}
	}
}
