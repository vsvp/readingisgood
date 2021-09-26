package com.example.readingisgood.service;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class BaseService {

    @PersistenceContext
    protected EntityManager em;

}
