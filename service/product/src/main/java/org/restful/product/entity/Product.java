package org.restful.product.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Representa um produto no sistema.
 *
 * <p>Esta classe encapsula as informações detalhadas de um produto, incluindo nome, descrição,
 * quantidade disponível, preço e a categoria à qual o produto pertence.
 *
 * <p>A relação com a entidade {@code Category} é de muitos para um, indicando que vários
 * produtos podem estar associados a uma única categoria.
 *
 * <p>Utiliza as anotações do Lombok para gerar automaticamente construtores, getters, setters
 * e o padrão Builder.
 *
 * @see Category
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // Identificador único do produto no banco de dados

    private String name;  // Nome do produto
    private String description;  // Descrição detalhada do produto
    private double availableQuantity;  // Quantidade disponível em estoque
    private BigDecimal price;  // Preço do produto

    /**
     * Relação de muitos para um com a entidade {@code Category}.
     *
     * <p>Indica que vários produtos podem pertencer a uma única categoria.
     * A exclusão de uma categoria resultará na remoção de todos os produtos
     * associados a ela, devido ao {@code CascadeType.REMOVE} definido na entidade {@code Category}.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;  // Categoria à qual o produto pertence

    /**
     * Verifica a igualdade entre este produto e outro objeto.
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

        Product that = (Product) o;

        return getId() != null &&
               Objects.equals(this.getId(), that.getId());
    }

    /**
     * Calcula o hash code do produto.
     *
     * <p>O hash code é baseado no campo {@code id}, considerando possíveis proxies do Hibernate.
     *
     * @return Hash code do produto.
     */
    @Override
    public final int hashCode() {
        return this instanceof org.hibernate.proxy.HibernateProxy
                ? ((org.hibernate.proxy.HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

    /**
     * Retorna uma representação JSON do objeto {@code Product}.
     *
     * <p>A representação é formatada com indentação para facilitar a leitura.
     *
     * @return String no formato JSON representando o produto.
     */
    @Override
    public String toString() {
        return "\t{\n" +
               "\t\t\"id\": " + id + ",\n" +
               "\t\t\"name\": \"" + name + "\",\n" +
               "\t\t\"description\": \"" + description + "\",\n" +
               "\t\t\"availableQuantity\": " + availableQuantity + ",\n" +
               "\t\t\"price\": " + price + ",\n" +
               "\t\t\"category\": " + (category != null ? category.getId() : null) + "\n" +
               "\t}\n";
    }
}
