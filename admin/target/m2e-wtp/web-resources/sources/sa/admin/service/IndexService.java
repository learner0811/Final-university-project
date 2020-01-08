package sa.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sa.admin.dao.UserDao;

@Service
public class IndexService {
	@Autowired
	private UserDao userDao;
}
