package com.kevinjanvier.multitenant.entity;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "tbl_users")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = -4551953276601557391L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name="pk_sequence",sequenceName="hibernate_sequence", allocationSize=1)
//    @SequenceGenerator(name = "users_generator", sequenceName = "hibernate_sequence", allocationSize = 1)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String tenantId;
}
