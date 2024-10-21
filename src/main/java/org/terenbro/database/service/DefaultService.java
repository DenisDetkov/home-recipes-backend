package org.terenbro.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public abstract class DefaultService<E, T extends JpaRepository> {
    @Autowired
    T jpaRepository;

    public List<E> findAll() {
        return jpaRepository.findAll();
    }

    public void save(E e){
        jpaRepository.save(e);
    }

    public void saveAll(List<E> e){
        jpaRepository.saveAll(e);
    }

    public void delete(E e){
        jpaRepository.delete(e);
    }

    public void deleteAll(List<E> e){
        jpaRepository.deleteAll(e);
    }

    public void deleteById(long id){
        jpaRepository.deleteById(id);
    }

    public Optional<E> getById(Long id) {
       return jpaRepository.findById(id);
    }

    public int count(){
        return (int) jpaRepository.count();
    }
}
