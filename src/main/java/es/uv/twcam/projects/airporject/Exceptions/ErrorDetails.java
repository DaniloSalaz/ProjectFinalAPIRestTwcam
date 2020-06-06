package es.uv.twcam.projects.airporject.Exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetails {

	private String errorCode;
	private List<String> errorsMessages;
	private String devErrorMessage;
	private Map<String, Object> additionalData = new HashMap<>();

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getErrorsMessages() {
		return errorsMessages;
	}

	public void setErrorsMessages(List<String> errorMessage) {
		this.errorsMessages = errorMessage;
	}

	public String getDevErrorMessage() {
		return devErrorMessage;
	}

	public void setDevErrorMessage(String devErrorMessage) {
		this.devErrorMessage = devErrorMessage;
	}

	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(Map<String, Object> additionalData) {
		this.additionalData = additionalData;
	}
}
