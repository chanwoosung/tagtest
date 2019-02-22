package Controller;

import java.io.BufferedReader;
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

import Model.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class RootController implements Initializable {
	public static ArrayList<Account> dbArrayList = MafiaDAO.getAccountTotalData();
	public Stage primaryStage;
	public @FXML PasswordField loginPassword;
	public @FXML Button btnLogin;
	public @FXML Button btnNewAccount;
	public @FXML TextField textLogin;
	public static String id;
	public static String pw;
	public static String ip;
	public static String gender;
	public static String weight;
	public static String height;
	public static String hair;
	public static String top;
	public static String bottom;
	public static String shoes;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnLogin.setOnAction((e) -> {
			handleBtnLogin();
		});
		btnNewAccount.setOnAction((e) -> {
			handleBtnNewAccount();
		});

	}

	private void handleBtnNewAccount() {
		try {
			Stage accountstage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/newaccount.fxml"));
			Parent root = loader.load();

			NewAccount newaccountController = loader.getController();//
			newaccountController.accountstage = accountstage;

			Scene scene = new Scene(root);
			accountstage.setScene(scene);
			accountstage.show();// 새로운 창을 띄운다.

		} catch (Exception e) {
			RootController.callAlert("생성창 오류 : 오류 점검바랍니다.");
		}

	}

	public void handleBtnLogin() {

		List<Account> dbList = new ArrayList<>();
		int i=0;
		for (Account account : dbArrayList) {
			dbList.add(account);
			id = account.getAccount();
			pw = account.getPassword();
			ip = account.getAddress();
			gender = account.getGender();
			weight = account.getWeight();
			height = account.getHeight();
			hair = account.getHair();
			top = account.getTop();
			bottom = account.getBottom();
			shoes = account.getShoes();
			if (textLogin.getText().equals(id) && loginPassword.getText().equals(pw)) {
				try {

					Stage choicestage = new Stage();
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Choice.fxml"));
					Parent root = loader.load();

					Choice choiceController = loader.getController();//
					choiceController.choicestage = choicestage;

					Scene scene = new Scene(root);
					choicestage.setScene(scene);
					// primaryStage.close();//로그인한면 처음창을 닫는다.
					 primaryStage.close();
					choicestage.show();// 새로운 창을 띄운다.
					
					Stage serverstage = new Stage();

				} catch (Exception e) {
					RootController.callAlert("화면전환 오류 : 화면 전환에 문제가 있습니다. 검토 바람");
				}
				i=1;
				break;
			} 
			}if(i==0) {
		callAlert("로그인 실패 : 아이디, 패스워드가 맞지 않습니다.");
		textLogin.clear();
		loginPassword.clear();
		return;
		}
	}

	public static void callAlert(String contentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("경고!");
		alert.setHeaderText(contentText.substring(0, contentText.lastIndexOf(":")));
		alert.setContentText(contentText.substring(contentText.lastIndexOf(":") + 1));

		alert.showAndWait();
	}
}
