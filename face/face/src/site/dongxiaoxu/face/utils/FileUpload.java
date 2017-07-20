package site.dongxiaoxu.face.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import site.dongxiaoxu.face.enumeration.FileNameStrategy;

public final class FileUpload {
	
	private HttpServletRequest request;
	
	private List<FileItem> fileStreams = new ArrayList<FileItem>();
	
	private Map<String, String[]> params = new HashMap<String, String[]>();
	
	//默认解析文件名及参数的字符集
	private String charset = "utf-8";
	
	//默认最大上传文件大小为10M
	private long maxSize = 10 * 1024 * 1024;
	
	public FileUpload(HttpServletRequest request) {
		this.request = request;
		init();
	}
	
	public FileUpload(HttpServletRequest request, String charset) {
		this.request = request;
		this.charset = charset;
		init();
	}
	
	public FileUpload(HttpServletRequest request, long maxSize) {
		this.request = request;
		this.maxSize = maxSize;
		init();
	}
	
	public FileUpload(HttpServletRequest request, String charset, long maxSize) {
		this.request = request;
		this.charset = charset;
		this.maxSize = maxSize;
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		DiskFileItemFactory factory;
		factory = new DiskFileItemFactory();
		ServletFileUpload upload;
		upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxSize);
		upload.setHeaderEncoding(charset);
		if (!ServletFileUpload.isMultipartContent(request)) {
			//一般的请求（不包含文件）
			fileStreams = Collections.emptyList();
			params = request.getParameterMap();
		} else {
			try {
				List<FileItem> fileItems;
				fileItems = upload.parseRequest(request);
				Iterator<FileItem> iterator;
				iterator = fileItems.iterator();
				FileItem fileItem;
				while (iterator.hasNext()) {
					fileItem = iterator.next();
					if (fileItem.isFormField()) {
						//一般的参数
						String paramName;
						paramName = fileItem.getFieldName();
						String paramValue;
						paramValue = fileItem.getString();
						//如果已经包含该参数，则添加进新的值
						if (params.containsKey(paramName)) {
							String[] values;
							values =  addString(params.get(paramName), paramValue);
							params.put(paramName, values);
						} else {
							params.put(paramName, new String[]{paramValue});
						}
					} else {
						//文件
						String fileName;
						fileName = fileItem.getName();
						if (fileName != null && !"".equals(fileName.trim())) {
							fileStreams.add(fileItem);
						}
					}
				}
			} catch (FileUploadException e) {
				throw new RuntimeException(e);
			} 
		}
	}
	
	/**
	 * 向字符串数组中添加一个元素
	 * @param array 未添加元素前的数组
	 * @param e 需要添加的元素
	 * @return 添加元素后的数组
	 */
	private String[] addString(String[] array, String e) {
		if (array == null || e == null) {
			throw new RuntimeException("参数不允许为null!");
		}
		int length;
		length = array.length;
		if (length == 0) {
			return new String[] {e};
		}
		String[] temp = new String[length + 1];
		for (int i = 0; i < length; i++) {
			temp[i] = array[i];
		}
		temp[length] = e;
		return temp;
	}
	
	/**
	 * 获得指定参数的参数值
	 * @param paramName 参数名
	 * @return  参数值
	 */
	public String[] getParameter(String paramName) {
		return params.get(paramName);
	}
	
	/**
	 * 获得所有文件的输入流
	 * @return 文件输入流
	 */
	public List<FileItem> getFileStreams() {
		return fileStreams;
	}
	
	public boolean saveFile(String path, FileNameStrategy strategy) {
		Iterator<FileItem> i;
		i = fileStreams.iterator();
		InputStream in;
		while (i.hasNext()) {
			OutputStream out;
			FileItem item;
			try {
				item = i.next();
				in = item.getInputStream();
				String fileName;
				fileName  = item.getName();
				out = new FileOutputStream(path + File.separator + getFileName(strategy, fileName));
				byte[] buffer;
				buffer = new byte[4096];
				int length;
				while ( -1 != (length = in.read(buffer))) {
					out.write(buffer, 0, length);
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	private String getFileName(FileNameStrategy strategy, String fileName) {
		if (strategy == null || FileNameStrategy.DEFAULT.equals(strategy)) {
			return fileName;
		}
		String suffix;
		suffix = fileName.substring(fileName.lastIndexOf("."));
		if (strategy.equals(FileNameStrategy.TIME)) {
			fileName = this.getTimeStamp() + suffix;
		} else if (strategy.equals(FileNameStrategy.IPTIME)) {
			fileName = this.getIpAddress() + "_" + this.getTimeStamp() + suffix;
		} else if (strategy.equals(FileNameStrategy.IPTIMERANDOM)) {
			fileName = this.getIpAddress() + "_" + this.getTimeStamp() + "_" + new Random().nextInt(10) + suffix;
		}
		return fileName;
	}
	
	private String getTimeStamp() {
		SimpleDateFormat sdf;
		sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(new Date());
	}
	
	private String getIpAddress() {
		return request.getRemoteAddr().replace(".", "-");
	}
	
//	private String getIpAddress() {
//		String ipAddr;
//		ipAddr = request.getRemoteAddr();
//		String[] ipSplits;
//		ipSplits = ipAddr.split("\\.");
//		StringBuilder ip;
//		ip = new StringBuilder();
//		String eachSplit;
//		for (int i = 0; i < ipSplits.length; i++) {
//			eachSplit = ipSplits[i];
//			if (1 == eachSplit.length()) {
//				ip.append("00" + eachSplit);
//			} else if (2 == eachSplit.length()) {
//				ip.append("0" + eachSplit);
//			} else {
//				ip.append(eachSplit);
//			}
//		}
//		return ip.toString();
//	}
	
}
