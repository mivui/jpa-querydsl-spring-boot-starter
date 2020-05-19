# jpa-basic-spring-boot-starter
[中文](./README.md) | [English](./ENGLISH.md)
<br>
<br>
Jpa Quick Design
### 使用
1. addDependency
     ```xml
        <dependency>
            <groupId>com.github.uinios</groupId>
            <artifactId>jpa-basic-spring-boot-starter</artifactId>
            <version>1.0.2</version>
        </dependency>
      ```
2. examples
* entityClass
```java
         @Entity
         @Getter
         @Setter
         @DynamicInsert
         @DynamicUpdate
         @Table(name = "user")
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
* Repository
```java
//JpaSpecificationExecutor 提供复杂查询
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
}
```
* service
```java
public interface UserService extends BaseService<User, String> {
}
```
```java
@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<User, String> implements UserService {
    
}
```
* controller
Other languages​inherit BaseController rewriting
```java
@Slf4j
@RestController
@RequestMapping("user")
public class UserController extends BaseController<User, String> {
    private static final String content = "userManagement";

    protected UserController() {
        super(content);
    }
}
```
