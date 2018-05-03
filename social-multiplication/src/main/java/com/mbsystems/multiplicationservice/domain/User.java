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
@Table(name = "USER")
public final class User  implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;
    private final String alias;

    public User() {
        this.alias = null;
    }
}
