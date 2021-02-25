/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.covidanalyzer.service.impl;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 *
 * @author svillamarin27
 */
@Service
public class CovidAggregateService implements ICovidAggregateService {
    List<Result> listaResultados = new ArrayList<>();
    public CovidAggregateService(){
    
        listaResultados.add(new Result(UUID.randomUUID(), "sebastian", "villamarin", "male", "sebastian@mail.com", "2-3-2000", "not_good", true, 0, ResultType.TRUE_POSITIVE));
        listaResultados.add(new Result(UUID.randomUUID(), "samuel", "villamarin", "male", "samuel@mail.com", "2-4-2001", "good", true, 0, ResultType.TRUE_NEGATIVE));
        listaResultados.add(new Result(UUID.randomUUID(), "maria", "villamarin", "female", "maria@mail.com", "2-3-2002", "good", true, 0, ResultType.FALSE_POSITIVE));
        listaResultados.add(new Result(UUID.randomUUID(), "alex", "villamarin", "male", "alex@mail.com", "2-3-2004", "not_good", true, 0, ResultType.TRUE_NEGATIVE));
        
        
    }
    @Override
    public void aggregateResult(Result result, ResultType type) {
        if(result.getResulTy()== type)listaResultados.add(result);
    }

    @Override
    public List<Result> getResult(ResultType type) {
        List<Result> getResultados = new ArrayList<>();
        for(int i=0;i<listaResultados.size();i++){
            if(listaResultados.get(i).getResulTy()==type) getResultados.add(listaResultados.get(i));
        }
        return getResultados;
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id, ResultType type) {
        
        for(int i = 0;i<listaResultados.size();i++){
            if(listaResultados.get(i).getId() == id){
                listaResultados.get(i).setResulTy(type);
                listaResultados.get(i).prueba();
            }
        
        }
        
    }
    
}
