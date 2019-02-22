package Controller;

import java.net.URL;
import java.util.ResourceBundle;



import Model.Account;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class NewAccount implements Initializable{

	public Stage accountstage;
	@FXML private Button  accountAccess;
	@FXML private Button  accountCancel;
	@FXML private TextField accountName;
	@FXML private TextField accountPW;
	@FXML private TextField accountIP;
	@FXML private TextField accountCKPW;
	@FXML private TextField textheight;
	@FXML private TextField textweight;
	@FXML private RadioButton radmale;
	@FXML private RadioButton radfemale;
	@FXML private RadioButton radlong;
	@FXML private RadioButton radshort;
	@FXML private RadioButton radtopred;
	@FXML private RadioButton radtopblack;
	@FXML private RadioButton radtopwhite;
	@FXML private RadioButton radbotwhite;
	@FXML private RadioButton radbotblack;
	@FXML private RadioButton radbotred;
	@FXML private RadioButton radshoeswhite;
	@FXML private RadioButton radshoesblack;
	@FXML private RadioButton radshoesred;
	@FXML private ToggleGroup group1;
	@FXML private ToggleGroup group2;
	@FXML private ToggleGroup group3;
	@FXML private ToggleGroup group4;
	@FXML private ToggleGroup group5;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		accountAccess.setOnAction(e->{handleAccountAccess();});
		accountCancel.setOnAction(e->{handleAccountCancel();});
	}
	private void handleAccountCancel() {
	accountstage.close();
	}
	private void handleAccountAccess() {
		String strid="";
		if(accountName.getText().length()==4) {
		strid= accountName.getText();
		}else {
			RootController.callAlert("아이디 오류 : 4자만 입력하세요.");
			accountName.clear();
			return;
		}
	/*	if(strid.length()==10) {
			RootController.callAlert("아이디 길이 오류 : 아이디를 10자 미만으로 입력해주세요.");
			accountName.clear();
			return;
		}*/
		String strpw= accountPW.getText();
		String strchpw= accountCKPW.getText();
		String strip= accountIP.getText();
		String strweight= textweight.getText();
		String strheight= textheight.getText();
		if(strpw==strchpw) {
			RootController.callAlert("경고 : 비밀번호를 확인해주세요.");
			accountCKPW.clear();
			accountPW.clear();
		}
		String strgender =group1.getSelectedToggle().getUserData().toString();
		String strhair =group2.getSelectedToggle().getUserData().toString();
		String strtop =group3.getSelectedToggle().getUserData().toString();
		String strbottom =group4.getSelectedToggle().getUserData().toString();
		String strshoes =group5.getSelectedToggle().getUserData().toString();
		System.out.println(strshoes);
		Account account= new Account(strid, strpw, strip, strgender, strweight, strheight, strhair, strtop, strbottom, strshoes);
		MafiaDAO mafiadao=new MafiaDAO();
		mafiadao.insertAccountData(account);
		
		
	}

}
