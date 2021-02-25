package eci.arsw.covidanalyzer;

import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import eci.arsw.covidanalyzer.service.impl.CovidAggregateService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CovidAggregateController {
    @Autowired
    CovidAggregateService covidAggregateService;

    

    @RequestMapping(value = "/covid/result/add/true-positive", method = RequestMethod.POST)
    public ResponseEntity addTruePositiveResult(Result result) {
        covidAggregateService.aggregateResult(result, ResultType.TRUE_POSITIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/covid/result/add/true-negative", method = RequestMethod.POST)
    public ResponseEntity addTruenegativeResult(Result result) {
        covidAggregateService.aggregateResult(result, ResultType.TRUE_NEGATIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/covid/result/add/false-positive", method = RequestMethod.POST)
    public ResponseEntity addFalsePositiveResult(Result result) {
        covidAggregateService.aggregateResult(result, ResultType.FALSE_POSITIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/covid/result/add/false-negative", method = RequestMethod.POST)
    public ResponseEntity addFalseNegativeResult(Result result) {
        covidAggregateService.aggregateResult(result, ResultType.FALSE_NEGATIVE);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/covid/result/true-positive", method = RequestMethod.GET)
    public ResponseEntity getTruePositiveResult() {
        List<Result> covidResultado = covidAggregateService.getResult(ResultType.TRUE_POSITIVE);
        return new ResponseEntity<>(covidResultado, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/covid/result/true-negative", method = RequestMethod.GET)
    public ResponseEntity getTrueNegativeResult() {
        List<Result> covidResultado = covidAggregateService.getResult(ResultType.TRUE_NEGATIVE);
        return new ResponseEntity<>(covidResultado, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/covid/result/false-positive", method = RequestMethod.GET)
    public ResponseEntity getFalsePositiveResult() {
        List<Result> covidResultado = covidAggregateService.getResult(ResultType.FALSE_POSITIVE);
        return new ResponseEntity<>(covidResultado, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/covid/result/false-negative", method = RequestMethod.GET)
    public ResponseEntity getFalseNegativeResult() {
        List<Result> covidResultado = covidAggregateService.getResult(ResultType.FALSE_NEGATIVE);
        return new ResponseEntity<>(covidResultado, HttpStatus.ACCEPTED);
    }
    
    @RequestMapping(value = "/covid/result/persona/{id}", method = RequestMethod.PUT)
    public ResponseEntity savePersonaWithMultipleTests(@PathVariable("id") UUID id, @RequestBody ResultType type) {
        covidAggregateService.upsertPersonWithMultipleTests(id, type);
        return new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED);
    }
    
}