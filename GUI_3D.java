import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.j3d.Canvas3D;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class GUI_3D extends JPanel implements MouseListener,
		MouseMotionListener{

	static float rotateSpeed = 0.0f;

	public static SwingTest getSwingTest() {
		return swingTest;
	}

	public static void setSwingTest(SwingTest swingTest) {
		GUI_3D.swingTest = swingTest;
	}

	private static SwingTest swingTest;
	private static Canvas3D c3d;

	private JFrame frame;

	// Panelat
	private JPanel rightToolbar, mainPanel;
  
	private JButton btn_pyramid;

	public GUI_3D() {
		swingTest = new SwingTest();
		c3d = swingTest.getC3d();
		init();
	}

	public final void init() {

		frame = new JFrame("3D GUI");
		frame.setSize(900, 700);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addMouseListener(this);
		addMouseMotionListener(this);

		mainPanel = new JPanel();
                mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new BorderLayout());
		frame.add(mainPanel);

		btn_pyramid = new JButton();
		btn_pyramid.setBackground(Color.BLACK);
                Color c = new Color(150,0,0);
                btn_pyramid.setForeground(c);
                btn_pyramid.setText("Krijo nje piramide");
		btn_pyramid.addActionListener(new CreatePyramid());
		rightToolbar = new JPanel();
		rightToolbar.setLayout(new GridLayout(3,0,0,10));
		rightToolbar.setBorder(LineBorder.createGrayLineBorder());

		mainPanel.add(rightToolbar, BorderLayout.LINE_START);      

		// paneli i rrotullimit
		JPanel rotatePane = new JPanel();
		rotatePane.setMaximumSize(new Dimension(150, 190));
		rotatePane.setPreferredSize(new Dimension(150, 190));
		rightToolbar.add(rotatePane);
                rightToolbar.add(btn_pyramid);
  		rotatePane.setBorder(new EmptyBorder(0, 0, 0, 0));

		new RotatePanel(rotatePane);

                mainPanel.add(c3d);
		frame.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {
		GUI_3D ex = new GUI_3D();
		ex.setVisible(true);
	}

	class CreatePyramid implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//System.out.println("U krijua nje piramide e re");
			swingTest.getSceneBranchGroup().addChild(swingTest.createPyramid());
		}
	}

	public void mouseDragged(MouseEvent arg0) {}

	public void mouseMoved(MouseEvent e) {}

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent e) {}
}
