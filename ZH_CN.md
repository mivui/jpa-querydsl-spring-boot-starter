# jpa-service-spring-boot-starter
中文 | [English](./README.md)
* Jpa Quick Design
### 简介
* Jpa Service CRUD 封装,支持querydsl开箱即用。
### 使用
1. 添加依赖
     ```xml
        <dependency>
            <groupId>com.github.uinio</groupId>
            <artifactId>jpa-service-spring-boot-starter</artifactId>
            <version>2.3.1.RELEASE</version>
        </dependency>
      ```
----------   
2. 示例
* 实体类
```java
@Table
@Entity
public class Contact  implements Serializable {

   @Id
   private Integer id;
  
   private Name name;
  
   private String notes;
   
   //getter setter ...      
}
```
---------
> 仓库
```java
public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
}
```
--------
> 服务
  * 提供单表CURD操作
  * 注意:提供的JpaService仅支持单表操作,复杂操作使用EntityManager或者QueryDsl
```java
public interface ContactService extends JpaService<Contact, Integer> {
}
```
--------
```java
@Service
public class  ContactServiceImpl extends JpaServiceImpl<Contact, Integer> implements UserService {
    
}
```
-------
> 测试
```java
@SpringBootTest
class MainApplicationTests {

    @Autowired
    private ContactService contactService;

    @Test
    void contextLoads() {
       //新增
       Contact contact = new Contact();
       contact.setName("test");
       contact.setNotes("test");
       contactService.save(contact);

       //更新
       Contact contact = new Contact();
       contact.setId(1);
       contact.setName("example");
       contact.setNotes("example");
       contactService.update(contact);

       //删除
       contactService.deleteById(1);

       //批量删除
       contactService.deleteByIds(new Integer[]{1,2});

       //分页
       contactService.page(1, 2);

       //...
    }
}
```
-------
> querydsl 开箱即用
```java
@SpringBootTest
class MainApplicationTests {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    void contextLoads() {
       QContact contact = QContact.contact;
       jpaQueryFactory.update(contact).set(contact.name,"test")
        .where(contact.id.eq(1)).execute();
    }

}
```

