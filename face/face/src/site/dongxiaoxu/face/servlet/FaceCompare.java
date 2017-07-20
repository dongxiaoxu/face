package site.dongxiaoxu.face.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dongxiaoxu.face.utils.FaceUtils;
import sun.misc.BASE64Decoder;

public class FaceCompare extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String image;
		image = request.getParameter("img");
		byte[] images;
		images = decode(image);
		BufferedImage picture = ImageIO.read(new ByteArrayInputStream(images));
		ImageIO.write(picture, "png", new FileOutputStream("D:/a.png"));
		Double score;
		score = FaceUtils.getSimilarScore("D:/a.png", "D:/1.jpg");
		System.out.println(score);
		response.setContentType("text/html");
		response.getWriter().print(score == null ? 0 : score);

	}

	public static byte[] decode(String str) {
		byte[] bt = null;
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			return null;
		}
		return bt;
	}
}
