package site.dongxiaoxu.face.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import site.dongxiaoxu.face.enumeration.FileNameStrategy;
import site.dongxiaoxu.face.utils.FileUpload;

public class FileUploadTest extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		FileUpload upload;
		upload = new FileUpload(request);
		System.out.println(upload.saveFile("D:/temp", FileNameStrategy.IPTIMERANDOM));
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}


}
