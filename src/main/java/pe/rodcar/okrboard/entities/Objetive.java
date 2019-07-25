package pe.rodcar.okrboard.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Table(name="objetives")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Objetive {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=true, length=500)
	private String title;
	
	@OneToMany(mappedBy="objetive", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<KeyResult> keyResults;
	
	public Objetive() {
		this.keyResults = new ArrayList<>();
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

	public List<KeyResult> getKeyResults() {
		return keyResults;
	}

	public void setKeyResults(List<KeyResult> keyResults) {
		this.keyResults = keyResults;
	}

}
