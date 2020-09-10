package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.exception.ApiRequestException;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonDao personDao;

    @Autowired
    public PersonService(@Qualifier("postgres") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person){
        isNameTaken(person);
       return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople(){
        return personDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id){
        return personDao.selectPersonById(id);
    }

    public void deletePerson (UUID id){
        personDao.deletePersonById(id);
    }

    public void updatePerson(UUID id, Person newPerson){
        isNameTaken(newPerson);
        personDao.updatePersonById(id, newPerson);
    }

    private void isNameTaken(Person newPerson) {
        if (personDao.isNameTaken(newPerson.getName())) {
            throw new ApiRequestException(newPerson.getName() + " is taken");
        }
    }
}
