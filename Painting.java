import java.awt.Component;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.*;  
import java.awt.event.*;  
import javax.swing.*;  

public class Painting extends JPanel implements ActionListener, MouseListener {

    private int WIDTH = 800; 
    private int HEIGHT = 600; 
    private int SCALE = 200;
    private float OFFSETX = 2f;
    private float OFFSETY = 1.5f;
    private int ITERATIONS = Integer.parseInt(FractalGenerator.mandelIter.getText()); 
    private int ZOOM = Integer.parseInt(FractalGenerator.zoom.getText());
    private double zoom = 1.5;
    private float hueOffset = FractalGenerator.slider.getValue()/100f;
    private BufferedImage buffer;
    private int max = Integer.parseInt(FractalGenerator.maxIter.getText());
    private int sideLength = Integer.parseInt(FractalGenerator.sideLength.getText());
    private int startX = Integer.parseInt(FractalGenerator.startingX.getText());
    private int angle = Integer.parseInt(FractalGenerator.startingAngle.getText());
    private boolean isSet = true;
    private boolean isMandelbrot = true;
    private String[] rgbaLineInputs = FractalGenerator.RGBAline.getText().split(" ");
    private Color lineCol = new Color(Float.parseFloat(rgbaLineInputs[0]), Float.parseFloat(rgbaLineInputs[1]), Float.parseFloat(rgbaLineInputs[2]), Float.parseFloat(rgbaLineInputs[3]));
    private String[] rgbaFillInputs = FractalGenerator.RGBAfill.getText().split(" ");
    private Color fillCol = new Color(Float.parseFloat(rgbaFillInputs[0]), Float.parseFloat(rgbaFillInputs[1]), Float.parseFloat(rgbaFillInputs[2]), Float.parseFloat(rgbaFillInputs[3]));
    private String[] rgbinputs = FractalGenerator.RGB.getText().split(" ");
    private Color insColor = new Color(Integer.parseInt(rgbinputs[0]), Integer.parseInt(rgbinputs[1]), Integer.parseInt(rgbinputs[2]));

   ArrayList<PythSq> sqs = new ArrayList<PythSq>();

    /**
     * Create a new painting.
     */
    public Painting() {
        setPreferredSize(new Dimension(800, 600)); // make panel 800 by 450 pixels.
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        addMouseListener(this);  
        renderSet();
    }

    @Override
    protected void paintComponent(Graphics g) { // draw all your shapes
        super.paintComponent(g); // clears the panel
        if(isSet){
            g.drawImage(buffer, 0, 0, null);
        } else{
            for(PythSq i : sqs){
            i.draw(g);
        }
        setBackground(Color.BLACK);
        }
    }

    public void renderSet(){
        for(int x=0; x<WIDTH; x++){
            for (int y = 0; y<HEIGHT;y++){
                int color = isMandelbrot ? (mandelbrot((float)(x)/SCALE-OFFSETX, (float)(y)/SCALE-OFFSETY)) : (phoenix((float)(x)/SCALE-OFFSETX, (float)(y)/SCALE-OFFSETY, 0.56667f, -0.5f));
                buffer.setRGB(x, y, color);
            }
        }
    }

    private int mandelbrot(float x, float y){
        float cx = x;
        float cy = y;
        int i = 0;
        for(;i<ITERATIONS;i++){
            float nx = x*x - y*y + cx;
            float ny = 2*x*y + cy;
            x = nx;
            y = ny;

            if(x*x + y*y > 4){
                break;
            }
        }
        if(i == ITERATIONS){
                return insColor.getRGB();
            }
            
        return Color.HSBtoRGB(((float) i/ITERATIONS + hueOffset)%1, 0.7f, i/(i+8f));
    }

    private int phoenix(float zr, float zi, float cr, float ci) {
        float pr = zr*zr, pi = zi*zi;
        float sr = 0.0f, si = 0.0f;
        float zm = 0.0f;
        int count = 0;
        while (pr + pi < 4.0 && count < ITERATIONS) {
          zm = pr + pi;
          pr = pr - pi + ci*sr + cr;
          pi = 2.0f*zr*zi + ci*si;
          sr = zr;
          si = zi;
          zr = pr;
          zi = pi;
          pr = zr*zr;
          pi = zi*zi;
          count++;
        }
        if (count == 0 || count == ITERATIONS){
            return insColor.getRGB();
        } 
        return Color.HSBtoRGB(((float) count/ITERATIONS + hueOffset)%1, 0.7f, count/(count+8f));
      }

      public void pythagoras(){
        sqs.clear();
        int[] x = new int[]{startX, startX + sideLength, startX + sideLength, startX};
        int[] y = new int[]{startX, startX, startX + sideLength, startX + sideLength};
        PythSq sq1 = new PythSq(angle, 0, x, y, lineCol, fillCol);
        
        sqs.add(sq1);
        int last = 1;
        for(int k=0; k<max; k++){
            int lastTemp = 0;
            ArrayList<PythSq> sqsTemp = new ArrayList<PythSq>();
            for(int i = sqs.size() - 1; i >= sqs.size() - last; i--) {
                ArrayList<PythSq> sqsS= sqs.get(i).offspring();
                lastTemp += sqsS.size();
                sqsTemp.addAll(sqsS);
            }
            last = lastTemp;
            sqs.addAll(sqsTemp);
        }
      }

    /**
     * Reaction to button press.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if("Redraw".equals(e.getActionCommand())){
            if(isSet){
                ITERATIONS = Integer.parseInt(FractalGenerator.mandelIter.getText()); 
                ZOOM = Integer.parseInt(FractalGenerator.zoom.getText());
                hueOffset = FractalGenerator.slider.getValue()/100f;
                SCALE = 200;
                OFFSETX = 2f;
                OFFSETY = 1.5f;
                renderSet();
            }else{
                max = Integer.parseInt(FractalGenerator.maxIter.getText());
                sideLength = Integer.parseInt(FractalGenerator.sideLength.getText());
                startX = Integer.parseInt(FractalGenerator.startingX.getText());
                angle = Integer.parseInt(FractalGenerator.startingAngle.getText());
                rgbinputs = FractalGenerator.RGB.getText().split(" ");
                rgbaLineInputs = FractalGenerator.RGBAline.getText().split(" ");
                rgbaFillInputs = FractalGenerator.RGBAfill.getText().split(" ");
                insColor = new Color(Integer.parseInt(rgbinputs[0]), Integer.parseInt(rgbinputs[1]), Integer.parseInt(rgbinputs[2]));
                fillCol = new Color(Float.parseFloat(rgbaFillInputs[0]), Float.parseFloat(rgbaFillInputs[1]), Float.parseFloat(rgbaFillInputs[2]), Float.parseFloat(rgbaFillInputs[3]));
                lineCol = new Color(Float.parseFloat(rgbaLineInputs[0]), Float.parseFloat(rgbaLineInputs[1]), Float.parseFloat(rgbaLineInputs[2]), Float.parseFloat(rgbaLineInputs[3]));
            pythagoras();
            }
            paintComponent(this.getGraphics());
        }else if(e.getSource() == FractalGenerator.set){
            if(isSet){
                FractalGenerator.colorM.setVisible(false);
                FractalGenerator.colorP.setVisible(false);
                FractalGenerator.settingsM.setVisible(true);
                FractalGenerator.settingsP.setVisible(false);
            }else{
                FractalGenerator.colorM.setVisible(false);
                FractalGenerator.colorP.setVisible(false);
                FractalGenerator.settingsM.setVisible(false);
                FractalGenerator.settingsP.setVisible(true);
            }
        }else if(e.getSource() == FractalGenerator.col){
            if(isSet){
                FractalGenerator.colorM.setVisible(true);
                FractalGenerator.colorP.setVisible(false);
                FractalGenerator.settingsM.setVisible(false);
                FractalGenerator.settingsP.setVisible(false);
            }else{
                FractalGenerator.colorM.setVisible(false);
                FractalGenerator.colorP.setVisible(true);
                FractalGenerator.settingsM.setVisible(false);
                FractalGenerator.settingsP.setVisible(false);
            }
        }else if(e.getSource() == FractalGenerator.pyth){
                isSet = false;
                isMandelbrot = true;
                paintComponent(this.getGraphics());
                FractalGenerator.colorM.setVisible(false);
                FractalGenerator.colorP.setVisible(false);
                FractalGenerator.settingsM.setVisible(false);
                FractalGenerator.settingsP.setVisible(true);
            
        }else if(e.getSource() == FractalGenerator.mand){
            isSet = true;
            isMandelbrot = true;
            renderSet();
            paintComponent(this.getGraphics());
            FractalGenerator.colorM.setVisible(false);
            FractalGenerator.colorP.setVisible(false);
            FractalGenerator.settingsM.setVisible(true);
            FractalGenerator.settingsP.setVisible(false);
        }else if(e.getSource() == FractalGenerator.phoe){
            isSet = true;
            isMandelbrot = false;
            renderSet();
            paintComponent(this.getGraphics());
            FractalGenerator.colorM.setVisible(false);
            FractalGenerator.colorP.setVisible(false);
            FractalGenerator.settingsM.setVisible(true);
            FractalGenerator.settingsP.setVisible(false);
        }   
        FractalGenerator.frame.pack();
    }

    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        
    }
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        hueOffset = FractalGenerator.slider.getValue()/100f;
        
    }
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        if(isSet){
            
            ITERATIONS = Integer.parseInt(FractalGenerator.mandelIter.getText()); 
            ZOOM = Integer.parseInt(FractalGenerator.zoom.getText());
            rgbinputs = FractalGenerator.RGB.getText().split(" ");
            insColor = new Color(Integer.parseInt(rgbinputs[0]), Integer.parseInt(rgbinputs[1]), Integer.parseInt(rgbinputs[2]));
            hueOffset = FractalGenerator.slider.getValue()/100f;
            float cx1 = (OFFSETX)/SCALE;
            float cx2 = (WIDTH + OFFSETX)/SCALE;
            float cy1 = (OFFSETX)/SCALE;
            float cy2 = (HEIGHT + OFFSETX)/SCALE;
            float mx = e.getX();
            float my = e.getY();
            if(!e.isPopupTrigger()){
                OFFSETX += -(mx-WIDTH/2)/SCALE;
                OFFSETY += -(my-HEIGHT/2)/SCALE;
            }
            SCALE *= ( (ZOOM > -100 ? 1 + (float)ZOOM/100 : 0.00000001));
            renderSet();
            repaint();
        }
        
    }
    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

}
