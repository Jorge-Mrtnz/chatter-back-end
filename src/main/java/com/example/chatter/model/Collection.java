package com.example.chatter.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    @OneToMany(mappedBy = "collection")
    private List<Post> posts;

    @ManyToOne
    @JoinColumn(name = "owner")
    private User owner;
}
