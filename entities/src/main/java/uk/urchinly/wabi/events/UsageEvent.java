/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.events;

import java.io.Serializable;
import java.time.LocalDateTime;

public class UsageEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	
	private Object object;
	
	private LocalDateTime currentTime = LocalDateTime.now();
	
	public UsageEvent(String status, Object object) {
		super();
		this.status = status;
		this.object = object;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getObject() {
		return this.object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public LocalDateTime getCurrentTime() {
		return this.currentTime;
	}	
	
}
