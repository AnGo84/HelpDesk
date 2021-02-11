package ua.helpdesk.controller;

import lombok.Getter;

@Getter
public enum ControllerDataType {
	USER("users_list_page", "user_page");


	private String listPage;
	private String recordPage;

	ControllerDataType(String listPage, String recordPage) {
		this.listPage = listPage;
		this.recordPage = recordPage;
	}
}
