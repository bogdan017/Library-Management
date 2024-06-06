package services;

import library.Reader;
import exceptions.ReaderNotFoundException;
import java.util.*;

public class ReaderService {
    private Map<String, Reader> readers = new HashMap<>();

    public void addReader(Reader reader) {
        readers.put(reader.getId(), reader);
        System.out.println("Reader added: " + reader.getName());
    }

    public void removeReader(String readerId) throws ReaderNotFoundException {
        if (!readers.containsKey(readerId)) {
            throw new ReaderNotFoundException("Reader not found: " + readerId);
        }
        readers.remove(readerId);
        System.out.println("Reader removed: " + readerId);
    }

    public Reader getReaderById(String readerId) throws ReaderNotFoundException {
        Reader reader = readers.get(readerId);
        if (reader == null) {
            throw new ReaderNotFoundException("Reader not found: " + readerId);
        }
        return reader;
    }

    public List<Reader> listReaders() {
        return new ArrayList<>(readers.values());
    }

    public List<Reader> searchReadersByName(String name) {
        List<Reader> result = new ArrayList<>();
        for (Reader reader : readers.values()) {
            if (reader.getName().equalsIgnoreCase(name)) {
                result.add(reader);
            }
        }
        return result;
    }
}
