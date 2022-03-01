package io.github.ruben.persona.infrastructure.repository.jpa;

import io.github.ruben.persona.domain.Persona;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static io.github.ruben.persona.application.PersonaServiceImp.*;


public class PersonaRepositorioImpl {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Persona> getData(HashMap<String, Object> conditions)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Persona> query= cb.createQuery(Persona.class);
        Root<Persona> root = query.from(Persona.class);

        String order = "";
        order = (String) conditions.get("order");

        Integer pagina = 0;
        pagina = (Integer) conditions.get("pagina");

        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach((field,value) ->
        {
            switch (field)
            {
                case "usuario":
                    predicates.add(cb.like (root.get(field), "%"+(String)value+"%"));
                    break;
                case "name":
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                case "surname":
                    predicates.add(cb.like(root.get(field),"%"+(String)value+"%"));
                    break;
                case "created_date":
                    String dateCondition=(String) conditions.get("dateCondition");
                    switch (dateCondition)
                    {
                        case GREATER_THAN:
                            predicates.add(cb.greaterThan(root.<Date>get(field),(Date)value));
                            break;
                        case LESS_THAN:
                            predicates.add(cb.lessThan(root.<Date>get(field),(Date)value));
                            break;
                        case EQUAL:
                            predicates.add(cb.equal(root.<Date>get(field),(Date)value));
                            break;
                    }
                    break;
            }
        });
        if(order == null){
            query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        }else{
            if(order.equalsIgnoreCase(USUARIO) || order.equalsIgnoreCase(NAME)){
                query.select(root).where(predicates.toArray(new Predicate[predicates.size()])).orderBy(cb.asc(root.get(order)));
            }else{
                query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        if(pagina == null){
            pagina = 0;
        }else{
            pagina = pagina *10;
        }
        return entityManager.createQuery(query).setMaxResults(10).setFirstResult(pagina).getResultList();
    }
}
