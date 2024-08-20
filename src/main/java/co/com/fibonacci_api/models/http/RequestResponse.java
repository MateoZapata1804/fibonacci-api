package co.com.fibonacci_api.models.http;

public class RequestResponse {
	
	private Boolean success;
	private String message;
	private String url;
	private Object result;
	
	public RequestResponse() { }
	
	public RequestResponse(Boolean success, String message, String url, Object result) {
		this.success = success;
		this.message = message;
		this.url = url;
		this.result = result;
	}

	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
