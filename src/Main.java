import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//         addStudent(1233,"asdasd Ali", 3.44f, "comp", "aa110");
//         modifyStudent(1235, "cgpa", "4.99");
//         deleteStudent(6221);
//        advisorListOfInstructor("TT001");
    }


    public static void addStudent(Integer id, String name, Float cgpa, String department, String instructorId)
            throws IOException {
        File studentFile = new File("students.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(studentFile, "rw");
        randomAccessFile.seek(randomAccessFile.length());

        String addedUser = String.format("%4s,%-30s,%4.2f,%4s,%5s\n", String.valueOf(id), name, cgpa, department,
                instructorId);
        byte[] addedUserByte = addedUser.getBytes("UTF-8");

        randomAccessFile.write(addedUserByte);
        randomAccessFile.close();
    }

    public static void advisorListOfInstructor(String advisorCode) throws IOException {
        File inctructorFile = new File("instructors.txt");
        File specificInstructor = new File("specificInstructor.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(inctructorFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(specificInstructor));

        String line;
        String newLineSym = System.getProperty("line.separator");
        String currentCode;

        while((line = bufferedReader.readLine()) != null){
            currentCode = line.substring(0,5);
            if(String.valueOf(currentCode).equals(advisorCode)){
                bufferedWriter.write(line + newLineSym);
            }
        }
        bufferedReader.close();
        bufferedWriter.close();
    }
    public static void deleteStudent(Integer id) throws IOException {
        File studentFile = new File("students.txt");
        File tmpStudentFile = new File("tmpStudents.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(studentFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tmpStudentFile));

        String line;
        String newLineSym = System.getProperty("line.separator");
        String currentNo;
        while ((line = bufferedReader.readLine()) != null) {
            currentNo = line.substring(0, 4);
            if (!Integer.valueOf(currentNo).equals(id)) {
                bufferedWriter.write(line + newLineSym);
            }
        }
        bufferedReader.close();
        bufferedWriter.close();

        if (!studentFile.delete())
            System.out.println("Failed to delete the original file.");
        if (!tmpStudentFile.renameTo(studentFile))
            System.out.println("Failed to rename the temporary file.");
    }

    public static void modifyStudent(Integer id, String attribute, String newValue) throws IOException {
        File studentFile = new File("students.txt");
        RandomAccessFile randomAccessFile = new RandomAccessFile(studentFile, "rw");
        while (randomAccessFile.getFilePointer() <= randomAccessFile.length() - 1) {
            byte[] buffer = new byte[52];
            randomAccessFile.read(buffer);
            String bufferedString = new String(buffer);
            if (bufferedString.split(",")[0].equals(id.toString())) {
                randomAccessFile.seek(randomAccessFile.getFilePointer() - 52);
                String[] bufferedLine = bufferedString.split(",");
                String formattedLine;
                byte[] lineBytes;
                switch (attribute) {
                    case "id":
                        bufferedLine[0] = newValue;
                        formattedLine = String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1],
                                Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "name":
                        bufferedLine[1] = newValue;
                        formattedLine = String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1],
                                Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "cgpa":
                        bufferedLine[2] = newValue;
                        formattedLine = String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1],
                                Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    case "dept":
                        bufferedLine[3] = newValue;
                        formattedLine = String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1],
                                Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                        break;
                    default:
                        bufferedLine[4] = newValue;
                        formattedLine = String.format("%4s,%-30s,%4.2f,%4s,%5s", bufferedLine[0], bufferedLine[1],
                                Float.valueOf(bufferedLine[2]), bufferedLine[3], bufferedLine[4]);
                        lineBytes = formattedLine.getBytes("UTF-8");
                        randomAccessFile.write(lineBytes);
                }
                break;
            }
        }
    }
}