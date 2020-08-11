import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.swing.*;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;

public class SwingTest extends JPanel implements MouseListener,
		MouseMotionListener{

	private int mouseX, mouseY;
	private int mouseButton;
	private float tx, ty;

	private static BranchGroup sceneBranchGroup = null;
	private static Canvas3D c3d;
	private static PickCanvas pickCanvas;

	private Node shapeClicked;
	private Node shapeToDrag;
	private TransformGroup tgArray;

	public SwingTest() {
		init();
	}

	protected void init() {
		VirtualUniverse universe = createVirtualUniverse();

		Locale locale = createLocale(universe);

		ViewPlatform vp = createViewPlatform();
		createView(vp);

		BranchGroup sceneBranchGroup = createSceneBranchGroup();

		Background background = createBackground();
		if (background != null)
			sceneBranchGroup.addChild(background);

		BranchGroup viewBranchGroup = createViewBranchGroup(
				getViewTransformGroupArray(), vp);

		locale.addBranchGraph(sceneBranchGroup);
		addViewBranchGroup(locale, viewBranchGroup);
	}

	protected void addCanvas3D(Canvas3D c3d) {
		add("Center", c3d);
	}

	protected View createView(ViewPlatform vp) {
		View view = new View();

		PhysicalBody pb = new PhysicalBody();
		PhysicalEnvironment pe = new PhysicalEnvironment();
		view.setPhysicalEnvironment(pe);
		view.setPhysicalBody(pb);

		view.attachViewPlatform(vp);

		c3d = createCanvas3D(false);
		view.addCanvas3D(c3d);

		addCanvas3D(c3d);
		c3d.addMouseListener(this);
		c3d.addMouseMotionListener(this);

		return view;
	}

	protected Background createBackground() {
		Background background = new Background();

		return background;
	}

	protected Canvas3D createCanvas3D(boolean offscreen) {
		GraphicsConfigTemplate3D gc3D = new GraphicsConfigTemplate3D();
		gc3D.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);

		GraphicsDevice gd[] = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getScreenDevices();

		Canvas3D c3d = new Canvas3D(gd[0].getBestConfiguration(gc3D), offscreen);

		return c3d;
	}

	public TransformGroup[] getViewTransformGroupArray() {
		TransformGroup[] tgArray = new TransformGroup[1];
		tgArray[0] = new TransformGroup();
		tgArray[0].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D t3d = new Transform3D();
		t3d.setTranslation(new Vector3d(0.0, 0.0, -10.0));
		t3d.invert(); // leviz shikuesin, jo skenen
		tgArray[0].setTransform(t3d);

		return tgArray;
	}

	protected void addViewBranchGroup(Locale locale, BranchGroup bg) {
		locale.addBranchGraph(bg);
	}

	protected Locale createLocale(VirtualUniverse u) {
		return new Locale(u);
	}

	protected ViewPlatform createViewPlatform() {
		ViewPlatform vp = new ViewPlatform();
		vp.setViewAttachPolicy(View.RELATIVE_TO_FIELD_OF_VIEW);
		vp.setActivationRadius(100);

		return vp;
	}

	protected BranchGroup createViewBranchGroup(TransformGroup[] tgArray,
			ViewPlatform vp) {
		BranchGroup vpBranchGroup = new BranchGroup();

		if (tgArray != null && tgArray.length > 0) {
			Group parentGroup = vpBranchGroup;
			TransformGroup curTg = null;

			for (int n = 0; n < tgArray.length; n++) {
				curTg = tgArray[n];
				parentGroup.addChild(curTg);
				parentGroup = curTg;
			}

			tgArray[tgArray.length - 1].addChild(vp);
		} else
			vpBranchGroup.addChild(vp);

		return vpBranchGroup;
	}

	protected VirtualUniverse createVirtualUniverse() {
		return new VirtualUniverse();
	}

	protected BranchGroup createSceneBranchGroup() {
		BranchGroup objRoot = new BranchGroup();

		TransformGroup objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		sceneBranchGroup = new BranchGroup();

		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_EXTEND);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_READ);
		sceneBranchGroup.setCapability(Group.ALLOW_CHILDREN_WRITE);

		// mundesojme kapjen e piramides me maus
		pickCanvas = new PickCanvas(c3d, sceneBranchGroup);
		pickCanvas.setMode(PickCanvas.GEOMETRY);

		objTrans.addChild(sceneBranchGroup);
		objRoot.addChild(objTrans);

		return objRoot;
	}

	protected BranchGroup createPyramid() {
		BranchGroup bg = new BranchGroup();
		bg.setCapability(BranchGroup.ALLOW_DETACH); // mundesojme fshirjen e piramides

		Piramida pyramid = new Piramida();

		bg.addChild(pyramid.createRotator());

		return bg;
	}

	/***********************************************************************/
	/****************************** AKSESORET ******************************/
	/***********************************************************************/
	public static BranchGroup getSceneBranchGroup() {
		return sceneBranchGroup;
	}

	public static Canvas3D getC3d() {
		return c3d;
	}

	public Node getShapeClicked() {
		return shapeClicked;
	}

	public TransformGroup getTgArray() {
		return tgArray;
        }

	/***********************************************************************/
	/************************* DEGJUESIT E INPUTEVE ************************/
	/***********************************************************************/
	public void mouseClicked(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		try {

			mouseX = e.getX();
			mouseY = e.getY();

			mouseButton = e.getButton();

			pickCanvas.setShapeLocation(e);
			PickResult result = pickCanvas.pickClosest();

			if (mouseButton == MouseEvent.BUTTON1) {

				if (result == null) {
					System.err.println("Nuk ke klikuar asgje\nhint: Krijo dicka per te klikuar");
				}

				else {
					shapeToDrag = result.getNode(PickResult.SHAPE3D);
					shapeClicked = result.getNode(PickResult.SHAPE3D);
				}

				if (shapeToDrag.getClass().getName().equals("Piramida")) {
					tx = ((Piramida) shapeToDrag).getTx();
					ty = ((Piramida) shapeToDrag).getTy();
				}
			}
		} catch (NullPointerException npe) {}
	}

	public void mouseMoved(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		tx += (x - mouseX) * 0.02f;
		ty -= (y - mouseY) * 0.02f;

		Transform3D dragShape = new Transform3D();
		dragShape.setTranslation(new Vector3f(tx, ty, 0.0f));

		if (mouseButton == MouseEvent.BUTTON3) {
		}

		else if (shapeToDrag == null) {
			//System.out.println("NULL");
			return;
		}

		else if (mouseButton == MouseEvent.BUTTON1) {

			if (shapeToDrag.getClass().getName().equals("Piramida")) {
				((Piramida) shapeToDrag).setTx(tx);
				((Piramida) shapeToDrag).setTy(ty);
				((Piramida) shapeToDrag).getTg().setTransform(dragShape);
			}
		}

		mouseX = x;
		mouseY = y;
	}

	public void mouseReleased(MouseEvent e) {
		if (mouseButton == MouseEvent.BUTTON3) {
		}

		else if (mouseButton == MouseEvent.BUTTON1) {
			try{
                            if(shapeToDrag.getClass().getName().equals("Piramida")){
				((Piramida) shapeToDrag).setTx(tx);
				((Piramida) shapeToDrag).setTy(ty);
			}}
                        catch(NullPointerException npe){}
		}
		shapeToDrag = null;
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}


























































