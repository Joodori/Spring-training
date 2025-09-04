package lx.edu.springmvc.vo;

public class RequestLogVO {
	
	int id;
	String req_class;
	String req_method;
	String req_time;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReq_class() {
		return req_class;
	}
	public void setReq_class(String req_class) {
		this.req_class = req_class;
	}
	public String getReq_method() {
		return req_method;
	}
	public void setReq_method(String req_method) {
		this.req_method = req_method;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	@Override
	public String toString() {
		return "ReqestLogVO [id=" + id + ", req_class=" + req_class + ", req_method=" + req_method + ", req_time="
				+ req_time + "]";
	}	
}
