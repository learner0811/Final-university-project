package hn46.sa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import hn46.sa.entity.RoomOwner;
import hn46.sa.service.SearchService;
import hn46.sa.util.AppServer;

@Controller
public class ImageController {
	@Autowired
	private SearchService searchService;
	
	@RequestMapping(value = "/{type}/{fileName}.{ext}", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	public @ResponseBody byte[] getImage(@PathVariable String fileName, @PathVariable int type, @PathVariable String ext) throws IOException {	
		File file = null;
		fileName +=  "." + ext;
		String path = "";
		if (type == 1)
			path = AppServer.getProp("fileAvatarPath") + File.separator + fileName;			
		else if (type == 2)
			path = AppServer.getProp("fileRoomPath") + File.separator + fileName;
		else
			path = AppServer.getProp("fileServer") + File.separator + fileName;
		InputStream in = null;
		try {
			file = new File(path);
			in = new FileInputStream(file);
		} catch (Exception ex) {
			ex.printStackTrace();
			path = AppServer.getProp("fileAvatarPath") + File.separator + "image-not-found.png";
			file = new File(path);
			in = new FileInputStream(file);
		}
		byte[] image = IOUtils.toByteArray(in);
		in.close();
		return image;
	}		
}
