package br.senai.sp.jandira.odonto.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_dentista")
public class Dentista {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Size(min = 3, max = 100,  message = "O nome deve conter no mínimo 3 e no máximo 100 caracteres.")
	private String nome;

	@NotNull
	private String cro;

	private String email;

	/*
		@JoinTable - name: nome da tabela N:N
		@JoinColumn - name: nome do campo que ficará na tabela N:N (AS no SQL)
		@JoinColumn - referencedColumnName: nome do campo na tabela original 
	*/
	@ManyToMany
	@JoinTable(name = "tbl_dentista_especialidade",
		joinColumns = @JoinColumn(name = "id_dentista", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "id_especialidade", referencedColumnName = "id"))
	private List<Especialidade> especialidades;
	
	@NotNull
	private String telefone;

	private String urlImagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCro() {
		return cro;
	}

	public void setCro(String cro) {
		this.cro = cro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	@Override
	public String toString() {
		return "Dentista [cro=" + cro + ", email=" + email + ", especialidades=" + especialidades + ", id=" + id
				+ ", nome=" + nome + ", telefone=" + telefone + ", urlImagem=" + urlImagem + "]";
	}
}
