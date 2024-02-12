import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Graphics;

import javax.swing.*;  

/** 
 * Main class for the homework assignment Random Animator.
 * 
 *  
 * TODO: Add implementation of saving a loading settings
 *
 * @author Maxim Selveliev
 */
public class FractalGenerator {
    public static JFrame frame;
    Painting painting; // panel that provides the random painting
    JPanel panel;
    public static JPanel colorP;
    public static JPanel colorM;
    public static JPanel settingsP;
    public static JPanel settingsM;
    JButton regenerateButton;
    JButton regenerateButton2;
    JButton regenerateButton3;
    JButton regenerateButton4;
    JButton shotButton;
    JButton recolButton;
    JButton startButton;
    JButton stopButton;
    public static JMenuItem pyth, mand, phoe, save, load, col, set;
    public static JTextField RGB = new JTextField("0 0 0");
    public static JTextField RGBAfill = new JTextField("0 0 0 0");
    public static JTextField RGBAline = new JTextField("0 1 0 1");
    public static JTextField maxIter = new JTextField("15");
    public static JTextField sideLength = new JTextField("100");
    public static JTextField mandelIter = new JTextField("600");
    public static JTextField zoom = new JTextField("20");
    public static JTextField startingX = new JTextField("450");
    public static JTextField startingAngle = new JTextField("45");
    public static JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);

    /**
     * Create a new instance of the RandomAnimator application.
     */
    FractalGenerator() {
        // invokeLater: preferred way to create components
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                painting = new Painting();
                JMenuBar menuBar = new JMenuBar();
                JMenu file = new JMenu("File");
                JMenu fractals = new JMenu("Fractals");
                JMenu tools = new JMenu("Tools");
                menuBar.add(file);
                menuBar.add(fractals);
                menuBar.add(tools);
                pyth = new JMenuItem("Pythagoras tree");
                mand = new JMenuItem("Mandelbrot set");
                phoe = new JMenuItem("Phoenix set");
                save = new JMenuItem("Save settings");
                load = new JMenuItem("Load settings");
                col = new JMenuItem("Color");
                set = new JMenuItem("Settings");
                file.add(save);
                file.add(load);
                fractals.add(pyth);
                fractals.add(mand);
                fractals.add(phoe);
                tools.add(col);
                tools.add(set);
                frame = new JFrame("Fractal Generator");
                frame.setJMenuBar(menuBar);
                regenerateButton = new JButton("Redraw");
                regenerateButton2 = new JButton("Redraw");
                regenerateButton3 = new JButton("Redraw");
                regenerateButton4 = new JButton("Redraw");
                
                
                // painting provides reaction to buttonclick
                regenerateButton.addActionListener(painting); 
                regenerateButton2.addActionListener(painting); 
                regenerateButton3.addActionListener(painting); 
                regenerateButton4.addActionListener(painting);
                pyth.addActionListener(painting); 
                mand.addActionListener(painting); 
                phoe.addActionListener(painting); 
                col.addActionListener(painting); 
                set.addActionListener(painting); 
                recolButton = new JButton("Recolor");
                shotButton = new JButton("Screenshot");
                shotButton.addActionListener(painting);
                recolButton.addActionListener(painting);
                frame.addMouseListener(painting);
                panel = new JPanel();
                settingsP = new JPanel();
                settingsM = new JPanel();
                colorM = new JPanel();
                
                colorM.setLayout(new GridLayout(5,0));  
                colorM.add(new JLabel("Set color (R G B format):"));
                colorM.add(RGB);
                colorM.add(new JLabel("Hue offset:"));
                colorM.add(slider);
                colorM.add(regenerateButton);
                colorP = new JPanel();
                colorP.setLayout(new GridLayout(7,0));  
                colorP.add(new JLabel("Line color (R G B A format)"));
                colorP.add(new JLabel("(values from 0 to 1 only):"));
                colorP.add(RGBAline);
                colorP.add(new JLabel("Fill color (R G B A format)"));
                colorP.add(new JLabel("(values from 0 to 1 only):"));
                colorP.add(RGBAfill);
                colorP.add(regenerateButton3);
                
                settingsM.setLayout(new GridLayout(6,0));
                settingsM.add(new JLabel("Max iterations:"));
                settingsM.add(mandelIter);
                settingsM.add(new JLabel("Zoom with each mouse click:"));
                settingsM.add(zoom);
                settingsM.add(regenerateButton2);
                
                settingsP.setLayout(new GridLayout(9,0));
                settingsP.add(new JLabel("Max iterations:"));
                settingsP.add(maxIter);
                settingsP.add(new JLabel("Initial side length:"));
                settingsP.add(sideLength);
                settingsP.add(new JLabel("Starting position:"));
                settingsP.add(startingX);
                settingsP.add(new JLabel("Left angle:"));
                settingsP.add(startingAngle);
                settingsP.add(regenerateButton4);

                panel.add(colorM, BorderLayout.CENTER);
                panel.add(colorP, BorderLayout.CENTER);
                panel.add(settingsM, BorderLayout.CENTER);
                panel.add(settingsP, BorderLayout.CENTER);
                colorM.setVisible(false);
                colorP.setVisible(false);
                settingsM.setVisible(true);
                settingsP.setVisible(false);
                JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, painting);
                splitPane.setOneTouchExpandable(true);
                frame.setBackground(Color.BLACK);
                frame.add(splitPane);
                frame.pack();
                frame.setResizable(false);
                frame.setVisible(true);
            }
        });
    }

    public static void main(String[] arg) {
        new FractalGenerator();
    }
}