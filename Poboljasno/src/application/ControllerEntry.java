package application;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerEntry {
	
	@FXML
	private TextField messEntry,encryptionPassword,messName,summarySHA;

	private Stage stage;
	private Parent root;
	private Scene scene;
	
	private static final String SECRETS_PATH_STRING = "C:\\Directory1\\Secrets";
	private static final String DIR_PATH_STRING = "C:\\Directory1\\Dir";
	private static final String DIR_STRING = "C:\\Directory1\\Dir";
	private static final String SECRETS_STRING = "C:\\Directory1\\Secrets";
	private static final String DIRECTORY_STRING = "C:\\Directory1";
	private static final String TXT_STRING = ".txt";
	

	public void switchToHomeScreenCroatian(ActionEvent e) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HomeSceenCroatian.fxml"));
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public void entryAndEncription(ActionEvent event) {
		String message, name, password;
		
		message = messEntry.getText();
		name = messName.getText();
		password = encryptionPassword.getText();
		
		
		Path p = Paths.get(DIR_STRING);
		Path s = Paths.get(SECRETS_STRING);
		new File(DIRECTORY_STRING).mkdir();
		
	    byte[] ivb = new byte[16];
	    new SecureRandom().nextBytes(ivb);
		IvParameterSpec iv = Security.generateIv(ivb);
		
		
		byte[] alt = new byte[64];
		SecureRandom random = new SecureRandom();
		random.nextBytes(alt);
		String salt = new String(alt);
		
		String encrypted = new String();
		String decrypted = new String();
		SecretKey pass = null;
		
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e2) {

			e2.printStackTrace();
		}
		byte[] encodedhash = digest.digest(message.getBytes(StandardCharsets.UTF_8));
		String hexString = Security.bytesToHex(encodedhash);
		summarySHA.setText(hexString);
		
		try {
			pass = Security.getKeyFromPassword(password,salt);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e1) {

			e1.printStackTrace();
		}
		try {
			encrypted = Security.encrypt(message, pass,iv);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {

			e.printStackTrace();
		}
		
		if(!Files.exists(p)) {
			try {
				Files.createDirectory(p);
				
			}catch(IOException ex) {
				System.out.println("Nista");
			}
		}
		
		if(!Files.exists(s)) {
			try {
				Files.createDirectory(s);
				
			}catch(IOException ex) {
				System.out.println("Nista");
			}
		}
		
		Path dok = Paths.get(DIR_PATH_STRING + "\\" +name + TXT_STRING);
		Path iva1 = Paths.get(SECRETS_PATH_STRING + "\\iv" + name + TXT_STRING);
		Path key2 = Paths.get(SECRETS_PATH_STRING + "\\key" + name + TXT_STRING);
		
		if(!Files.exists(dok)) {
			try {
				Files.createFile(dok);
				Files.write(dok, encrypted.getBytes(), StandardOpenOption.APPEND);
				
			}catch(IOException e) {
				System.out.println("Nema");
			}
		}
		
		if(!Files.exists(iva1)) {
			try {
				Files.createFile(iva1);
				Files.write(iva1, new String(ivb).getBytes(), StandardOpenOption.APPEND);
				
			}catch(IOException e) {
				System.out.println("Nema");
			}
		}
		
		if(!Files.exists(key2)) {
			try {
				Files.createFile(key2);
				Files.write(key2, salt.getBytes(), StandardOpenOption.APPEND);
				
			}catch(IOException e) {
				System.out.println("Nema");
			}
		}

		
		try {
			decrypted = Security.decrypt(encrypted, pass, iv);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException
				| InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {

			e.printStackTrace();
		}		
	}	
}
