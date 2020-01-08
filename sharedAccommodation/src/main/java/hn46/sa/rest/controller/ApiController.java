package hn46.sa.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hn46.sa.dto.MessageResponse;
import hn46.sa.entity.RoomOwner;
import hn46.sa.service.SearchService;

@RestController
public class ApiController {
	@Autowired
	private SearchService searchService;

	@RequestMapping(value = "/room_owners", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<MessageResponse> listRoomOwner() {
		List<RoomOwner> lstResult = searchService.loadAllData();
		MessageResponse response = new MessageResponse();
		response.setPayload(lstResult);
		response.setStatusCode(HttpStatus.OK.name());
		return new ResponseEntity<MessageResponse>(response, HttpStatus.OK);
	}
}
