import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        // Загрузка группы из файла group.txt
        load("group.txt");
    }
    public static void save(Human[] group, String filename)throws IOException
    {
        File file = new File(filename);
        System.out.println(file.getAbsoluteFile());
        file.delete();
        file.createNewFile();

        FileWriter writer = new FileWriter(file);
        // Запись каждого объекта Human в файл
        for(int i = 0 ; i < group.length; i++)
        {
            writer.write(((Object)group[i]).getClass().getSimpleName() + ":\t" + group[i].toString() + ";");
            writer.write('\n');
        }
        writer.close(); //Потоки обязательно нужно закрывать

        // Открытие файла в Notepad++
        String command = "C:\\Program Files\\Notepad++\\notepad++ " + filename;
        Process process;
        process = Runtime.getRuntime().exec(command);
    }
    public static Human[] load(String filename) throws FileNotFoundException {
        ArrayList<Human> al_group = new ArrayList<>();
        File file = new File(filename);
        Scanner scanner = new Scanner(file);    //Создаем и открываем поток чтения из файла
        while(scanner.hasNextLine())  // Чтение каждой строки файла
        {
            String buffer = scanner.nextLine();
            if(buffer.isEmpty())continue;
            String[] values = buffer.trim().split("[:,;]\\s*");

            Human member = HumanFactory.Create(values[0]);    // Создание и инициализация объекта Human
            if (member == null) {
                System.out.println("Invalid type: " + values[0]);
                continue;
            }
            member.init(values);
            al_group.add(member);
        }
        scanner.close();    // Закрытие потока чтения

        Human[] group = al_group.toArray(new Human[0]);
        for (Human human : group) {
            System.out.println(human);
        }
        return group;
    }
}

