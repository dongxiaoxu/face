package site.dongxiaoxu.face.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import site.dongxiaoxu.face.base.FaceBaseException;

/**
 * properties文件的工具类
 * @author dongxiaoxu
 *
 */
public class PropUtils {
	
	//私有化构造器，不允许实例化对象
	private PropUtils(){}
	
	/**
	 * 存放所有的properties对象
	 */
	private static Map<String, Properties> propertieses;
	
	static {
		//第一次加载类时初始化
		propertieses = new HashMap<String, Properties>();
	}
	
	public static void loadPropByFilePath(String filePath, String propName) {
		if (!filePath.contains(".properties")) {
			throw new FaceBaseException("路径：" + filePath + " 不是properties文件！");
		}
		//如果没有传入属性名，则根据文件解析出属性名
		if (propName == null) {
			propName = getPropName(filePath);
		}
		try {
			loadProperties(new FileInputStream(filePath), propName);
		} catch (FileNotFoundException e) {
			throw new FaceBaseException(e);
		}
	}
	
	public static void loadPropByClassPath(String classPath, String propName) {
		if (!classPath.contains(".properties")) {
			throw new FaceBaseException("路径：" + classPath + " 不是properties文件！");
		}
		//如果没有传入属性名，则根据文件解析出属性名
		if (propName == null) {
			propName = getPropName(classPath);
		}
		InputStream in;
		in = PropUtils.class.getClassLoader().getResourceAsStream(classPath);
		loadProperties(in, propName);
	}
	
	/**
	 * 根据路径解析出properties文件名
	 * @param path 路径
	 * @return 文件名
	 */
	private static String getPropName(String path) {
		String propName;
		//filePath中最后一个斜杠号的位置
		int lastSlashLocation;
		//filePath中最后一个反斜杠号的位置
		int lastBackSlashLocation;
		lastSlashLocation = path.lastIndexOf("/");
		lastBackSlashLocation = path.lastIndexOf("\\");
		if (lastSlashLocation > lastBackSlashLocation) {
			//文件名以斜杠号为最后的分隔符
			propName = path.substring(lastSlashLocation + 1, path.lastIndexOf(".properties"));
		} else {
			//文件名以反斜杠号为最后的分隔符
			propName = path.substring(lastSlashLocation + 1, path.lastIndexOf(".properties"));
		} 
		return propName;
	}
	
	
	/**
	 * 根据输入流和属性名加载properties,并添加进缓存
	 * @param inputStream 要加载的properties输入流
	 * @param propName 属性名
	 */
	private static void loadProperties(InputStream inputStream, String propName) {
		if (inputStream == null || propName == null) {
			throw new FaceBaseException("参数不可为null！");
		}
		//防止多线程时判断错误
		synchronized (propertieses) {
			if (propertieses.containsKey(propName)) {
				throw new FaceBaseException("属性名" + propName + "已加载！");
			}
		}
		Properties prop;
		prop = new Properties();
		try {
			//加载properties文件
			prop.load(inputStream);
		} catch (IOException e) {
			throw new FaceBaseException(e);
		}
		//添加进缓存
		propertieses.put(propName, prop);
	}
	
	/**
	 * 根据属性名和属性key获得属性值
	 * @param propName 属性名
	 * @param propKey 属性key
	 * @return 属性值
	 */
	public static String getProperty(String propName, String propKey) {
		Properties prop;
		prop = propertieses.get(propName);
		if (prop == null) {
			return null;
		} else {
			return prop.getProperty(propKey);
		}
				
	}
	
	/**
	 * 移除缓存中的属性文件
	 * @param propName 属性文件名
	 * @return 是否移除成功 true 成功 , false 失败（没有该属性名的缓存文件）
	 */
	public static boolean removeProp(String propName) {
		if (propertieses.remove(propName) == null) {
			return false;
		} else {
			return true;
		}
	}
	

}
