import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        addStudent(1233,"asdasd Ali", 3.44f, "comp", "aa110");
        modifyStudent(1235, "cgpa", "4.99");
    }
    public static void addStudent (Integer id,String name,Float cgpa,String department,String instructorId) throws IOException {
        File studentFile = new File("students.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(studentFile, "rw");
        randomAccessFile.seek(randomAccessFile.length());
//        FileWriter fileWriter = new FileWriter(studentFile);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
        String addedUser = String.format("%4s,%-30s,%4.2f,%4s,%5s\n", String.valueOf(id), name, cgpa, department, instructorId);
        byte[] addedUserByte =  addedUser.getBytes("UTF-8");
        randomAccessFile.write(addedUserByte);
        randomAccessFile.close();
//        printWriter.printf("%4s,%-30s,%4.2f,%4s,%5s\n", String.valueOf(id), name, cgpa, department, instructorId);
//        printWriter.close();
//        fileWriter.close();
    }

    public static void modifyStudent (Integer id, String attribute, String newValue) throws IOException {
        File studentFile = new File("students.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(studentFile, "rw");
        while (randomAccessFile.getFilePointer() <= randomAccessFile.length() - 1){
            byte[] buffer = new byte[52];
            randomAccessFile.read(buffer);
            String bufferedString = new String(buffer);
            if (bufferedString.split(",")[0].equals(id.toString())) {
                randomAccessFile.seek(randomAccessFile.getFilePointer()-52);
                String[] bufferedLine = bufferedString.split(",");
                String formattedLine;
                byte[] lineBytes;
                switch (attribute){
                    case "id":
                        bufferedLine[0] = newValue;
                        formattedLine =  String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1], Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "name":
                        bufferedLine[1] = newValue;
                        formattedLine =  String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1], Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "cgpa":
                        bufferedLine[2] = newValue;
                        formattedLine =  String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1], Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "dept":
                        bufferedLine[3] = newValue;
                        formattedLine =  String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1], Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    default:
                        bufferedLine[4] = newValue;
                        formattedLine =  String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1], Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                }
                break;
            }
        }
    }

}