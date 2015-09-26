package main;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class View extends JPanel implements ActionListener {

	private JButton directoryToListBtn;
	private JLabel directoryToListLbl;
	private JButton exportDirectoryBtn;
	private JLabel exportDirectoryLbl;

	public View() {
		JPanel topPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		this.setupTopLine(topPanel);
		this.setupBottomLine(bottomPanel);
		this.add(topPanel);
		this.add(bottomPanel);
	}
	
	public void setupTopLine(JPanel topPanel) {
		directoryToListBtn = new JButton("Choose a directory to list");
		directoryToListLbl = new JLabel("No directory chosen");
		topPanel.add(directoryToListBtn);
		topPanel.add(directoryToListLbl);
		
		directoryToListBtn.addActionListener(this);
	}
	
	public void setupBottomLine(JPanel bottomPanel) {
		exportDirectoryBtn = new JButton("Choose a file to save file list");
		exportDirectoryLbl = new JLabel("No directory chosen");
		bottomPanel.add(exportDirectoryBtn);
		bottomPanel.add(exportDirectoryLbl);
		
		exportDirectoryBtn.addActionListener(this);
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 200);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == directoryToListBtn) {
			directoryToListBtnClicked(e);
		} else if (e.getSource() == exportDirectoryBtn) {
			exportDirectoryBtnClicked(e);
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
	
	private void directoryToListBtnClicked(ActionEvent event) {
		JFileChooser chooser = setupFileChooser(Action.LIST);
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): "
					+ chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}
	}
	
	private void exportDirectoryBtnClicked(ActionEvent event) {
		JFileChooser chooser = setupFileChooser(Action.EXPORT);
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): "
					+ chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());
		} else {
			System.out.println("No Selection ");
		}
	}
}
