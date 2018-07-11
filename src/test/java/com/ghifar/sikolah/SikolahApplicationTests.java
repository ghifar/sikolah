package com.ghifar.sikolah;

import com.ghifar.sikolah.com.ghifar.sikolah.entities.User;
import com.ghifar.sikolah.repository.RoleRepository;
import com.ghifar.sikolah.repository.UserRepository;
import com.ghifar.sikolah.services.IUserService;
import com.ghifar.sikolah.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class SikolahApplicationTests {


	private Logger logger= Logger.getLogger(getClass().getName());

	@Autowired
	UserRepository userRepository;
//
//	@Autowired
//	IUserService userService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void myTest() throws Exception {
		logger.info(">> Membuat akun");
//		User source =new User("test1", "test", "test");
// 		entityManager.persist(source);
//		entityManager.flush();


		List<User> source= new ArrayList<>();

//		source.add(new User("t","test","test3"));
		source.add(new User("test1","test1","test1"));
		source.add(new User("test2","test2","test2"));

		for (User user : source){
			entityManager.persist(user);
			entityManager.flush();
		}

		logger.info(">> Mencari username");
		List <User> users= userRepository.findAllByUsernameContains("test");

		logger.info((">> eksekusi assert"));
		assertThat(users).containsAll(source);

		// when
//		userService.tampilkanSemua();

//		User found = userRepository.findByUsername(user.getUsername());
//        assertThat(found.getUsername()).isEqualTo("test");
	}


//	@Test
//	public void contextLoads() {
//	}

//	@Test
//	public void useSliceToLoadContent() {
//
//		userRepository.deleteAll();
//
//		// int repository with some values that can be ordered
//		int totalNumberUsers = 11;
//		List<User> source = new ArrayList<User>(totalNumberUsers);
//
//		for (int i = 1; i <= totalNumberUsers; i++) {
//
//			User user = new User();
//			user.setUsername(user.getUsername() + "-" + String.format("%03d", i));
//			source.add(user);
//		}
//
////		userRepository.saveAll(source);
//		userRepository.save(source);
//
////		Slice<User> users = userRepository.findAll();
//
////		List<User> users= userRepository.findAll();
//
////		assertThat(users).containsAll(source.subList(5, 10));
//	}

//	@Test
//	public void liatSemuaUsers(){
//		userService.tampilkanSemua();
//
//	}

//	@Test
//	public void liatRole(){
//		logger.info(">> Eksekusi ROLE");
//
//		List<User> source= new ArrayList<>();
//
////		source.add(new User("t","test","test3"));
//		source.add(new User("test1","test1","test1"));
//		source.add(new User("test2","test2","test2"));
//
//		for (User user : source){
//			entityManager.persist(user);
//			entityManager.flush();
//		}
//
//		Page<User> users= userRepository.findAll(new PageRequest(0,5));
//		for (User user: source){
//
//
//
//		}
//		logger.info(">> Telah Tereksekusi");
//	}
}
