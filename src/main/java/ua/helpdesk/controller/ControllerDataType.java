package ua.helpdesk.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ControllerDataType {
	USER("users_list_page", "/users", "user_page"),
	SERVICES("services_list_page", "/services", "service_page");

	private String listPage;
	private String listPageURL;
	private String recordPage;

}
