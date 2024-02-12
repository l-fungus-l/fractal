import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PythSq {
    private int angle;
    private int currentAngle;
    private int side;
    private Color lineCol;
    private Color fillCol;
    private int[] x;
    private int[] y;

    public PythSq(int ang, int curAng, int[]xPoints, int[] yPoints, Color lc, Color fc){
        angle = ang;
        x = xPoints;
        y = yPoints;
        lineCol = lc;
        fillCol = fc;
        currentAngle = curAng;
    }

    public void draw(Graphics g){
        g.setColor(fillCol);
        g.fillPolygon(x, y, 4);
        g.setColor(lineCol);
        g.drawPolygon(x, y, 4);
    }

    public ArrayList<PythSq> offspring(){
        double ang = Math.toRadians(angle+currentAngle);
        int width = (int) (Math.cos(Math.toRadians(angle))*Math.sqrt(Math.pow(x[1]-x[0], 2) + Math.pow(y[1]-y[0], 2)));
        int[] x1 = new int[]{(int) (x[0]-width*Math.sin(ang)), (int)(x[0]-width*Math.sin(ang) + width*Math.cos(ang)), (int)(x[0]+width*Math.cos(ang)), x[0]};
        int[] y1 = new int[]{(int) (y[0]-width*Math.cos(ang)), (int)(y[0]-width*Math.cos(ang) - width*Math.sin(ang)), (int)(y[0]-width*Math.sin(ang)), y[0]};
        PythSq s1 = new PythSq(angle, currentAngle + angle, x1, y1, lineCol, fillCol);
        double ang2 = Math.toRadians(angle+currentAngle);
        int width2 = (int) (Math.sin(Math.toRadians(angle))*Math.sqrt(Math.pow(x[1]-x[0], 2) + Math.pow(y[1]-y[0], 2)));
        int[] x2 = new int[]{(int)(x[1]-width2*Math.sin(ang2) + width2*Math.cos(ang2)), (int) (x[1]+width2*Math.cos(ang2)), x[1], (int)(x[1]-width2*Math.sin(ang2))};
        int[] y2 = new int[]{(int)(y[1]-width2*Math.cos(ang2) - width2*Math.sin(ang2)), (int) (y[1]-width2*Math.sin(ang2)), y[1], (int)(y[1]-width2*Math.cos(ang2))};
        PythSq s2 = new PythSq(angle, currentAngle - 90 + angle, x2, y2, lineCol, fillCol);
        ArrayList<PythSq> sqs = new ArrayList<PythSq>();
        if(width >=1){
            sqs.add(s1);
        }
        if(width2 >=1){
            sqs.add(s2);
        }
        return sqs;
    }
}
