# jpa-service-spring-boot-starter
[中文](./ZH_CN.md) | [English](./README.md)
* Jpa Quick Design
### 简介
* Jpa Service 封装,部份CRUD进行优化.
### 使用
1. 添加依赖
     ```xml
        <dependency>
            <groupId>com.github.uinio</groupId>
            <artifactId>jpa-service-spring-boot-starter</artifactId>
            <version>2.0.2.RELEASE</version>
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
  * 注意:提供的更新和批量更新只支持单表,复杂更新参考EntityManager以下简单示例
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

