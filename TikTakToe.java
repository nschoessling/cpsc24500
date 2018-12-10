/*
 * Rock Paper Scissors
 * Nicholas Schoessling
 * The purpose of this project is to play a game of rock paper scissors
 * To use it, you use the buttons to pick your choice and then you get your result if you win or not back
 * It uses two Choice objects in the RPSFrame
 * It supports text serialization and xml
 * I would like to add support to keeping track of wins in the future
 * 
 */
//imports
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//object class
class Choice{
	//variables
	private int pick;
	//getters and setters
	public int getPick() {
		return pick;
	}
	public void setPick(int pick) {
		this.pick = pick;
	}
	//constructor
	public Choice(int pick) {
		setPick(pick);
	}
	 //toString
	public String toString() {
		return String.format("Your choice = %d", pick);
	}
	
}
//Frame class
class RPSFrame extends JFrame{
	private ArrayList<Choice> games;
	private Random rnd;
	private Choice yours;
	private Choice comp;
	//configures the menu
	public void configureMenu() {
		JMenuBar mbar = new JMenuBar();
		JMenu mnuFile = new JMenu("File");
		//opens files
		JMenuItem miOpen = new JMenuItem("Open");
		miOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				String line;
				String[] parts;
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					//to make sure no errors happen
					try {
						File f = jfc.getSelectedFile();
						Scanner sc = new Scanner(f);
						games.clear();
						while (sc.hasNextLine()) {
							line = sc.nextLine().trim();
							parts = line.split(" ");
							games.add(new 
	Choice(Integer.parseInt(parts[0])));
						}
						sc.close();
						repaint();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,"Could not open the file.");
					}
				}
			}
		});
		mnuFile.add(miOpen);
		//save your data
		JMenuItem miSave = new JMenuItem("Save");
		miSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						File f = jfc.getSelectedFile();
						PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
						for (Choice c : games) {
							pw.println(c);
						}
						pw.close();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,"Could not save file.");
					}
				}
			}
		});
		mnuFile.add(miSave);
		//to exit the program
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnuFile.add(miExit);
		mbar.add(mnuFile);
		setJMenuBar(mbar);


	}
	//configuring the iu
	public void configureUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//screen dimensions
		setBounds(100,100,400,300);
		//title
		setTitle("Rock Paper Scissors V1.0");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		JPanel panSouth = new JPanel();
		panSouth.setLayout(new FlowLayout());
		//if you choose rock
		JButton btnRock = new JButton("Rock");
		btnRock.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//randomizes
						comp.setPick(rnd.nextInt(3));
						yours.setPick(0);
						//checks if you win
						if(yours.getPick()==0 && comp.getPick()== 2) {
							System.out.println("You win");
						}
						else {
							System.out.println("You lose");
						}
						repaint();
						games.add(new Choice(yours.getPick()));
					}
				}
		);
		panSouth.add(btnRock);
		//if you choose paper
		JButton btnPaper = new JButton("Paper");
		btnRock.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						comp.setPick(rnd.nextInt(3));
						yours.setPick(1);
						if(yours.getPick()==1 && comp.getPick()== 0) {
							System.out.println("You win");
						}
						else {
							System.out.println("You lose");
						}
						repaint();
						games.add(new Choice(yours.getPick()));
					
					}
				}
		);
		panSouth.add(btnPaper);
		//if you choose scissors
		JButton btnScissors = new JButton("Scissors");
		btnRock.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						comp.setPick(rnd.nextInt(3));
						yours.setPick(2);
						if(yours.getPick()==2 && comp.getPick()== 1) {
							System.out.println("You win");
						}
						else {
							System.out.println("You lose");
						}
						repaint();
						games.add(new Choice(yours.getPick()));
						
					}
				}
		);
		panSouth.add(btnScissors);
		c.add(panSouth,BorderLayout.SOUTH);
		configureMenu();
	}
	
	//constructor
	public RPSFrame(ArrayList<Choice> games) {
		this.games = games;
		configureUI();
	}
}

public class TikTakToe {
	public static void main(String[] args) {
		Choice yours = new Choice(0);
		Choice theirs = new Choice(0);
		ArrayList<Choice> games = new ArrayList<Choice>();
		games.add(new Choice(1));
		games.add(new Choice(0));
		games.add(new Choice(1));
		//saving in xml
		try {
			XMLEncoder enc = new XMLEncoder(new BufferedOutputStream(
				new FileOutputStream(new File("games.xml"))));
			enc.writeObject(games);
			enc.close();
		} catch (Exception ex) {

		}
		System.out.println("Will now read them back in: ");
		ArrayList<Choice> readGames;
		try {
			XMLDecoder dec = new XMLDecoder(new BufferedInputStream(
				new FileInputStream(new File("games.xml"))));
			readGames = (ArrayList<Choice>)(dec.readObject());
			dec.close();
			for (Choice c : readGames) {
				System.out.println(c);
			}
		} catch (Exception ex) {

		}
		//make frame visible

		RPSFrame frm = new RPSFrame(games);
		frm.setVisible(true);
	}
}
