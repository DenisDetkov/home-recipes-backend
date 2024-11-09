package org.terenbro.database.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Type type;

    public enum Type {
        PACKET("пакет"), TEA_SPOON("ч. ложка"), TABLE_SPOON("стол. ложка"), PIECE("шт."), MLITER("мілілітр"), LITER("літр"), GRAM("грам"), KILOGRAM("кілограм");

        public final String text;

        Type(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return this.text;
        }
    }
}
