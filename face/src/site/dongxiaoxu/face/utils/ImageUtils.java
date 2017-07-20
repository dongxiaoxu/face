package site.dongxiaoxu.face.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageUtils {
	//私有化构造器
	private ImageUtils(){}
	
	
	public static void drawRect(BufferedImage image, int left, int top, int width, int height, Color color) {
		Graphics graphics;
		graphics = image.getGraphics();
		graphics.setColor(color);
		graphics.drawRect(left, top, width, height);
	}
	

}
