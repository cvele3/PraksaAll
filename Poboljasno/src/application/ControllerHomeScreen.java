package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ControllerHomeScreen {
	
	private Stage stage;
	private Parent root;
	private Scene scene;

	public void switchToCroatianMessageEntry(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MessageEntryCroatian.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void switchToCroatianMessageWrite(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MessageWriteCroatian.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void switchToEnglishMessageWrite(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MessageWriteEnglish.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void switchToEnglishMessageEntry(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MessageEntryEnglish.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void switchToChooseLanguage(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ChooseLanguage.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
