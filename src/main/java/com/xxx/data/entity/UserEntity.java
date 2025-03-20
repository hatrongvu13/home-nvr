package com.xxx.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account")
public class UserEntity extends BaseEntity {
    @Column(unique = true, nullable = false, name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(unique = true, nullable = false, name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "full_name")
    private String fullName;
}
