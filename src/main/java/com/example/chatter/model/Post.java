package com.example.chatter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String file;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @ManyToOne
    @JoinColumn(name = "collection")
    private Collection collection;

    @ManyToOne
    @JoinColumn(name = "creator")
    private User creator;
}
