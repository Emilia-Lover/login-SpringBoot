package com.example.login_demo.domain;

import jakarta.persistence.*;

@Entity                      // 이 클래스는 DB 테이블과 연결된다
@Table(name = "users")       // 테이블 이름은 users (user는 예약어라 피함)
public class User {

    @Id                                                   // 기본키(PK)
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, unique = true, length = 50) // NOT NULL, 중복 불가
    private String username;

    @Column(nullable = false, length = 100)               // BCrypt 해시는 60자라 넉넉히
    private String password;

    @Column(nullable = false, length = 20)
    private String role;

    protected User() {}       // JPA가 객체를 만들 때 필요한 기본 생성자

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}
