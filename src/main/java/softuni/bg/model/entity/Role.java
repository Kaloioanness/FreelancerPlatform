package softuni.bg.model.entity;

import jakarta.persistence.*;
import softuni.bg.model.BaseEntity;
import softuni.bg.model.enums.RoleName;

import java.util.Set;

@Entity
@Table
public class Role extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName name;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;
    public Role(RoleName name) {
        super();
        this.name = name;
    }
    public Role() {}
    //getters and setters

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }
}
