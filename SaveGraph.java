/* 
Project: Retirement Account Calculator
Course: CSCI-185 M01 - Computer Programming II
Contributors: Sydney Jacob
Last Contribution: 05/15/2026
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SaveGraph implements ActionListener {
    private File graph;

    public SaveGraph(String dir) throws IOException{
        graph = new File(dir);
        if (graph.createNewFile()){
            System.out.println("File has been created");
        }
        else System.out.println("File already exists");

    }

    public void saveImage(JFrame frame){
        try{
            Container contentPane = frame.getContentPane();
            BufferedImage image = new BufferedImage(
                    contentPane.getWidth(),
                    contentPane.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D g2d = image.createGraphics();
            contentPane.printAll(g2d);
            g2d.dispose();

            ImageIO.write(image, "png", graph);
            System.out.println("Image saved successfully");
        }
        catch (IOException e){
            System.out.println("An error occurred when saving the image");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        saveImage(RetirementChart.frame);
    }
}
