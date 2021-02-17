package ua.helpdesk.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ControllerDataType {
	USER("users_list_page", "/users", "user_page"),
	SERVICES("services_list_page", "/services", "service_page"),
	CATEGORY("categories_list_page", "/categories", "category_page"),
	TICKET("tickets_list_page", "/tickets", "ticket_page");

	private String listPage;
	private String listPageURL;
	private String recordPage;

}
