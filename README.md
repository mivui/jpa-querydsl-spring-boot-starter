# jpa-service-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### Introduction
* jpa service package some crud optimization
### use
1. Add dependency
     ```xml
        <dependency>
            <groupId>com.github.uinio</groupId>
            <artifactId>jpa-service-spring-boot-starter</artifactId>
            <version>2.0.3.RELEASE</version>
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
  * Provide single table CURD operation.
  * Note: The provided updates and batch updates only support single tables. For complex updates, refer to the following example of EntityManager
```java
public class OrderManagement {

    @PersistenceContext
    private EntityManager em;

    //...

    public void updateOrder(Double oldAmount, Double newAmount) {
        CriteriaBuilder cb = this.em.getCriteriaBuilder();

        // create update
        CriteriaUpdate<Order> update = cb.
        createCriteriaUpdate(Order.class);

        // set the root class
        Root e = update.from(Order.class);

        // set update and where clause
        update.set("amount", newAmount);
        update.where(cb.greaterThanOrEqualTo(e.get("amount"), oldAmount));

        // perform update
        this.em.createQuery(update).executeUpdate();
    }

    //...
 
} 
```    
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

