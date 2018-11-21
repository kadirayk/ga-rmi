package rmi;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "CommandId", "SentTimeStamp", "ReceivetimeStamp", "Delay" })
public class Command {

	@JsonProperty("CommandId")
	private Integer commandId;
	@JsonProperty("SentTimeStamp")
	private Double sentTimeStamp;
	@JsonProperty("ReceivetimeStamp")
	private Double receivetimeStamp;
	@JsonProperty("Delay")
	private String delay;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("CommandId")
	public Integer getCommandId() {
		return commandId;
	}

	@JsonProperty("CommandId")
	public void setCommandId(Integer commandId) {
		this.commandId = commandId;
	}

	@JsonProperty("SentTimeStamp")
	public Double getSentTimeStamp() {
		return sentTimeStamp;
	}

	@JsonProperty("SentTimeStamp")
	public void Double(Double sentTimeStamp) {
		this.sentTimeStamp = sentTimeStamp;
	}

	@JsonProperty("ReceivetimeStamp")
	public Double getReceivetimeStamp() {
		return receivetimeStamp;
	}

	@JsonProperty("ReceivetimeStamp")
	public void setReceivetimeStamp(Double receivetimeStamp) {
		this.receivetimeStamp = receivetimeStamp;
	}

	@JsonProperty("Delay")
	public Double getDelay() {
		if(delay.indexOf("-")<0){
			return Double.parseDouble(delay);
		}
		return 0.0;
	}

	@JsonProperty("Delay")
	public void setDelay(String delay) {
		this.delay = delay;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}