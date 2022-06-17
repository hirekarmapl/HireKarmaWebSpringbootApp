package com.hirekarma.model;
import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="hire_karma_qandatable")
public class QuestionANdanswer implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name="Id")
	private Long id;
	@Column(name="question")
	private String question;
	@Column(name="type")
	private String type;
    @Column(name="codingdescribtion")
    private String codingDescription; 
   
    @ManyToOne
    private Corporate corporate;
    
    @ManyToOne
    private University university;
    @Column(name="UID")
    private String uID;
    @ManyToMany(mappedBy = "questionANdanswers")

	@JsonIgnore
    public List<OnlineAssessment> onlineAssessments;



	private String correctOption;
    
    @Column(name="status")
    private String status;
    
	@OneToMany(targetEntity=MCQAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="uID")
	private List<MCQAnswer> mcqAnswer;
	
	@OneToOne(targetEntity=LongAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private LongAnswer longAnswer;
	
	@OneToOne(targetEntity=InputAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="id")
	private InputAnswer inputAnswer;
	
	@OneToMany(targetEntity=CodingAnswer.class,cascade=CascadeType.ALL)
	@JoinColumn(name="uID")
	private List<CodingAnswer> codingAnswer;
	
	@OneToMany(mappedBy = "questionANdanswer")
	@JsonIgnore
	private List<StudentOnlineAssessmentAnswer> studentOnlineAssessmentAnswers;
	
  
}
