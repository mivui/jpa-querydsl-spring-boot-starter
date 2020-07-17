# jpa-basic-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### use
1. Add dependency
     ```xml
        <dependency>
            <groupId>com.github.uinios</groupId>
            <artifactId>jpa-basic-spring-boot-starter</artifactId>
            <version>2.0.0.RELEASE</version>
        </dependency>
      ```
----------   
2. examples
* Entity class
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
> Repository
```java
public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
}
```
--------
> service
  * Provide single table CURD operation
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
> Test
```java
@SpringBootTest
class MainApplicationTests {

    @Autowired
    private ContactService contactService;

    @Test
    void contextLoads() {
       //Insert
       Contact contact = new Contact();
       contact.setName("test");
       contact.setNotes("test");
       contactService.save(contact);

       //Update
       Contact contact = new Contact();
       contact.setId(1);
       contact.setName("example");
       contact.setNotes("example");
       contactService.update(contact);

       //Delete
       contactService.deleteById(1);

       //BatchDeletion
       contactService.deleteByIds(new Integer[]{1,2});

       //Pagination
       contactService.page(1, 2);

       //...
    }
}
```

