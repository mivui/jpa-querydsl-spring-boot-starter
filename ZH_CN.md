# jpa-basic-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### 使用
1. 添加依赖
     ```xml
        <dependency>
            <groupId>com.github.uinios</groupId>
            <artifactId>jpa-basic-spring-boot-starter</artifactId>
            <version>1.0.5</version>
        </dependency>
      ```
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
* 仓库
```java
//JpaSpecificationExecutor 提供复杂查询
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
}
```
* 服务
```java
public interface UserService extends BaseService<User, String> {
}
```
```java
@Service
@Slf4j
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    
}
```
* 控制器
```java
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController<User, String> {
    private static final String content = "用户管理";

    protected UserController() {
        super(content);
    }
}
```

