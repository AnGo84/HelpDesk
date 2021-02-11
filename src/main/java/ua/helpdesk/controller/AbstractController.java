package ua.helpdesk.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import ua.helpdesk.entity.AbstractEntity;
import ua.helpdesk.service.CommonService;

import java.util.Locale;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractController<E extends AbstractEntity, S extends CommonService<E>>
		implements CommonController<E> {

	public static final String ATTRIBUTE_READ_ONLY = "readOnly";
	public static final String OBJECT_ATTRIBUTE = "object";
	private final ControllerDataType controllerData;
	private final Class<E> objectClass;
	private final S service;
	@Value("${spring.web.locale:en}")
	private String springLocale;

	@Override
	public String allRecords(Model model) {
		model.addAttribute("objectsList", service.getAll());
		return controllerData.getListPage();
	}

	@Override
	public String addRecord(Model model) {
		log.info("Add new '{}' record", objectClass);
		model.addAttribute(ATTRIBUTE_READ_ONLY, false);
		model.addAttribute(OBJECT_ATTRIBUTE, createInstance(objectClass));
		return controllerData.getRecordPage();
	}

	@Override
	public String editRecord(Long id, Model model) {
		log.info("Edit '{}' with ID= {}", objectClass, id);
		model.addAttribute(ATTRIBUTE_READ_ONLY, true);
		model.addAttribute(OBJECT_ATTRIBUTE, service.get(id));
		return controllerData.getRecordPage();
	}

	@Override
	public String deleteRecord(Long id) {
		log.info("Delete {} with ID= {}", objectClass, id);
		service.deleteById(id);
		return "redirect:" + controllerData.getListPage();
	}

	protected Locale getSpringWebLocale() {
		return new Locale(springLocale);
	}
}
