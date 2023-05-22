package com.sm.rsm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sm.rsm.model.Table;

public interface TableDao extends JpaRepository<Table, Integer> {

}
