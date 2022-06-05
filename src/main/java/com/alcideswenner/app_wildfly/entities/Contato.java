package com.alcideswenner.app_wildfly.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contato {

    @Id
    @GeneratedValue
    private Long contactId;

    private String firstName;
    private String lastName;
    private String companyName;

}