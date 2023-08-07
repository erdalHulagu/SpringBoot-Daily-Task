package com.java.dailyTasks.domain;





import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "activities")
public class Activities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	@NotNull
	private int namaziIlkVaktindeKilmak;
	
	@NotBlank
	@NotNull
	@Column
	private boolean teheccutNamazi;
	
	@NotBlank
	@NotNull
	@Column
	private boolean duhaNamazi;
	
	@NotBlank
	@NotNull
	@Column
	private int kuraniKerimOkumakSayfa;
	
	@NotBlank
	@NotNull
	@Column
	private boolean sabahAksamZikirleri;
	
	@NotBlank
	@NotNull
	@Column
	private boolean islamaHizmet;
	
	@NotBlank
	@NotNull
	@Column
	private boolean teblig;
	
	@NotBlank
	@NotNull
	@Column
	private int camiyeGitmek;
	
	@NotBlank
	@NotNull
	@Column
	private boolean akrabZiyareti;
	
	@NotBlank
	@NotNull
	@Column
	private boolean erkenYatipKalkmak;
	
	@NotBlank
	@NotNull
	@Column
	private boolean kitapOkumak;
	
	@NotBlank
	@NotNull
	@Column
	private boolean ziyaretlesmek;
	
	@NotBlank
	@NotNull
	@Column
	private boolean nafileOruc;
	
	@NotBlank
	@NotNull
	@Column
	private boolean haftalikSohbetlereKatilmak;
	
	@NotBlank
	@NotNull
	@Column
	private boolean sadakaVermek;
	
	@NotBlank
	@NotNull
	@Column
	private boolean ayinDergisiniOkumak;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id", referencedColumnName ="id", nullable = false )
	private User user;
	
}
