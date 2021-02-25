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
        synchronized (this) {
            for (File resultFile : this.archivos) {
                while (suspender) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                List<Result> results = testReader.readResultsFromFile(resultFile);
                System.out.println("Archivoooooo");
                for (Result result : results) {
                    resultAnalyzer.addResult(result);
                }
                amountOfFilesProcessed.getAndIncrement();
            }
        }
    }

    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();		
    }
    synchronized void reanudarHilo() {
        suspender = false;
        notify();

	}

    synchronized void suspenderHilo() {
        suspender = true;
    }
}
