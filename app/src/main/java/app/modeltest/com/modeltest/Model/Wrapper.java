/**
 * 
 */
package app.modeltest.com.modeltest.Model;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * @author Amit
 *
 */
public class Wrapper {
	@SerializedName("data")
	private JsonObject data;
	@SerializedName("status")
	private Status status;
	
	public JsonObject getData() {
		return data;
	}

	public void setData(JsonObject data) {
		this.data = data;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
