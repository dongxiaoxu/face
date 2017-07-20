package site.dongxiaoxu.face.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import site.dongxiaoxu.face.domain.Rectangle;

import com.baidu.aip.face.AipFace;

/**
 * 人脸识别工具类
 * @author dongxiaoxu
 *
 */
public class FaceUtils {
	
	//私有化构造器，不允许实例化对象
	private FaceUtils(){}
	
	private final static String APPID;
	
	private final static String APIKEY;
	
	private final static String SECRETKEY;
	
	private static AipFace client;
	
	static {
		PropUtils.loadPropByClassPath("face.properties", "face");
		APPID = PropUtils.getProperty("face", "face.appid");
		APIKEY = PropUtils.getProperty("face", "face.apikey");
		SECRETKEY = PropUtils.getProperty("face", "face.secretkey");
		client = new AipFace(APPID, APIKEY, SECRETKEY);
	}
	
	public static Double getSimilarScore(InputStream firstStream, InputStream secondStream) {
		byte[][] bytes;
		bytes = new byte[2][];
		try {
			bytes[0] = new byte[firstStream.available()];
			bytes[1] = new byte[secondStream.available()];
			JSONObject res;
			res = client.match(bytes, new HashMap<String, String>());
			return getSimilarScore(res);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static Double getSimilarScore(String firstPath, String secondPath) {
		List<String> params;
		params = new ArrayList<String>();
		params.add(firstPath);
		params.add(secondPath);
		JSONObject res;
		res = client.match(params, new HashMap<String, String>());
		return getSimilarScore(res);
	}
	
	public static AipFace getAipFace() {
		return client;
	}
	
    private static Double getSimilarScore(JSONObject res) {
    	if (res != null) {
			JSONArray results;
			try {
				results = res.getJSONArray("result");
				if (results != null && !results.isNull(0)) {
					JSONObject firstResult;
					firstResult = results.getJSONObject(0);
					return firstResult.getDouble("score");
				}
			} catch (JSONException e) {
				return null;
			}
		}
    	return null;
    }
    
    public static Rectangle getFacePosition(byte[] data) {
    	JSONObject res;
    	res = client.detect(data, new HashMap<String, String>());
    	Rectangle rect;
    	rect = new Rectangle();
    	try {
    		JSONArray result;
    		result = res.getJSONArray("result");
    		JSONObject location;
    		location = result.getJSONObject(0).getJSONObject("location");
    		rect.setTop(location.getInt("top"));
    		rect.setLeft(location.getInt("left"));
    		rect.setWidth(location.getInt("width"));
    		rect.setHeight(location.getInt("height"));
    	} catch (JSONException e) {
    		return null;
    	}
    	return rect;
    }
	
	
	
	
	
	
	
	
	
	
	
	
}
