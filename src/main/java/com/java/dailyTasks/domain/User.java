package com.java.dailyTasks.domain;


import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user")

public class User {

	
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@NotNull
@Column(name="isim", nullable = false, length = 100)
private String firstName;

@NotNull
@Column(name="soyisim",nullable = false, length = 100)
private String lastName;

@NotNull
@Column(name="ulke",nullable = false, length = 50)
private String state;

@NotNull
@Column(name="sehir",nullable = false, length = 50)
private String city;

@Email(message = "Please provide valid email")
@Size(min = 10, max = 80)
@Column(length = 80, nullable = false, unique=true)
private String email;
//
//@Column
//private Long image;

//@Pattern(regexp = "\\\\d{3}-\\\\d{3}-\\\\d{4}",	// 999-999-9999
//message = "Please provide valid phone number" ) 
//@Column(nullable = false)
//private String phone;



@Column(name = "create_at", updatable = false, nullable = true)
private LocalDateTime createAt;


@OneToMany(orphanRemoval = true)
@JoinColumn(name="userId")
private Set<Image> image;


}
