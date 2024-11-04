package ee.ivkhkdev.repositories;

import ee.ivkhkdev.interfaces.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Storage<T> implements Repository<T> {

    private final String fileName;

    public Storage(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void save(T entity) {
        List<T> entities = this.load(); // Загружаем существующие сущности
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(entities);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл"+e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода"+e.getMessage());
        }
    }

    @Override
    public void saveAll(List<T> entities) {
        if (entities == null) {
            entities = new ArrayList<>();
        }

        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(entities);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл"+e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода"+e.getMessage());
        }
    }

    @Override
    public List<T> load() {
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (List<T>) objectInputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Нет такого файла"+e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка вывода"+e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден класс"+e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public void update(int index, T entity) {
        List<T> entities = this.load();
        if (entities == null) {
            return;
        }
        entities.set(index, entity);
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(entities);
            objectOutputStream.flush();
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл"+e.getMessage());
        } catch (IOException e) {
            System.out.println("Ошибка ввода"+e.getMessage());
        }
    }
}