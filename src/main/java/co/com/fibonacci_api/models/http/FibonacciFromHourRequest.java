package co.com.fibonacci_api.models.http;

public class FibonacciFromHourRequest {
	
	private String hour;
	private Boolean sendMail;

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}
	
	public Boolean getSendMail() {
		return sendMail;
	}
	
	public void setSendMail(Boolean sendMail) {
		this.sendMail = sendMail;
	}
}
