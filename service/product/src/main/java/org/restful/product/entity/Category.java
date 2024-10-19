package org.restful.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Representa uma categoria de produtos no sistema.
 *
 * <p>Esta classe encapsula as informações detalhadas de uma categoria, incluindo nome
 * e descrição. Além disso, mantém uma lista de produtos que pertencem a esta categoria.
 *
 * <p>A relação com a entidade {@code Product} é de um para muitos, indicando que uma
 * única categoria pode ter vários produtos associados.
 *
 * <p>O uso de {@code REMOVE} na relação {@code OneToMany} garante que, ao
 * remover uma categoria, todos os produtos associados a ela também sejam removidos
 * automaticamente do banco de dados. Isso mantém a integridade referencial e evita
 * dados órfãos.
 *
 * <p>Utiliza as anotações do Lombok para gerar automaticamente construtores, getters,
 * setters e o padrão Builder.
 *
 * @see Product
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;  // Identificador único da categoria no banco de dados

    private String name;  // Nome da categoria
    private String description;  // Descrição detalhada da categoria

    /**
     * Relação de um para muitos com a entidade {@code Product}.
     *
     * <p>Indica que uma única categoria pode ter vários produtos associados a ela.
     *
     * <p>O {@code CascadeType.REMOVE} assegura que, ao remover uma categoria, todos
     * os produtos vinculados a ela sejam automaticamente removidos do banco de dados.
     * Isso serve para manter a integridade dos dados e evitar referências inválidas.
     */
    @OneToMany(
            mappedBy = "category",
            cascade = REMOVE,
            orphanRemoval = true
    )
    private List<Product> products;  // Lista de produtos associados à categoria

    /**
     * Verifica a igualdade entre esta categoria e outro objeto.
     *
     * <p>A igualdade é baseada no campo {@code id}, considerando possíveis proxies do Hibernate.
     *
     * @param o Objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
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

    /**
     * Calcula o hash code da categoria.
     *
     * <p>O hash code é baseado no campo {@code id}, considerando possíveis proxies do Hibernate.
     *
     * @return Hash code da categoria.
     */
    @Override
    public final int hashCode() {
        return this instanceof org.hibernate.proxy.HibernateProxy
                ? ((org.hibernate.proxy.HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    /**
     * Retorna uma representação JSON do objeto {@code Category}.
     *
     * <p>A representação é formatada com indentação para facilitar a leitura.
     *
     * @return String no formato JSON representando a categoria.
     */
    @Override
    public String toString() {
        return "\t{\n" +
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"name\": \"" + name + "\",\n" +
               "\t\t\"description\": \"" + description + "\"\n" +
               "\t}\n";
    }
}
