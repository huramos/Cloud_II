package cl.duoc.dsy2207.microservicebff.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    private Long id;
    private String name;
    private String description;

    // Constructores
    public Role() {}
    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Role{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
}