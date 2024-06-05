package services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVService<T> {
    private static CSVService instance = null;

    private CSVService() {}

    public static synchronized CSVService getInstance() {
        if (instance == null) {
            instance = new CSVService();
        }
        return instance;
    }

    public List<T> readFromCSV(String filePath, RowMapper<T> rowMapper) {
        List<T> items = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                items.add(rowMapper.mapRow(nextLine));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void writeToCSV(String filePath, List<T> items, RowWriter<T> rowWriter) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            for (T item : items) {
                writer.writeNext(rowWriter.writeRow(item));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface RowMapper<T> {
        T mapRow(String[] row);
    }

    public interface RowWriter<T> {
        String[] writeRow(T item);
    }
}
