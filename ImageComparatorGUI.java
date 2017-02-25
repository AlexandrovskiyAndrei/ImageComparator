package com.alexandrovskiy;

import java.awt.Button;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class ImageComparatorGUI {

	private JFrame frmPleaseSetFiles;
	private JTextField textFile1;
	private JTextField textFile2;
	private JTextField textResultFolder;
	private JCheckBox checkboxOpenFolder;
	private BufferedImage img1;
	private BufferedImage img2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageComparatorGUI window = new ImageComparatorGUI();
					window.frmPleaseSetFiles.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ImageComparatorGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		initFrame();
		initButtonFile1();
		initButtonFile2();
		initButtonResult();
		initButtonCompare();
		initCheckBoxOpen();

	}
	
	private void initFrame() {
		frmPleaseSetFiles = new JFrame();
		frmPleaseSetFiles.setTitle("Please specify files and folder to compare images");
		frmPleaseSetFiles.setBounds(450, 200, 546, 300);
		frmPleaseSetFiles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPleaseSetFiles.getContentPane().setLayout(null);
		frmPleaseSetFiles.setResizable(false);
		
		textFile1 = new JTextField();
		textFile1.setBounds(24, 24, 379, 20);
		frmPleaseSetFiles.getContentPane().add(textFile1);
		textFile1.setColumns(10);
		
		textFile2 = new JTextField();
		textFile2.setBounds(24, 76, 379, 20);
		frmPleaseSetFiles.getContentPane().add(textFile2);
		textFile2.setColumns(10);
		
		textResultFolder = new JTextField();
		textResultFolder.setBounds(24, 131, 379, 20);
		frmPleaseSetFiles.getContentPane().add(textResultFolder);
		textResultFolder.setColumns(10);
	}
	
	private void initButtonFile1() {
		Button buttonFile1 = new Button("File 1");
		buttonFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser1 = new JFileChooser();
				chooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result1 = chooser1.showOpenDialog(null);
				if (result1==JFileChooser.APPROVE_OPTION) {
					String name1 = chooser1.getSelectedFile().toString();
					File file1 = new File(name1);
					System.out.println(file1);
					textFile1.setText(name1);
					if(!(isImageFile(file1)) ){
					   JOptionPane.showMessageDialog(null, "The selected file is not an image. "
					   		+ "Please make sure that you have "
					   		+ "selected an image file.", "WARNING", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		buttonFile1.setBounds(409, 24, 97, 22);
		frmPleaseSetFiles.getContentPane().add(buttonFile1);
	}
	
	private void initButtonFile2() {
		Button buttonFile2 = new Button("File 2");
		buttonFile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser2 = new JFileChooser();
				chooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result2 = chooser2.showOpenDialog(null);
				if (result2==JFileChooser.APPROVE_OPTION) {
					String name2 = chooser2.getSelectedFile().toString();
					File file2 = new File(name2);
					System.out.println(file2);
					textFile2.setText(name2);
					if(!(isImageFile(file2)) ){
						   JOptionPane.showMessageDialog(null, "The selected file is not an image. "
						   		+ "Please make sure that you have "
						   		+ "selected an image file.", "WARNING", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		buttonFile2.setBounds(409, 74, 97, 22);
		frmPleaseSetFiles.getContentPane().add(buttonFile2);
	}

	private void initButtonResult() {
		Button buttonResultFolder = new Button("Results folder");
		buttonResultFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser3 = new JFileChooser();
				chooser3.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result2 = chooser3.showOpenDialog(null);
				if (result2==JFileChooser.APPROVE_OPTION) {
					String name3 = chooser3.getSelectedFile().toString();
					File file3 = new File(name3);
					System.out.println(file3);
					textResultFolder.setText(name3);
				}
			}
		});
		buttonResultFolder.setBounds(409, 131, 97, 22);
		frmPleaseSetFiles.getContentPane().add(buttonResultFolder);
	}
	
	private void initButtonCompare() {
		Button buttonCompare = new Button("Compare images!");
		buttonCompare.setActionCommand("Compare images!");
		buttonCompare.setFont(new Font("Calibri", Font.BOLD, 12));
		buttonCompare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				compare();
			}
		});
		buttonCompare.setBounds(177, 210, 150, 31);
		frmPleaseSetFiles.getContentPane().add(buttonCompare);
	}

	private void initCheckBoxOpen() {
		checkboxOpenFolder = new JCheckBox("Open resulting folder after files are compared");
		checkboxOpenFolder.setSelected(true);
		checkboxOpenFolder.setBounds(74, 173, 305, 23);
		frmPleaseSetFiles.getContentPane().add(checkboxOpenFolder);
	}
	
	private void compare() {
		if (emptyFields()) {
			JOptionPane.showMessageDialog(null,
					"Please specify both image files and the resulting folder",
					"WARNING", JOptionPane.WARNING_MESSAGE);
			return;
		} else {
			String imageName1 = textFile1.getText();
			String imageName2 = textFile2.getText();
			try {
				img1 = ImageIO.read(new File(imageName1));
				img2 = ImageIO.read(new File(imageName2));
				if (img1.getHeight() != img2.getHeight()
						&& img1.getWidth() != img2.getWidth()) {
					JOptionPane.showMessageDialog(null, "Unable to compare. "
							+ "Please provide two images of the same size");
				} else {
					writeDifferences();
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void writeDifferences() throws IOException {
		File result = new File(textResultFolder.getText()
				+ "\\result.png");
		BufferedImage img3 = new BufferedImage(img1.getWidth(),
				img1.getHeight(), BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < img1.getWidth(); x++) {
			for (int y = 0; y < img1.getHeight(); y++) {
				if (img1.getRGB(x, y) == img2.getRGB(x, y)) {
					img3.setRGB(x, y, img1.getRGB(x, y));
				} else {
					img3.setRGB(x, y, 16711680);
					// 16711680 is an integer representation of absolute red;
				}
			}
		}
		ImageIO.write(img3, "PNG", result);
		JOptionPane.showMessageDialog(null,
					"Successfully compared two files. Please see 'result.png'");
		// open resulting folder
		if (checkboxOpenFolder.isSelected()){
			Desktop.getDesktop().open(new File(textResultFolder.getText()));
		}
	}
	
	private boolean isImageFile(File testFile) {
		boolean isImage = false;
		if (	(testFile.getName().toLowerCase().endsWith(".png")) ||
				(testFile.getName().toLowerCase().endsWith(".jpg")) ||
				(testFile.getName().toLowerCase().endsWith(".jpeg"))||
				(testFile.getName().toLowerCase().endsWith(".gif")) ||
				(testFile.getName().toLowerCase().endsWith(".bmp")) ||
				(testFile.getName().toLowerCase().endsWith(".tiff"))	) {
			isImage = true;
		}
		return isImage;
	}
	
	private boolean emptyFields() {
		boolean empty = false;
		if (	textFile1.getText().length()==0 ||
				textFile2.getText().length()==0 ||
				textResultFolder.getText().length()==0 	
		) {
			empty = true;
		}
		return empty;
	}
}