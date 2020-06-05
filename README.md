
# jpa-basic-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### use
1. addDependency
     ```xml
        <dependency>
            <groupId>com.github.uinios</groupId>
            <artifactId>jpa-basic-spring-boot-starter</artifactId>
            <version>1.3.5</version>
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
//JpaSpecificationExecutor provideComplexQueries
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    
}
```
* service
  * provide single table curd operation
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
  * use please see the source code baseController,The default restful provided.
  * Provide Chinese and English by default, other languages ​​can be inherited and reconstructed.
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