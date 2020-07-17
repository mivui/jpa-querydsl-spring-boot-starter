# jpa-basic-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### 使用
1. 添加依赖
     ```xml
        <dependency>
            <groupId>com.github.uinios</groupId>
            <artifactId>jpa-basic-spring-boot-starter</artifactId>
            <version>2.0.0.RELEASE</version>
        </dependency>
      ```
----------   
2. 示例
* 实体类
```java
         @Entity
         @Getter
         @Setter
         @DynamicInsert
         @DynamicUpdate
         @Table(name = "sys_user")
         @JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
         public class User implements Serializable {
         
             @Id
             @GenericGenerator(name = "uuid2", strategy = "uuid2")
             @GeneratedValue(generator = "uuid2")
             @Column(columnDefinition = "char(36)")
             private String userId;
             private String loginName;
             private String password;
       }
       
```
---------
> 仓库
```java
//JpaSpecificationExecutor 提供复杂查询
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
}
```
--------
> 服务
  * 提供单表CURD操作
```java
public interface UserService extends JpaService<User, String> {
}
```
--------
```java
@Service
@Slf4j
public class UserServiceImpl extends JpaServiceImpl<User, String> implements UserService {
    
}
```
-------
> 测试
```java
@SpringBootTest
class MainApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
       User person = new User();
       person.setLoginName("test");
       person.setpPassword("test");

       userService.save(person);

       userService.page(1, 2);

       userService.findById("test");
       //...
    }
}
```

