package eci.arsw.covidanalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A Camel Application
 */
public class CovidAnalyzerTool {

    private ResultAnalyzer resultAnalyzer;
    private TestReader testReader;
    private static int amountOfFilesTotal;
    private static AtomicInteger amountOfFilesProcessed;
    private int com,fin,numeroHilos;

    public CovidAnalyzerTool() {
        resultAnalyzer = new ResultAnalyzer();
        testReader = new TestReader();
        amountOfFilesProcessed = new AtomicInteger();
        amountOfFilesProcessed.set(0);
    }

    public void processResultData() {
        amountOfFilesProcessed.set(0);
        List<File> resultFiles = getResultFileList();
        amountOfFilesTotal = resultFiles.size();
        for (File resultFile : resultFiles) {
            List<Result> results = testReader.readResultsFromFile(resultFile);
            for (Result result : results) {
                resultAnalyzer.addResult(result);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }

    private List<File> getResultFileList() {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }


    public Set<Result> getPositivePeople() {
        return resultAnalyzer.listOfPositivePeople();
    }

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String[] args) throws Exception {
        int numeroHilos = 5;
        
        CovidAnalyzerTool covidAnalyzerTool = new CovidAnalyzerTool();
        //Thread processingThread = new Thread(() -> covidAnalyzerTool.processResultData());
        //processingThread.start();
        
        List<File> archivosResultados = covidAnalyzerTool.getResultFileList();
        covidAnalyzerTool.amountOfFilesTotal = archivosResultados.size();
        
        int archivosHilo=amountOfFilesTotal /numeroHilos;
        int com = 0;
        int fin = archivosHilo;
        List<CovidThread> listaHilos = new ArrayList<>();
        
        for(int i=0;i<numeroHilos;i++) {
            if(i==numeroHilos-1) fin = amountOfFilesTotal;
            List<File> divArchivos = covidAnalyzerTool.divideFiles(archivosResultados, com,fin);
            listaHilos.add(new CovidThread(divArchivos, amountOfFilesProcessed));
            com=fin+1;
            fin+=archivosHilo+1;
        }




        for(int i =0;i<listaHilos.size();i++) {
            listaHilos.get(i).start();
            try {
                listaHilos.get(i).join();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        while (true) {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            if (line.contains("exit"))
                break;
            
            Set<Result> positivePeople = covidAnalyzerTool.covidResults(listaHilos);
            
            String message = "Processed %d out of %d files.\nFound %d positive people:\n%s";
            //Set<Result> positivePeople = covidAnalyzerTool.getPositivePeople();
            String affectedPeople = positivePeople.stream().map(Result::toString).reduce("", (s1, s2) -> s1 + "\n" + s2);
            message = String.format(message, covidAnalyzerTool.amountOfFilesProcessed.get(), covidAnalyzerTool.amountOfFilesTotal, positivePeople.size(), affectedPeople);
            System.out.println(message);
        }
    }

    public Set<Result> covidResults(List<CovidThread> listaHilos){
    	Set<Result> positivePeople = new HashSet<>();
    	for(int i =0;i<listaHilos.size();i++) positivePeople.addAll(listaHilos.get(i).getPositivePeople());
    	return positivePeople;
    }
    public AtomicInteger getAmountOfFilesProcessed() {
        return amountOfFilesProcessed;
	}
    private List<File> divideFiles(List<File> divArchivos, int min, int max) {
        if(max == 23){
            return divArchivos.subList(min, max);
        }else{
            return divArchivos.subList(min, max+1);
        }

        
    }
	

}

