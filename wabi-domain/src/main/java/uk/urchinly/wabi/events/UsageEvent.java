/**
 * Copyright (C) ${year} Urchinly <wabi@urchinly.uk>
 */
package uk.urchinly.wabi.events;

import java.time.LocalDateTime;

public class UsageEvent extends AbstractEvent {

	private static final long serialVersionUID = 1L;

	private String status;

	private String object;

	private LocalDateTime currentTime = LocalDateTime.now();

	public UsageEvent(String status, Object object) {
		super();
		this.status = status;
		this.object = object.toString();
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getObject() {
		return this.object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public LocalDateTime getCurrentTime() {
		return this.currentTime;
	}
}
