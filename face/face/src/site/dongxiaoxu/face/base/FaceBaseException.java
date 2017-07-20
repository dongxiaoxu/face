package site.dongxiaoxu.face.base;

/**
 * 人脸识别异常
 * @author dongxiaoxu
 *
 */
public class FaceBaseException extends RuntimeException{

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1L;

	public FaceBaseException() {
		super();
	}
	
	public FaceBaseException(String msg) {
		super(msg);
	}
	
	public FaceBaseException(Throwable cause) {
		super(cause);
	}
	
	public FaceBaseException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
