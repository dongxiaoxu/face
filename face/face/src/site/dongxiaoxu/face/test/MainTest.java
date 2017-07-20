package site.dongxiaoxu.face.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Random;

import org.json.JSONObject;
import org.junit.Test;

import site.dongxiaoxu.face.enumeration.FileNameStrategy;
import site.dongxiaoxu.face.utils.FaceUtils;
import site.dongxiaoxu.face.utils.PropUtils;

import com.baidu.aip.face.AipFace;

public class MainTest {

	@Test
	public void testStr() {
		String str;
		str = "a.properties";
		System.out.println(str.substring(str.lastIndexOf("/") + 1, str.lastIndexOf(".properties")));
	}
	
	public void testpro() throws IOException {
		Properties prop;
		prop = new Properties();
		prop.load(new FileInputStream("D://1.properties"));
	}
	
	@Test
	public void testLoadProperties() {
		PropUtils.loadPropByClassPath("face.properties", "face");
		PropUtils.removeProp("face");
		PropUtils.loadPropByFilePath("d:\\1.properties", "face");
		System.out.println(PropUtils.getProperty("face", "face.appid"));
	}
	
	@Test
	public void testFace() {
//		AipFace client = FaceUtils.getAipFace();
//		JSONObject res = client.detect("d:\\1.jpg", new HashMap<String, String>());
//		System.out.println(res.toString(2));
		System.out.println(FaceUtils.getSimilarScore("D:/1.jpg", "D:/3.jpg"));
	}
	
	@Test
	public void testRandom() {
		System.out.println( new Random().nextInt(10) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
