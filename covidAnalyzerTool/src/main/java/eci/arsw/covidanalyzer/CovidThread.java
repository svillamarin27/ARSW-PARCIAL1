/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eci.arsw.covidanalyzer;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author svillamarin27
 */
public class CovidThread extends Thread{
    
    private List<File> archivos;
    private TestReader testReader;
    private ResultAnalyzer resultAnalyzer;
    private boolean suspender;
    private AtomicInteger amountOfFilesProcessed;
    
    public CovidThread(List<File> divArchivos, AtomicInteger atomic) {
        
        this.archivos = divArchivos;
	amountOfFilesProcessed = atomic;
        testReader = new TestReader();
        resultAnalyzer = new ResultAnalyzer();
        suspender = false;
        

	}
    
    @Override
    public void run() {
        while (suspender) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (File resultFile : this.archivos) {
            System.out.println("Archivoooooo");
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.getAndIncrement();
        }
    }

    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();		
    }
    public void reanudarHilo() {
        suspender = false;
        notify();

	}

    public void suspenderHilo() {
        suspender = true;
    }
}
