package com.orbital3d.server.fnet.controller.fnet;

import java.io.IOException;
import java.nio.file.Path;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.database.entity.VFSEntity;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SessionService;
import com.orbital3d.server.fnet.service.SettingsService;
import com.orbital3d.server.fnet.service.VirtualFileService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Controller for file uload.
 * 
 * @author msiren
 *
 */
@RestController
@RequestMapping("/fnet")
public class Upload {
	private static final Logger LOG = LogManager.getLogger(Upload.class);

	@Autowired
	private VirtualFileService virtualFileService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private SettingsService settingsService;

	@Autowired
	private ItemService itemService;

	@Async
	@PutMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@Transactional
	@RequiresPermission(FnetPermissions.File.UPLOAD)
	public Item handleUpload(@RequestParam("file") MultipartFile file, @RequestParam("parentId") Long parentId,
			HttpServletResponse response) throws IOException {
		LOG.info("{} uploaded file {}", sessionService.getCurrentUser().getUserName(), file.getOriginalFilename());
		try {
			Item uploadedItem = Item.of(parentId, file.getOriginalFilename(), resolveItemType(file),
					sessionService.getCurrentGroup().getGroupId(), sessionService.getCurrentUser().getUserId());
			uploadedItem = itemService.save(uploadedItem);
			VFSEntity virtualFile = virtualFileService.generate(file.getOriginalFilename(), uploadedItem.getItemId());
			LOG.debug("about to move file");
			// Move uploaded file to VFS
			file.transferTo(Path.of(settingsService.storagePath() + virtualFile.getVirtualName()));
			LOG.debug("Done moving file");
			virtualFileService.save(virtualFile);
			response.setStatus(HttpStatus.OK_200);
			return uploadedItem;
		} catch (Throwable e) {
			e.printStackTrace();
			LOG.warn(e);
		}
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR_500);
		return null;
	}

	private ItemType resolveItemType(MultipartFile file) {
		return ItemType.FILE;
	}

}
