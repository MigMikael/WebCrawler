import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.Button;
import java.awt.Color;
import javax.swing.JLabel;
//import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Canvas;
import java.awt.Panel;
import java.awt.Label;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.Scrollbar;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CrawlerGUI {

	private JFrame frame;
	private JTextField inputUrl;
	private JTextField inputQuery;
	public TextArea resultTextArea;
	public JLabel labelResult;
	public JScrollPane scrollPane;
	public JLabel labelUrl;
	public JLabel labelQuery;
	public JLabel buttonGo;
	public JLabel bg2;
	public JLabel bg1;
	
	public Spider spider;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CrawlerGUI window = new CrawlerGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CrawlerGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		labelResult = new JLabel("\u0E1C\u0E25\u0E25\u0E31\u0E1E\u0E18\u0E4C\u0E01\u0E32\u0E23\u0E04\u0E49\u0E19\u0E2B\u0E32\u0E08\u0E32\u0E01\u0E17\u0E31\u0E49\u0E07\u0E2B\u0E21\u0E14");
		labelResult.setFont(new Font("Angsana New", Font.BOLD, 43));
		labelResult.setBounds(40, 249, 365, 50);
		frame.getContentPane().add(labelResult);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 310, 904, 326);
		frame.getContentPane().add(scrollPane);
		
		resultTextArea = new TextArea();
		resultTextArea.setFont(new Font("Angsana New", Font.PLAIN, 20));
		scrollPane.setViewportView(resultTextArea);
		
		labelUrl = new JLabel("Input URL you want to search :");
		labelUrl.setForeground(Color.DARK_GRAY);
		labelUrl.setFont(new Font("Angsana New", Font.BOLD, 32));
		labelUrl.setBounds(377, 48, 311, 29);
		frame.getContentPane().add(labelUrl);
		
		inputUrl = new JTextField();
		inputUrl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputUrl.setText("");
			}
		});
		inputUrl.setBounds(336, 88, 341, 29);
		frame.getContentPane().add(inputUrl);
		inputUrl.setColumns(10);
		
		labelQuery = new JLabel("Input Search Word :");
		labelQuery.setForeground(Color.DARK_GRAY);
		labelQuery.setFont(new Font("Angsana New", Font.BOLD, 32));
		labelQuery.setBounds(416, 118, 188, 29);
		frame.getContentPane().add(labelQuery);
		
		inputQuery = new JTextField();
		inputQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inputQuery.setText("");
			}
		});
		inputQuery.setBounds(336, 153, 341, 29);
		frame.getContentPane().add(inputQuery);
		inputQuery.setColumns(10);
		
		buttonGo = new JLabel("");
		buttonGo.setIcon(new ImageIcon("C:\\Users\\Mig\\Desktop\\go3.png"));
		buttonGo.setBounds(600, 193, 85, 50);
		frame.getContentPane().add(buttonGo);
		buttonGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				resultTextArea.setText("Searching... Please wait");
				String url = inputUrl.getText();
				String query = inputQuery.getText();
				
				spider = new Spider();
				spider.search(url, query);
				
				spider.countPageFound(query);
				spider.printPageFound();
				
				System.out.println("---------------------------------------------------");
				spider.calculateSimilarity();
				spider.printPageFound();
				
				System.out.println("---------------------------------------------------");
				spider.printPagePoint();
				System.out.println("---------------------------------------------------");
				
				spider.sortPage();
				spider.printResultPage();
				spider.showResult(resultTextArea);
				System.out.println("---------------------------------------------------");
			}
		});
		
		bg2 = new JLabel("");
		bg2.setIcon(new ImageIcon("C:\\Users\\Mig\\Desktop\\bg2.png"));
		bg2.setBounds(0, 0, 566, 503);
		frame.getContentPane().add(bg2);
		
		bg1 = new JLabel("");
		bg1.setFont(new Font("Tahoma", Font.BOLD, 11));
		bg1.setIcon(new ImageIcon("C:\\Users\\Mig\\Desktop\\maxresdefault.jpg"));
		bg1.setBounds(0, 0, 984, 661);
		frame.getContentPane().add(bg1);
	}
}
