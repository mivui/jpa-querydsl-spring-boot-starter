# jpa-querydsl-spring-boot-starter

English | [中文](./ZH_CN.md)

* Jpa Quick Design
* Support spring boot 2.1.x and above (including 2.1.x)

### Introduction

* Jpa Service CRUD package, support querydsl out of the box.

### use

1. Add dependency
     ```xml
        <dependency>
            <groupId>com.github.uinio</groupId>
            <artifactId>jpa-querydsl-spring-boot-starter</artifactId>
            <version>2.4.1</version>
        </dependency>
      ```

----------   

2. examples

* Entity class

```java

@Table
@Entity
public class Contact implements Serializable {

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
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}
```

--------
> service

* Provide single table CURD operation.
* Note: The provided JpaService only supports single table operations, and complex operations use EntityManager or
  QueryDsl

```java
public interface ContactService extends JpaService<Contact, Integer> {
}
```

--------

```java

@Service
public class ContactServiceImpl extends JpaServiceImpl<Contact, Integer> implements UserService {

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
        contactService.deleteByIds(new Integer[]{1, 2});

        //Pagination
        contactService.page(1, 2);

        //...
    }
}
```

-------
> querydsl out of the box

* configuration

```xml

<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
        <plugin>
            <groupId>com.mysema.maven</groupId>
            <artifactId>apt-maven-plugin</artifactId>
            <version>1.1.3</version>
            <executions>
                <execution>
                    <goals>
                        <goal>process</goal>
                    </goals>
                    <configuration>
                        <outputDirectory>target/generated-sources/java</outputDirectory>
                        <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
                    </configuration>
                </execution>
            </executions>
            <dependencies>
                <dependency>
                    <groupId>com.querydsl</groupId>
                    <artifactId>querydsl-apt</artifactId>
                    <version>${querydsl.version}</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```

```java

@SpringBootTest
class MainApplicationTests {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Test
    void contextLoads() {
        QContact contact = QContact.contact;
        jpaQueryFactory.update(contact).set(contact.name, "test")
                .where(contact.id.eq(1)).execute();
    }

}
```

