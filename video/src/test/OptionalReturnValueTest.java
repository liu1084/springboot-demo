package com.proaimltd.web.video.controller;


import java.util.Optional;

/**
 * @project: java-core
 * @packageName: com.jim.java.core.optional
 * @author: Administrator
 * @date: 2019/9/11 22:36
 * @description：TODO
 */
public class OptionalReturnValueTest {

	//@Autowaire
	//private IUserService userService;

	//@Autowarie
	//private IUserRepository userRepository;

	//官方推荐
//	private IUserRepository userRepository;
//	private IUserService userService;
//
//	public OptionalReturnValueTest(@Lazy IUserService userService, IUserRepository userRepository) {
//		this.userRepository =userRepository;
//		this.userService = userService;
//	}

	//@Autowarie
	//private User user1;

	@Test
	public void FindUserById() {
		Long id = 1L;
		IUserService userService = new UserServiceImpl();
		Optional<User> result = userService.findUserById(id);
		result.ifPresent(user -> Assert.assertNotEquals(null, user));
		result.ifPresent(user-> Assert.assertEquals(true, user.getId() == 2L));
		result.ifPresent(user -> Assert.assertEquals(true, user.getName().equals("Liu")));

		///return result.orElse(null);

	}

	//Service接口
	interface IUserService {
		Optional<User> findUserById(Long id);
	}
	//Service实现类---接口中的功能具体实现
	class UserServiceImpl implements IUserService{
		private IUserRepository userRepository;
		@Override
		public Optional<User> findUserById(Long id) {
			IUserRepository userRepository = new UserRepository();
			return userRepository.findUserById(1L).map((user) -> {
				user.setId(2L);
				user.setName("Liu");
				return user;
			} );
		}
	}
	//DAO层接口
	interface IUserRepository {
		Optional<User> findUserById(Long id);
	}
	//DAO层接口实现类---数据库操作
	class UserRepository implements IUserRepository {
		private IUserService userService;
		@Override
		public Optional<User> findUserById(Long id) {
			User user1 = new User(1L, "jim");
			return Optional.ofNullable(user1);
		}
	}

}
