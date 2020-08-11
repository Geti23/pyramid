import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.j3d.Alpha;
import javax.media.j3d.Node;
import javax.media.j3d.Transform3D;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.border.EmptyBorder;

public class RotatePanel  {
	
    JPanel buttonsPanel = new JPanel();
    JPanel blankPanel = new JPanel();
    
	Transform3D yAxis = new Transform3D();
	
	public RotatePanel(JPanel panel)
	{
	    panel.setLayout(new BorderLayout());

	    JLabel resize_title = new JLabel(" K U T I A  E  M J E T E V E");
	    resize_title.setOpaque(true);
	    resize_title.setBackground(Color.lightGray);

	    panel.add(resize_title, BorderLayout.PAGE_START);
	    
	    JPanel rotatePanel1 = new JPanel();
	    rotatePanel1.setLayout(new GridBagLayout());
	    panel.add(rotatePanel1, BorderLayout.LINE_START);
	    
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    constraints.insets = new Insets(4, 4, 4, 4); // margjine 5 pixeleshe ne te gjitha anet
	    constraints.anchor = GridBagConstraints.NORTHWEST;

	    constraints.gridx = 1;
	    constraints.gridy = 0;
	    constraints.gridwidth = 3;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(new JLabel(""), constraints);
	    
	    constraints.gridx = 1;
	    constraints.gridy = 1;
	    constraints.gridwidth = 3;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(new JLabel("Boshti i rrotullimit:"), constraints);
	    
	    /**********************************
	     ---BUTONAT E BOSHTIT RROTULLUES---
	     **********************************/
        final JRadioButton x_cb = new JRadioButton("x");
	x_cb.setSelected(true);
        yAxis.rotZ(Math.PI / 2.0);
        constraints.gridx = 1;
	    constraints.gridy = 2;
	    constraints.gridwidth = 1;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(x_cb, constraints);

	    final JRadioButton y_cb = new JRadioButton("y");
	    //y_cb.setSelected(true);
            //yAxis.rotY(Math.PI / 2.0);
            constraints.gridx = 2;
	    constraints.gridy = 2;
	    constraints.gridwidth = 1;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(y_cb, constraints);
	    
	    final JRadioButton z_cb = new JRadioButton("z");
	    constraints.gridx = 3;
	    constraints.gridy = 2;
	    constraints.gridwidth = 1;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(z_cb, constraints);
	    
	    //grupojme butonat e radios se bashku (kur klikohet njeri, tjeret lirohen)
	    ButtonGroup group = new ButtonGroup();
	    group.add(x_cb);
	    group.add(y_cb);
	    group.add(z_cb);

	    ItemListener listener = new ItemListener()
	    {
	        public void itemStateChanged(ItemEvent e) {
	        	if (x_cb.isSelected())
	        		yAxis.rotZ(Math.PI / 2.0);
	        	else if (y_cb.isSelected())
	        		yAxis.rotY( Math.PI / 2.0 );
	        	else if (z_cb.isSelected())
	        		yAxis.rotX(Math.PI / 2.0);
	        }
	    };
	    
	    x_cb.addItemListener(listener);
	    y_cb.addItemListener(listener);
	    z_cb.addItemListener(listener);
	    
	    /**********************************
	     ----------HAPESIRA BOSHE----------
	     **********************************/
	    constraints.gridx = 1;
	    constraints.gridy = 3;
	    constraints.gridwidth = 3;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add( Box.createHorizontalStrut( 10 ), constraints );
	    rotatePanel1.add( Box.createVerticalStrut( 10 ), constraints );
	    
	    /**********************************
	     ----------KONTROLLUESIT-----------
	     **********************************/
	    //numri i rrotullimeve
	    constraints.gridx = 1;
	    constraints.gridy = 4;
	    constraints.gridwidth = 2;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 0.0;
	    rotatePanel1.add(new JLabel("Nr. rrotullimeve:"), constraints);
        
	    Integer[] numRotList = new Integer[25];
	    for (int i = 0; i < 25; i++)
	    	numRotList[i] = i+1;
	    
	    Integer[] speedList = new Integer[20];
	    for (int i = 0; i < 20; i++)
	    	speedList[i] = i+1;
	    
	    SpinnerListModel numRotModel = new SpinnerListModel(numRotList);
	    SpinnerListModel speedModel = new SpinnerListModel(speedList);
	    
	    final JSpinner numRotations = new JSpinner(numRotModel);
	    ((JSpinner.DefaultEditor)numRotations.getEditor()).getTextField().setColumns(2);
	    ((JSpinner.DefaultEditor)numRotations.getEditor()).getTextField().setEditable(false); 
	    numRotations.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
            numRotations.setValue(5);

	    final JSpinner speed = new JSpinner(speedModel);
	    ((JSpinner.DefaultEditor)speed.getEditor()).getTextField().setEditable(false); 
	    speed.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
	    speed.setValue(10);

	    constraints.gridx = 3;
	    constraints.gridy = 4;
	    constraints.gridwidth = 2;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add(numRotations, constraints);
	    
	    //shpejtesia
	    constraints.gridx = 1;
	    constraints.gridy = 5;
	    constraints.gridwidth = 2;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 0.0;
	    rotatePanel1.add(new JLabel("Shpejtesia:"), constraints);
	    
	    constraints.gridx = 3;
	    constraints.gridy = 5;
	    constraints.gridwidth = 2;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    constraints.fill = GridBagConstraints.HORIZONTAL;
	    rotatePanel1.add(speed, constraints);
	    
	    /**********************************
	     ---------HAPESIRA BOSHE-----------
	     **********************************/
	    constraints.gridx = 1;
	    constraints.gridy = 6;
	    constraints.gridwidth = 3;
	    constraints.gridheight = 1;
	    constraints.weightx = constraints.weighty = 1.0;
	    rotatePanel1.add( Box.createHorizontalStrut( 8 ), constraints );
	    rotatePanel1.add( Box.createVerticalStrut( 8 ), constraints );
	    
	    /**********************************
	     --------BUTONAT start/pause--------
	     **********************************/
	    JPanel rotatePanel2 = new JPanel();
	    panel.add(rotatePanel2, BorderLayout.PAGE_END);
	    
	    final JButton start_b = new JButton("start");
	    final JButton pause_b = new JButton("pause");
	    start_b.setFont(new Font("sansserif",Font.PLAIN,11));
	    pause_b.setFont(new Font("sansserif",Font.PLAIN,11));
	    
	    pause_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
        	Node shapeClicked = GUI_3D.getSwingTest().getShapeClicked();
            	
            	GUI_3D.rotateSpeed = (Integer)speed.getValue();
            	
            	Alpha alpha = new Alpha();

	        	try{if (shapeClicked.getClass().getName().equals("Piramida")) {
	        		alpha = ((Piramida) shapeClicked).getRotationAlpha();
	        	}}
                        catch(NullPointerException npe){}

        		if (alpha.isPaused()) {
        			alpha.resume();
        			pause_b.setText("pause");
        		}
        		else if (!alpha.finished()) {
        			alpha.pause();
        			pause_b.setText("resume");
        		}	
            } 
        });  
	    
	    start_b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
        	Node shapeClicked = GUI_3D.getSwingTest().getShapeClicked();
            	
            	GUI_3D.rotateSpeed = (Integer)speed.getValue();
            	
            	Alpha alpha = new Alpha();
            	
                try{if (shapeClicked.getClass().getName().equals("Piramida")) {
	        		alpha = ((Piramida) shapeClicked).getRotationAlpha();
            		((Piramida) shapeClicked).getRotator().setTransformAxis(yAxis);
	        	}}
                catch(NullPointerException npe){}

            	if (alpha.finished()){
            		alpha.setLoopCount((Integer)numRotations.getValue());
            		alpha.setStartTime(System.currentTimeMillis());
            		alpha.setIncreasingAlphaDuration((21 - (Integer)speed.getValue())*250);
         		alpha.setMode(Alpha.INCREASING_ENABLE);	
            	}
            	
            	else if (start_b.getText()=="stop") {
        			alpha.setLoopCount(0);
        			alpha.setStartTime(System.currentTimeMillis());
            	}
            	
        		if (alpha.isPaused()) {
        			alpha.resume();
        			pause_b.setText("pause");
        		}	
            } 
        });
	   
	    buttonsPanel.setLayout(new GridLayout(1,2,7,0));
	    buttonsPanel.add(start_b, constraints);
	    buttonsPanel.add(pause_b, constraints);

	    rotatePanel2.add(buttonsPanel, BorderLayout.PAGE_START);
	    blankPanel.setBorder(new EmptyBorder(10, 10, 15, 10) );
	    rotatePanel2.add(blankPanel, BorderLayout.PAGE_END);
	}

	public static void main(String[] args)
	{
		JFrame frame = new JFrame("paneli i rrotullimit");

		frame.setSize(170, 230);
		
                //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                //alternative per kodin me larte
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread() {
					public void run() 
					{
						System.exit(0);
					}
				}.start();
			}
		});

	    JPanel panel = new JPanel();
	    frame.add(panel);
	    
		new RotatePanel(panel);
		frame.setVisible(true);
        }	
}
