package com.aliasmk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;



public class gui extends JFrame
{
	//Class Declarations
	JTextField resval, tolval;
	JButton enter;
	String disp = "";
	TextHandler handler = null;
	
	
	//Constructor
	public gui() {
		super("TextField Test Demo");
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		
		JLabel title = new JLabel("Convert Values to Colors:");
		JLabel val = new JLabel("Value:");
		resval = new JTextField(10);
		JLabel tol = new JLabel("Tolerance:");
		tolval = new JTextField(10);
		enter = new JButton("Calculate");
		
		//container.add(title);
		container.add(val);
		container.add(resval);
		//container.add(tol);
		//container.add(tolval);
		container.add(enter);
		
		
		
		handler = new TextHandler();
		resval.addActionListener(handler);
		setTitle("Resistor Codes");
		setSize(300,300);
		setVisible(true);
	}
	//Inner Class TextHandler
	private class TextHandler implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == resval || e.getSource() == enter) 
			{
				resistor res = new resistor(resval.getText(),false);
				
			
				String[] colors = res.valueToColor();
				String parsedColors = "";
				
				for(int i = 0; i<colors.length; i++)
				{
					parsedColors+=colors[i] +" ";
				}
				
				disp = "Resistance : " + e.getActionCommand() +"\n" +"Colors: " +parsedColors ;
			}
			JOptionPane.showMessageDialog(null, disp);
		}
	}
	//Main Program that starts Execution
	public static void main(String args[]) {
		gui test = new gui();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}// End of class TextFieldTest
