package com.salesianostriana.campaing.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "DATOS_MAESTROS")
public class DatosMaestros {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "TIPO")
	private String tipo;

	@OneToMany
	@JsonIgnore
	private Set<Aportacion> aportaciones = new HashSet<Aportacion>();
	
	@ManyToOne
	@JsonIgnore
	private Campanya campanya;

	public DatosMaestros() {
	}

	public DatosMaestros(String tipo) {
		this.tipo = tipo;
	}

	public void addCampanya(Campanya c) {
		this.setCampanya(c);
		c.getDatosMaestros().add(this);
	}

	public void removeCampanya(Campanya c) {
		c.getDatosMaestros().remove(this);
		this.setCampanya(null);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatosMaestros other = (DatosMaestros) obj;
		if (id != other.id)
			return false;
		if (tipo == null) {
			if (other.tipo != null)
				return false;
		} else if (!tipo.equals(other.tipo))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

}
