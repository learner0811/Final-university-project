package hn46.sa.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hn46.sa.dao.ConversationDao;
import hn46.sa.dao.ImageDao;
import hn46.sa.dao.MessageDao;
import hn46.sa.dao.RoomOwnerDao;
import hn46.sa.dao.TenantDao;
import hn46.sa.dao.UserDao;
import hn46.sa.entity.Conversation;
import hn46.sa.entity.Image;
import hn46.sa.entity.Message;
import hn46.sa.entity.RoomOwner;
import hn46.sa.entity.Tenant;
import hn46.sa.entity.User;

@Service
public class ConversationService {
	@Autowired
	private MessageDao messageDao;

	@Autowired
	private ConversationDao conversationDao;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoomOwnerDao roomOwnerDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Conversation loadConversation(Conversation conversation) {
		// check exist conversation
		boolean exist = conversationDao.existsConversation(conversation.getParticipantA().getIdUser(),
				conversation.getParticipantB().getIdUser());
		if (exist) {
			conversation = conversationDao.findByParticipants(conversation.getParticipantA().getIdUser(),
					conversation.getParticipantB().getIdUser());
			Map params = new HashMap<String, Object>();
			params.put("idconversation", conversation.getIdConversation());
			List<Message> messages = messageDao.findByParam(params);
			conversation.setMessages(messages);
		} else {
			conversation = conversationDao.save(conversation);
		}
		return conversation;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Conversation sendMesage(Conversation conversation, Message message) {
		message.setConversation(conversation);
		message = messageDao.save(message);
		List<Message> lstMessage = conversation.getMessages();
		lstMessage.add(message);
		conversation.setMessages(lstMessage);
		return conversation;

	}
	
	public List<Conversation> findByUser(User user){
		List<Conversation> lstConversation = conversationDao.findByUser(user);
		//find the latest Message
		for (Conversation conversation : lstConversation) {
			List<Message> messages = messageDao.latestMessage(conversation);			
			conversation.setMessages(messages);
		}
		return lstConversation;
	}
	
	public User getUesrInfo(User user) {
		user = userDao.findByid2(user.getIdUser());
		if (user.getType() == 1) {
			Map params = new HashMap<String, Object>();
			params.put("iduser", user.getIdUser());
			List<RoomOwner> lstRoomOwner =  roomOwnerDao.findByParam(params);
			RoomOwner roomOwner = lstRoomOwner.get(0);
			params.clear();
			params.put("status", 1);
			params.put("type", 1);	//avatar type
			params.put("iddata", roomOwner.getIdRoomOwner());
			List<Image> lstAvatar = imageDao.findByParam(params);
			user.setAvatar(lstAvatar.get(0));
		}else if (user.getType() == 2) {
			Tenant tenant = new Tenant();
			Map params = new HashMap<String, Object>();
			params.put("iduser", user.getIdUser());
			List<Tenant> lstTenant = tenantDao.findByParam(params);
			tenant = lstTenant.get(0);
			params.clear();
			params.put("status", 1);
			params.put("type", 1);	//avatar type
			params.put("iddata", tenant.getIdTenant());		
			List<Image> lstAvatar = imageDao.findByParam(params);
			user.setAvatar(lstAvatar.get(0));
		}
		else {
			Image avatar = new Image();
			avatar.setFileName("default.jpg");
			user.setAvatar(avatar);
		}
		return user;
	}
}
