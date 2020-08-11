import javax.media.j3d.*;
import javax.vecmath.*;


public class Piramida extends Shape3D {
	private RotationInterpolator rotator;
	
	private TransformGroup tg;
	
	private Alpha rotationAlpha;
	private TriangleArray pyramidGeometry;
	private TriangleArray pyramidEdgeGeometry;
	
	private Appearance app;
	
	public Alpha getRotationAlpha() {
		return rotationAlpha;
	}
	
    public RotationInterpolator getRotator() {
		return rotator;
	}
	
	float tx = 0.0f;
	private float ty = 0.0f;	
	
	Point3f frontL = new Point3f(-1.0f, -1.0f, 1.0f); // para majtas
	Point3f frontR = new Point3f(1.0f, -1.0f, 1.0f); // para djathtas
	Point3f backR = new Point3f(1.0f, -1.0f, -1.0f); // prapa djathtas
	Point3f backL = new Point3f(-1.0f, -1.0f, -1.0f); // prapa majtas
	Point3f top = new Point3f(0.0f, 1.0f, 0.0f); // kulmi larte

    
    public Piramida() {
		pyramidGeometry = new TriangleArray(18, TriangleArray.COORDINATES | GeometryArray.COLOR_3);
		pyramidGeometry.setCapability(TriangleArray.ALLOW_COLOR_WRITE);
		
		face(pyramidGeometry, 0, frontR, top, frontL, Colors.ORANGE);
		face(pyramidGeometry, 3, backR, top, frontR, Colors.DARK_RED);
		face(pyramidGeometry, 6, backL, top, backR, Colors.ORANGE);
		face(pyramidGeometry, 9, frontL, top, backL, Colors.DARK_RED);
		face(pyramidGeometry, 12, backL, backR, frontR, Colors.DARK_RED);
		face(pyramidGeometry, 15, frontR, frontL, backL, Colors.DARK_RED);

		this.setGeometry(pyramidGeometry);
    }
    
    private void face(TriangleArray pyramidGeometry, int index, Point3f coordinate1, Point3f coordinate2, Point3f coordinate3, Color3f color) {
    	pyramidGeometry.setCoordinate(index, coordinate1);
    	pyramidGeometry.setCoordinate(index+1, coordinate2);
    	pyramidGeometry.setCoordinate(index+2, coordinate3);
    	pyramidGeometry.setColor(index, color);
    	pyramidGeometry.setColor(index+1, color);
    	pyramidGeometry.setColor(index+2, color);
    }
    
    private void edge(TriangleArray pyramidEdgeGeometry, int index, Point3f coordinate1, Point3f coordinate2, Point3f coordinate3, Color3f color) {
    	pyramidEdgeGeometry.setCoordinate(index, coordinate1);
    	pyramidEdgeGeometry.setCoordinate(index+1, coordinate2);
    	pyramidEdgeGeometry.setCoordinate(index+2, coordinate3);
    	pyramidEdgeGeometry.setColor(index, color);
    	pyramidEdgeGeometry.setColor(index+1, color);
    	pyramidEdgeGeometry.setColor(index+2, color);
    }
    
    public Node pyramidEdges() {
		pyramidEdgeGeometry = new TriangleArray(18, TriangleArray.COORDINATES | GeometryArray.COLOR_3);
		pyramidEdgeGeometry.setCapability(TriangleArray.ALLOW_COLOR_WRITE);
		
		edge(pyramidEdgeGeometry, 0, frontR, top, frontL, Colors.YELLOW);
		edge(pyramidEdgeGeometry, 3, backR, top, frontR, Colors.YELLOW);
		edge(pyramidEdgeGeometry, 6, backL, top, backR, Colors.YELLOW);
		edge(pyramidEdgeGeometry, 9, frontL, top, backL, Colors.YELLOW);
		//edge(pyramidEdgeGeometry, 12, backL, backR, frontR, Colors.ORANGE);
		//edge(pyramidEdgeGeometry, 15, frontR, frontL, backL, Colors.ORANGE);
				
		app = new Appearance();
		app.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
		
		// Ndreqja e atributeve te poligonit
        PolygonAttributes pa = new PolygonAttributes();
        pa.setPolygonMode(pa.POLYGON_LINE);
        pa.setCullFace(pa.CULL_NONE);
        pa.setPolygonOffsetFactor(-0.5f);
        app.setPolygonAttributes(pa);
        
        LineAttributes lineattributes = new LineAttributes();
        lineattributes.setLineWidth(2.0f);
        lineattributes.setLineAntialiasingEnable(true);
        lineattributes.setLinePattern(LineAttributes.PATTERN_SOLID);
        
        app.setLineAttributes(lineattributes);
        
        Shape3D pyramidEdges = new Shape3D();
		pyramidEdges.setGeometry(pyramidEdgeGeometry);
		pyramidEdges.setAppearance(app);
		
		return pyramidEdges;
    }
    
    
    
    public TriangleArray getPyramidGeometry() {
		return pyramidGeometry;
	}

	public TriangleArray getPyramidEdgeGeometry() {
		return pyramidEdgeGeometry;
	}

	TransformGroup createRotator() {
    	Transform3D yAxis = new Transform3D();
		    
		 TransformGroup spin = new TransformGroup(yAxis);
		 
		 
		 spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		 

		 spin.addChild(this); //shtoje formen e piramides ne spin TG
		 spin.addChild(pyramidEdges());

		    
		rotationAlpha = new Alpha(0, Alpha.INCREASING_ENABLE, 0, 0, 4000, 0, 0, 0, 0, 0);
			
		rotator = new RotationInterpolator(rotationAlpha, spin, yAxis, 0.0f, (float) Math.PI*2.0f);

		    
		 BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		 rotator.setSchedulingBounds(bounds);
		 spin.addChild(rotator);  //shtoje rotator RI ne spin TG
		 
		 
		 TransformGroup tg = new TransformGroup() ;
		 tg.setCapability( TransformGroup.ALLOW_TRANSFORM_WRITE ) ;
		 setTg(tg);
		 
		 tg.addChild(spin) ;
		 
		 return tg;
    }
	
	public TransformGroup getTg() {
		return tg;
	}

	public void setTg(TransformGroup tg) {
		this.tg = tg;
	}
	
	public float getTx() {
		return tx;
	}

	public void setTx(float tx) {
		this.tx = tx;
	}

	public float getTy() {
		return ty;
	}

	public void setTy(float ty) {
		this.ty = ty;
	}
}
