package com.orbital3d.server.fnet.controller;

import java.util.Date;
import java.util.Random;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.orbital3d.server.fnet.database.entity.Comment;
import com.orbital3d.server.fnet.database.entity.Group;
import com.orbital3d.server.fnet.database.entity.Item;
import com.orbital3d.server.fnet.database.entity.Item.ItemType;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.service.CommentService;
import com.orbital3d.server.fnet.service.GroupService;
import com.orbital3d.server.fnet.service.ItemService;
import com.orbital3d.server.fnet.service.UserService;

@Controller
public class TestController {
	private static final Logger LOG = LogManager.getLogger(TestController.class);

	@Autowired
	private GroupService gs;

	@Autowired
	private ItemService is;

	@Autowired
	private UserService us;

	@Autowired
	private CommentService cs;

	@GetMapping("/test")
	protected String testThis() {
		return "root";
	}

	@GetMapping("/app/index")
	protected String needAuth() {
		return "index";
	}

	@GetMapping("/ise")
	protected void ise() {
		throw new NullPointerException();
	}

	@GetMapping("/roots")
	protected void createRoots() {
		gs.findAll().forEach(group -> is.createRoot(group, us.findUserByName("administrator").get().getUserId()));
	}

	@GetMapping("/puppu")
	@Transactional(TxType.REQUIRED)
	protected void generatePuppu() {
		String username = null;
		String groupname = null;
		try {
			if (StringUtils.isBlank(username)) {
				username = "administrator";
			}
			if (StringUtils.isBlank(groupname)) {
				groupname = "administrator";
			}
			User u = us.findUserByName(username).get();
			Group ag = gs.findByName(groupname).get();
			Item root = is.findRoot(ag);
			for (int i = 0; i < 15; i++) {
				Item p = is.save(Item.of(null, root.getItemId(), RandomStringUtils.randomAlphabetic(8), ItemType.FOLDER,
						new Date(), ag.getGroupId(), u.getUserId()));
				generateFiles(p, u, ag);
				generateComments(p, u, ag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateFiles(Item parent, User u, Group g) {
		int numFiles = (int) ((new Random()).nextFloat() * 10.0f);
		for (int i = 0; i < numFiles; i++) {
			Item generated = is.save(Item.of(null, parent.getItemId(), RandomStringUtils.randomAlphabetic(12),
					ItemType.FILE, new Date(), g.getGroupId(), u.getUserId()));
		}
	}

	private void generateComments(Item parent, User u, Group g) {
		int nc = (int) ((new Random()).nextFloat() * 10.0f);
		for (int i = 0; i < nc; i++) {
			cs.save(Comment.of(null, parent.getItemId(), g.getGroupId(), u.getUserId(),
					RandomStringUtils.randomAlphanumeric((int) ((new Random()).nextFloat() * 128.0f)), new Date()));
		}
	}
}
