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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Table(name="objectives")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Objective {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="title", nullable=true, length=500)
	private String title;
	
	@OneToMany(mappedBy="objective", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	private List<KeyResult> keyResults;
	
	@JsonIgnore
	@NotNull(message="the objective need to be associated with an user")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable=false)
	private User user;
	
	public Objective() {
		this.keyResults = new ArrayList<>();
		this.user = new User();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
