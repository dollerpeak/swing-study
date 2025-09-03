/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package study.netbeans.util;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author P088454
 */
public class ImagePanel extends JPanel {
    private Image img;

    public ImagePanel(String resourcePath) {
        // getClass().getResource()는 JAR 내부 리소스도 지원
        this.img = new ImageIcon(getClass().getResource(resourcePath)).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img != null) {
            g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
