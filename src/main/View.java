package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import message.Message;

@SuppressWarnings("serial")
public class View extends JPanel implements ActionListener, Observer {

	private Controller controller;
	private JButton directoryToListBtn;
	private JLabel directoryToListLbl;
	private JButton exportDirectoryBtn;
	private JLabel exportDirectoryLbl;
	private JButton launchBtn;

	public View(Controller controller) {
		this.controller = controller;
		this.controller.addObserver(this);
		this.setupUI();
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(600, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == directoryToListBtn) {
			directoryToListBtnClicked(e);
		} else if (e.getSource() == exportDirectoryBtn) {
			exportDirectoryBtnClicked(e);
		} else if (e.getSource() == launchBtn) {
			this.controller.launchProcess();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			Message message = (Message) arg;
			switch(message.title) {
			case LAUNCHABLE:
			case PROCESSED:
				this.launchBtn.setEnabled(true);
				break;
			case LAUNCHED:
				this.launchBtn.setEnabled(false);
				break;
			}
		}
	}

	private void setupUI() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel listDirectoryPanel = new JPanel();
		JPanel exportFilePanel = new JPanel();
		JPanel launchPanel = new JPanel();
		
		this.setupListDirectoryPanel(listDirectoryPanel);
		this.setupExportFilePanel(exportFilePanel);
		this.setupLaunchPanel(launchPanel);
		
		this.add(listDirectoryPanel);
		this.add(exportFilePanel);
		this.add(launchPanel);
	}
	
	private void setupListDirectoryPanel(JPanel listDirectoryPanel) {
		directoryToListBtn = new JButton("Choose a directory to list");
		directoryToListLbl = new JLabel("No directory chosen");
		listDirectoryPanel.add(directoryToListBtn);
		listDirectoryPanel.add(directoryToListLbl);
		
		directoryToListBtn.addActionListener(this);
	}
	
	private void setupExportFilePanel(JPanel exportFilePanel) {
		exportDirectoryBtn = new JButton("Choose a file to save file list");
		exportDirectoryLbl = new JLabel("No directory chosen");
		exportFilePanel.add(exportDirectoryBtn);
		exportFilePanel.add(exportDirectoryLbl);
		
		exportDirectoryBtn.addActionListener(this);
	}
	
	private void setupLaunchPanel(JPanel launchPanel) {
		launchBtn = new JButton("Start");
		launchBtn.setEnabled(false);
		launchPanel.add(launchBtn);
		
		launchBtn.addActionListener(this);
	}
	
	private void directoryToListBtnClicked(ActionEvent event) {
		JFileChooser chooser = setupFileChooser(Action.LIST);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File directoryToList = chooser.getSelectedFile();
			controller.setToListDirectory(directoryToList);
			directoryToListLbl.setText(directoryToList.getPath());
		}
	}
	
	private void exportDirectoryBtnClicked(ActionEvent event) {
		JFileChooser chooser = setupFileChooser(Action.EXPORT);
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File exportFile = chooser.getSelectedFile();
			controller.setExportFile(exportFile);
			exportDirectoryLbl.setText(exportFile.getPath());
		}
	}
	
	private JFileChooser setupFileChooser(Action action) {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		switch(action) {
			case LIST:
				chooser.setDialogTitle("Choose the directory you want to list files from");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				break;
			case EXPORT:
				chooser.setDialogTitle("Choose the directory you want to list files from");
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				break;
			default:
				break;	
		}
		
		return chooser;
	}
}
