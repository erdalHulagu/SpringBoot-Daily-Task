package com.java.dailyTasks.domain;



import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_chat")
@Entity
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String chatName;
		
	
	@ManyToMany   // hibernate defaultta LAZY
	@JoinTable( name="t_chat_role",
							 joinColumns = @JoinColumn(name="chat_id"),
							 inverseJoinColumns = @JoinColumn(name="image_id"))
	private  Set<Image> chatImage = new HashSet<>();
	

   
//    private Set<User> userAdmin;
	
	@Column(name = "is_group")
	private Boolean isGroup;
	
	@JoinColumn(name = "createdBy")
	@ManyToOne
	private User createdBy;
	
	@ManyToMany()
	private Set<User> users=new HashSet<>();
	
	@OneToMany(orphanRemoval = true)
	private List<Message>messages=new ArrayList<>();
		
	}
	
	
	
	

