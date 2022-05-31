package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ControllerRead {
	
	@FXML
	private TextField finalMessage,enteredSHA,generatedSHA,passwordEntry;
	@FXML
	private ListView allMessages;
	@FXML
	private Button choseMessage;
	@FXML
	private Label integrity;

	private Stage stage;
	private Parent root;
	private Scene scene;
	
	
	private static final String SECRETS_PATH_STRING = "C:\\Directory1\\Secrets";
	private static final String DIR_PATH_STRING = "C:\\Directory1\\Dir";
	static String langString = null;
	

	public void switchToHomeScreenCroatian(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HomeSceenCroatian.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
	
	public void switchToHomeScreenEnglish(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HomeScreenEnglish.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {

			e1.printStackTrace();
		}
	}
	
	public void ButtonAction(ActionEvent event){
	    FileChooser fc = new FileChooser();
	    fc.setInitialDirectory(new File(DIR_PATH_STRING));
	    File seletedFile = fc.showOpenDialog(null);
	    
	    integrity.setText(null);
	    
	    if(seletedFile!=null){
	        allMessages.getItems().add(seletedFile.getName());
	    }else{
	       System.out.println("file is not valid");
	    }
	    
	    String line = null;
	    
	    try {
	        BufferedReader in;
	        in = new BufferedReader(new FileReader(seletedFile));
	        
	        line = in.readLine();
	        
	    } catch (FileNotFoundException ex) {
	        System.out.println("nije ga naslo");
	    } catch (IOException ex) {
	        System.out.println("ioexception");
	    }
	    
	    String salt = null;
	    byte[] ivb = null;
	    
	    
	    try {
	    	
	    	ivb = Files.readAllBytes(Paths.get(SECRETS_PATH_STRING + "\\iv" +seletedFile.getName()));
			byte [] key = Files.readAllBytes(Paths.get(SECRETS_PATH_STRING + "\\key" + seletedFile.getName()));
		
	        salt = new String(key);
	        
	    } catch (FileNotFoundException ex) {
	        System.out.println("nije ga naslo");
	    } catch (IOException ex) {
	        System.out.println("ioexception");
	    }
	    
	    String passwordString = passwordEntry.getText();
	    String decryptedString = null;
	    SecretKey pass = null;
	    IvParameterSpec iv = Security.generateIv(ivb);
	    
	    try {
			pass = Security.getKeyFromPassword(passwordString,salt);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
	    
	    
	    
	    try {
	    	decryptedString = Security.decrypt(line, pass, iv);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException e) {

		}catch(BadPaddingException e) {
			integrity.setText(StatusCodes.MESSAGE_TAMPERED_WITH. getMessageForLanguage(langString));
		}
	    
		MessageDigest digest = null;
		
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e2) {

			e2.printStackTrace();
		}
		
		byte[] encodedhash = digest.digest(decryptedString.getBytes(StandardCharsets.UTF_8));
		String hexString = Security.bytesToHex(encodedhash);
		generatedSHA.setText(hexString);
		String tocanString = enteredSHA.getText();
		
		if(tocanString.equals(hexString)) {
			integrity.setText(StatusCodes.MESSAGE_SAME. getMessageForLanguage(langString));
		}else if(tocanString.equals(tocanString)) {
			integrity.setText(StatusCodes.SHA256_CHECKSUM_NOT_ENTERED. getMessageForLanguage(langString));
		}
	    
	    finalMessage.setText(decryptedString);
	}
}
