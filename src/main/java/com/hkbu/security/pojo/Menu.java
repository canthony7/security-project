package com.hkbu.security.pojo;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "sys_menu")
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "perm_key")
    private String permKey;

    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
    private List<Role> roles;

}
