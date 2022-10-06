package com.orbital3d.server.fnet.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.orbital3d.server.fnet.ServerApplication;
import com.orbital3d.server.fnet.database.entity.User;
import com.orbital3d.server.fnet.database.repository.UserRepository;
import com.orbital3d.server.fnet.service.impl.PasswordServiceImpl;
import com.orbital3d.web.security.weblectricfence.util.HashUtil;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerApplication.class)
public class PasswordServiceTest {
	private PasswordService ps;

	@Autowired
	private UserRepository ur;

	@BeforeEach
	protected void prepareMocks() {
		ps = new PasswordServiceImpl();
	}

	@Test
	protected void testHashPassword() throws NoSuchAlgorithmException {
		byte[] b1 = new byte[] { 0x02, 0x03, 0x00 };
		byte[] b2 = new byte[] { 0x0A, 0x0B };
		assertNotEquals(ps.hash(b1, b2), b1);
		assertNotEquals(ps.hash(b1, b2), b2);
		assertNotEquals(ps.hash(b1, b2), ps.hash(b2, b1));
	}

	@Test
	protected void testBase64VerifyPassword() throws NoSuchAlgorithmException {
		byte[] salt = HashUtil.fillSecure(new byte[128]);
		String saltStr = Base64.getEncoder().encodeToString(salt);
		String pwd = "pwd1";
		byte[] pwdbytes = pwd.getBytes(StandardCharsets.UTF_8);
		byte[] hashed = HashUtil.secureHash(ArrayUtils.addAll(pwdbytes, salt));
		User u = mock(User.class);
		String ep = Base64.getEncoder().encodeToString(hashed);

		when(u.getPassword()).thenReturn(ep);
		when(u.getSalt()).thenReturn(saltStr);
		assertTrue(ps.verifyPassword(u, pwd));

		byte[] dec = Base64.getDecoder().decode(saltStr);
		assertArrayEquals(salt, dec);
	}

	@Test
	protected void testStoringBase64ToDatabaseAndRetrieving() throws NoSuchAlgorithmException {
		byte[] salt = HashUtil.fillSecure(new byte[128]);
		String saltStr = Base64.getEncoder().encodeToString(salt);
		String pwd = "pwd1";
		byte[] pwdbytes = pwd.getBytes(StandardCharsets.UTF_8);
		byte[] hashed = HashUtil.secureHash(ArrayUtils.addAll(pwdbytes, salt));
		String ep = Base64.getEncoder().encodeToString(hashed);
		User u = ur.save(User.of("user1", ep, saltStr));

		User user1 = ur.findById(u.getUserId()).get();
		assertTrue(ps.verifyPassword(user1, pwd));
	}

	@Test
	protected void testNoDuplicateSaltGenerated() {
		String pwd = "pwd";
		Pair<String, String> pair1 = ps.hash(pwd);
		Pair<String, String> pair2 = ps.hash(pwd);
		assertNotEquals(pair1.getRight(), pair2.getRight());
	}

	@Test
	protected void testExceptionsThrown() {
		String pwd = "pwd";
		User u = mock(User.class);
		assertThrows(IllegalArgumentException.class, () -> {
			ps.hash(null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.hash("");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.verifyPassword(null, pwd);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.verifyPassword(null, null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.verifyPassword(null, "");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.verifyPassword(u, null);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			ps.verifyPassword(u, "");
		});
	}
}
