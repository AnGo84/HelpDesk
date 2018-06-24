package ua.helpdesk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.helpdesk.dao.UserTypeDao;
import ua.helpdesk.model.UserType;


@Service("userTypeService")
@Transactional
public class UserTypeServiceImpl implements UserTypeService {
	
	@Autowired
	UserTypeDao dao;
	
	public UserType findById(Integer id) {
		return dao.findById(id);
	}

	public UserType findByName(String name){
		return dao.findByName(name);
	}

	public List<UserType> findAll() {
		return dao.findAll();
	}
}
