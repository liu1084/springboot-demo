//package com.proaimltd.web.video.controller;
//
//import org.springframework.context.annotation.Lazy;
//import org.springframework.util.Assert;
//
//import java.util.Optional;
//
///**
// * @project: springboot-demo
// * @packageName: com.proaimltd.web.video.controller
// * @author: fengkun.zhao
// * @date: 2019/9/12 13:53
// * @description：TODO
// */
//public class OptionalReturnValue {
//	//@Autowaire
//	//private IUserService userService;
//
//	//@Autowarie
//	//private IUserRepository userRepository;
//
//	//官方推荐
//	private IUserRepository userRepository;
//	private IUserService userService;
//
//	public OptionalReturnValue(@Lazy IUserService userService, IUserRepository userRepository) {
//		this.userRepository =userRepository;
//		this.userService = userService;
//	}
//
////	@Autowarie
////	private User user1;
//
//	//Controller层
//	public void testFindUserById() {
//		int id = 1;
//		IUserService userService = new UserServiceImpl();
//		Optional<User> result = userService.findUserById(id);
//		result.ifPresent(user -> Assert.assertNotEquals(null, user));
//		result.ifPresent(user-> Assert.assertEquals(true, user.getId() == 2));
//		result.ifPresent(user -> Assert.assertEquals(true, user.getName().equals("Liu")));
//
//		///return result.orElse(null);
//	}
//
//	//Service层---接口，定义可能会出现的处理业务逻辑的方法
//	interface IUserService {
//		Optional<User> findUserById(int id);
//	}
//	//Service层---接口实现类，补充功能的具体实现方法。
//	class UserServiceImpl implements IUserService{
//		private IUserRepository userRepository;
//		@Override
//		public Optional<User> findUserById(int id) {
//			IUserRepository userRepository = new UserRepository();
//			return userRepository.findUserById(1).map((user) -> {
//				user.setId(2);
//				user.setName("Liu");
//				return user;
//			} );
//		}
//	}
//
//	//Dao层---接口
//	interface IUserRepository {
//		Optional<Object> findUserById(int id);
//	}
//	//Dao层---实现类，进行数据库的相关操作具体实现
//	class UserRepository implements IUserRepository {
//		private IUserService userService;
//			@Override
//			public Optional<Object> findUserById(int id) {
//				User user1 = new User(1, "jim");
//				return Optional.ofNullable(user1);
//		}
//	}
//	//实体类，数据库的一张表
//	class User {
//		private int id;
//		private String name;
//
//		public User(int id, String name) {
//			this.id = id;
//			this.name = name;
//
//		}
//
//		public int getId() {
//			return id;
//		}
//
//		public void setId(int id) {
//			this.id = id;
//		}
//
//		public String getName() {
//			return name;
//		}
//
//		public void setName(String name) {
//			this.name = name;
//		}
//	}
//
//
//}
