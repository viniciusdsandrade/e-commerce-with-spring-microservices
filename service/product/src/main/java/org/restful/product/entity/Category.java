package org.restful.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.GenerationType.IDENTITY;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    private String name;
    private String description;

    @OneToMany(
            mappedBy = "category",
            cascade = REMOVE,
            orphanRemoval = true
    )
    private List<Product> products;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Class<?> oEffectiveClass = o instanceof org.hibernate.proxy.HibernateProxy
                ? ((org.hibernate.proxy.HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();

        Class<?> thisEffectiveClass = this instanceof org.hibernate.proxy.HibernateProxy
                ? ((org.hibernate.proxy.HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();

        if (thisEffectiveClass != oEffectiveClass) return false;

        Category that = (Category) o;

        return getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof org.hibernate.proxy.HibernateProxy
                ? ((org.hibernate.proxy.HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "\t{\n" +
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"name\": \"" + name + "\",\n" +
               "\t\t\"description\": \"" + description + "\"\n" +
               "\t}\n";
    }
}
