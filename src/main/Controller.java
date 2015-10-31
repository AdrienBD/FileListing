package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import message.Message;
import message.MessageTitle;
import view.LicenseView;
import view.View;

public class Controller extends Observable implements ActionListener {
	
	private boolean launchable;
	private File toListDirectory;
	private File exportFile;

	public void initiateFrame() {
		JFrame frame = new JFrame(Main.SOFTWARE_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		this.addMainPanelToFrame(frame);
		this.setMenu(frame);
		
		frame.pack();
		frame.setVisible(true);
	}

	public void setToListDirectory(File toListDirectory) {
		this.toListDirectory = toListDirectory;
		updateLaunchable();
	}
	
	public void setExportFile(File exportFile) {
		this.exportFile = exportFile;
		updateLaunchable();
	}
	
	public void launchProcess() {
		this.setChanged();
		this.notifyObservers(new Message(MessageTitle.LAUNCHED));
		
		File[] files = toListDirectory.listFiles();
		try {
			exportFileList(files);
			
			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.PROCESSED));
		} catch (IOException e) {

			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.ERROR_ON_EXPORT));
		}
		
	}
	
	private void addMainPanelToFrame(JFrame frame) {
		View panel = new View(this);
		frame.getContentPane().add(panel, "Center");
		frame.setSize(panel.getPreferredSize());
	}

	private void setMenu(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu aboutMenu = new JMenu("About");
		menuBar.add(aboutMenu);
		
		JMenuItem versionItem = new JMenuItem(Main.VERSION_NAME);
		JMenuItem licenseItem = new JMenuItem("License");
		licenseItem.addActionListener(this);
		
		aboutMenu.add(versionItem);
		aboutMenu.add(licenseItem);
		
		frame.setJMenuBar(menuBar);
	}
	
	private void updateLaunchable() {
		launchable = this.exportFile != null && this.toListDirectory != null;
		if (launchable) {
			this.setChanged();
			this.notifyObservers(new Message(MessageTitle.LAUNCHABLE));
		}
	}
	
	private void exportFileList(File[] files) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));
		for (File f : files) {
			bw.write(f.getName());
			bw.newLine();
		}
		bw.flush();
		bw.close();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new LicenseView();
	}
}
