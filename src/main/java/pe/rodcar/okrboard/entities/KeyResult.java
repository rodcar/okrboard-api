package pe.rodcar.okrboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
//import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Table(name="keyresults")
public class KeyResult {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=true, length=500)
	private String title;
	
	@Column(name="progress", nullable=false)
	private Double progress;
	
	@JsonIgnore
	//@NotNull(message="The Key Result need to be associated with an objective")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectives_id", nullable=false)
	private Objective objective;
	
	public KeyResult() {
		this.progress = 0.0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getProgress() {
		return progress;
	}

	public void setProgress(Double progress) {
		this.progress = progress;
	}

	public Objective getObjective() {
		return objective;
	}

	public void setObjective(Objective objetive) {
		this.objective = objetive;
	}
	
}
