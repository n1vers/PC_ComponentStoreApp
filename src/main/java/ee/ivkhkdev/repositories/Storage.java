package ee.ivkhkdev.repositories;

import ee.ivkhkdev.repositories.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage<T> implements Repository<T> {

    private final String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
        initialize(); // Инициализация при создании
    }

    private void initialize() {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile(); // Создание файла, если его нет
            } catch (IOException e) {
                System.out.println("Ошибка при создании файла: " + e.getMessage());
            }
        }
    }

    @Override
    public void save(T entity) {
        List<T> entities = load(); // Загружаем существующие сущности
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
        // Используем try-with-resources для автоматического закрытия потоков
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(entities);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + fileName + ". Пожалуйста, убедитесь, что путь существует.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода при сохранении в файл " + fileName + ": " + e.getMessage());
        }
    }

    @Override
    public List<T> load() {
        // Используем try-with-resources для автоматического закрытия потоков
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (List<T>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + fileName + " не найден, создается новый список.");
        } catch (IOException e) {
            System.out.println("Ошибка ввода-вывода при загрузке из файла " + fileName + ": " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден класс при загрузке из файла " + fileName);
        }
        return new ArrayList<>(); // Возвращаем пустой список, если произошла ошибка
    }
}