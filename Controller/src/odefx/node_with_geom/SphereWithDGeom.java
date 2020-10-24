package odefx.node_with_geom;


import javafx.collections.ObservableList;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.MatrixType;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import odefx.FxBody;
import org.ode4j.math.DMatrix3;
import org.ode4j.ode.DBody;
import org.ode4j.ode.DGeom;
import org.ode4j.ode.OdeHelper;

public class SphereWithDGeom extends Sphere implements Shape3DWithDGeom {
    private String name = "SphereWithDGeom";
    private DGeom dGeom;
    private Rotate relativeGeomRotate = null;
    private Translate relativeGeomTranslate = null;

    public SphereWithDGeom(double radius, FxBody fb){
        super(radius);
        dGeom = OdeHelper.createSphere(radius);
        fb.addGeom(dGeom);
    }

    public SphereWithDGeom(double radius, FxBody fb, String name){
        this(radius, fb);
        this.name = name;
        this.dGeom.setData(name);
    }

    public SphereWithDGeom(double radius, FxBody fb, DGeom dGeom){
        super(radius);
        this.dGeom = dGeom;
        fb.addGeom(dGeom);
    }

    public SphereWithDGeom(double radius, FxBody fb, DGeom dGeom, String name){
        this(radius, fb, dGeom);
        this.name = name;
        this.dGeom.setData(name);
    }

    public void setDGeom(DGeom geom){
        DBody db = dGeom == null? null : dGeom.getBody();
        if (dGeom != null) dGeom.destroy();
        dGeom = geom;
        if (dGeom != null && db != null) dGeom.setBody(db);
    }

    public DGeom getDGeom(){ return dGeom; }

    public void setName(String name){
        this.name = name;
        if (dGeom != null) dGeom.setData(name);
    }

    public String getName(){ return name; }

    public void setRelGeomRotate(Rotate rotate){ this.relativeGeomRotate = rotate; }

    public Rotate getRelGeomRotate(){ return relativeGeomRotate; }

    public void setRelGeomOffset(Translate translate) { this.relativeGeomTranslate = translate; }

    public Translate getRelGeomOffset () { return relativeGeomTranslate; }

    public void updateGeomOffset(Transform preTransform){
        if (dGeom == null) return;
        Transform transform = preTransform.clone();
        ObservableList<Transform> nodeTransforms = this.getTransforms();
        for (int i=0; i<nodeTransforms.size(); i++) transform = transform.createConcatenation(nodeTransforms.get(i));
        Rotate nodeRelGeomRotate = this.getRelGeomRotate();
        Translate nodeRelGeomOffset = this.getRelGeomOffset();
        if (nodeRelGeomOffset != null) transform = transform.createConcatenation(nodeRelGeomOffset);
        if (nodeRelGeomRotate != null) transform = transform.createConcatenation(nodeRelGeomRotate);
        double[] tData = transform.toArray(MatrixType.MT_3D_3x4);
        dGeom.setOffsetPosition(tData[3], tData[7], tData[11]);
        DMatrix3 dRotMatrix = new DMatrix3(tData[0], tData[1], tData[2], tData[4], tData[5], tData[6],
                tData[8], tData[9], tData[10]);
        dGeom.setOffsetRotation(dRotMatrix);
    }
}
