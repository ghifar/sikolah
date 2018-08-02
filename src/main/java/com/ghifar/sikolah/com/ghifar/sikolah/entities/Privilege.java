package com.ghifar.sikolah.com.ghifar.sikolah.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges",  fetch = FetchType.LAZY)
    private Collection<Role> roles;

    public Privilege(){
        super();
    }

    public Privilege(final String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
//        final StringBuilder builder = new StringBuilder();
//        builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
        return name;
    }
}
