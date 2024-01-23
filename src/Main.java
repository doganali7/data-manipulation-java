import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        // addStudent(1233,"asdasd Ali", 1.44, "comp", "aa110");
        // modifyStudent(1235, "cgpa", "4.99");
        // deleteStudent(6221);
        // advisorListOfInstructor("TT001");
        // displayStudent("allWithAdvisors", null);
        // newFileForAdvisor("TT001");
    }

    public static void displayStudent(String displayingOption, Integer studentId) throws IOException {
        File studentFile = new File("students.txt");
        FileReader studentFileReader = new FileReader(studentFile);
        BufferedReader studentBufferedReader = new BufferedReader(studentFileReader);
        String studentLine;

        while ((studentLine = studentBufferedReader.readLine()) != null) {
            String[] studentInfo = studentLine.split(",");
            String advisorId = studentInfo[4].trim();
            
            switch (displayingOption) {
                case "all":
                    System.out.println(Arrays.toString(studentInfo));
                    break;
                case "allWithAdvisors":
                case "specific":
                    if (displayingOption.equals("specific") && !studentId.toString().equals(studentInfo[0].trim())) {
                        continue;
                    }
                    File instructorFile = new File("instructors.txt");
                    BufferedReader instructorReader = new BufferedReader(new FileReader(instructorFile));
                    String instructorLine;

                    while ((instructorLine = instructorReader.readLine()) != null) {
                        String[] instructorInfo = instructorLine.split(",");
                        if (advisorId.equals(instructorInfo[0].trim())) {
                            System.out.println("Student: " + Arrays.toString(studentInfo) + "\nAdvisor: " + Arrays.toString(instructorInfo));
                            break;
                        }
                    }
                        instructorReader.close();
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
        studentBufferedReader.close();
    }

    public static void newFileForAdvisor(String advisorCode) throws IOException {
        File instructorFile = new File("instructors.txt");
        File specificInstructor = new File(advisorCode + ".txt");

        FileReader fileReader = new FileReader(instructorFile);
        FileWriter fileWriter = new FileWriter(specificInstructor);

        BufferedReader bufferedReader = new BufferedReader(fileReader);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String line;
        String newLineSym = System.getProperty("line.separator");
        String currentCode;

        while ((line = bufferedReader.readLine()) != null) {
            currentCode = line.substring(0, 5);
            if (String.valueOf(currentCode).equals(advisorCode)) {
                bufferedWriter.write(line + newLineSym);
            }
        }
        bufferedReader.close();
        bufferedWriter.close();
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
        File instructorFile = new File("instructors.txt");
        File specificInstructor = new File("specificInstructor.txt");

        BufferedReader bufferedReader = new BufferedReader(new FileReader(instructorFile));
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

        studentFile.delete();
        tmpStudentFile.renameTo(studentFile);
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
            randomAccessFile.close();
        }
    }
}