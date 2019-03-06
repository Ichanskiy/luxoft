package com.ichanskyi.luxoft.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ichanskyi.luxoft.entity.enums.Position;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "employee")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Employee extends BaseObject {

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "married")
    private boolean married;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "position")
    @Enumerated(EnumType.STRING)
    private Position position;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "department_id")
    private Department department;


}
