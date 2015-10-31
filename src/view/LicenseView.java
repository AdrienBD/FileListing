package view;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class LicenseView {
	
	private final String licensePath = "LICENSE";

	public LicenseView() {
		try {
			String licenseText = getLicenseFromFile();
			
			JFrame licenseFrame = new JFrame("Software's License");
			licenseFrame.setLocationRelativeTo(null);
			
			JPanel licensePanel = new JPanel();
			licenseFrame.getContentPane().add(licensePanel, "Center");
			licenseFrame.setSize(new Dimension(200, 200));
			
			JTextArea licenseTextArea = new JTextArea(licenseText);
			licenseTextArea.setEditable(false);
			licensePanel.add(licenseTextArea);
			
			licenseFrame.pack();
			licenseFrame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getLicenseFromFile() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(licensePath)));
		String licenseText = "";
		String line = br.readLine();
		while(line != null) {
			licenseText += line;
			licenseText += "\n";
			line = br.readLine();
		}
		br.close();
		return licenseText;
	}
}
