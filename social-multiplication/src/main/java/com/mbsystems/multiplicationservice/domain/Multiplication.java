package com.mbsystems.multiplicationservice.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "MULTIPLICATION")
public final class Multiplication  implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;
    private final int factorA;
    private final int factorB;

    //Empty constructor for JSON (de)serialization
    public Multiplication() {
        this.factorA = 0;
        this.factorB = 0;
    }
}
