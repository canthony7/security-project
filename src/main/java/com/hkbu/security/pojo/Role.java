package com.hkbu.security.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sys_role")
@EntityListeners(AuditingEntityListener.class)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_key")
    private String roleKey;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToMany(targetEntity = Menu.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "sys_role_menu", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id", referencedColumnName = "id")})
    private List<Menu> menus;

}
