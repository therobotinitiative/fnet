package com.orbital3d.server.fnet.controller.fnet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.database.entity.VFSEntity;
import com.orbital3d.server.fnet.security.FnetPermissions;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.SettingsService;
import com.orbital3d.server.fnet.service.VirtualFileService;
import com.orbital3d.web.security.weblectricfence.annotation.RequiresPermission;

/**
 * Controller for downloading a file.
 * 
 * @author msiren
 *
 */
@Controller
@RequestMapping("/fnet")
public class Download {
	private static final Logger LOG = LogManager.getLogger(Download.class);

	@Autowired
	private ItemService itemService;

	@Autowired
	private VirtualFileService virtualFileService;

	@Autowired
	private SettingsService setttingsService;

	/**
	 * Download requested file. Item is resolved based on the given id and based on
	 * the item the virtual file is resolved. Asynchronous operation.
	 * 
	 * @param itemId   Item id which is used to locate the actual file
	 * @param response {@link HttpServletResponse} response object
	 * @return {@link StreamingResponseBody} as the file being downloaded
	 * @throws IOException
	 */
	@Async
	@GetMapping(path = "/download/{id}")
	@RequiresPermission(FnetPermissions.File.DOWNLOAD)
	public StreamingResponseBody download(@PathVariable(name = "id") Long itemId, HttpServletResponse response)
			throws IOException {
		Optional<Item> item = itemService.getById(itemId);
		if (item.isPresent()) {
			LOG.info("Downloadin {}", item);
			if (!item.get().getItemType().equals(ItemType.FOLDER)) {
				VFSEntity virtualFile = virtualFileService.getVrtualFile(item.get().getItemId());
				File file = new File(setttingsService.storagePath() + virtualFile.getVirtualName());
				LOG.info("Trying to locate: {}", file.getAbsolutePath());
				if (file.exists()) {
					LOG.info("File found: {}", file.getAbsolutePath());
					// Send file
					response.setContentType(Files.probeContentType(file.toPath()));
					response.setHeader("Content-Disposition",
							"attachment; filename=\"" + virtualFile.getOriginalName() + "\"");
					InputStream inputStream = new FileInputStream(file);
					return outputStream -> {
						int nRead;
						byte[] data = new byte[1024];
						while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
							outputStream.write(data, 0, nRead);
						}
						inputStream.close();
					};
				}
			}
		}
		response.setStatus(HttpStatus.NO_CONTENT_204);
		return null;
	}

}
