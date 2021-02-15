package ua.helpdesk.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FieldErrorData {
	private String fieldName;
	private String fieldLabel;
	private String fieldValue;
}
