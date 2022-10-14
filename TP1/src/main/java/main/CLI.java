package main;

import methods.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CLI {

    public static ArrayList<File> listJavaFilesFromFolder(final File folder) {
        ArrayList<File> javaFiles = new ArrayList<>();
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                javaFiles.addAll(listJavaFilesFromFolder(fileEntry));
            } else if (fileEntry.getName().contains(".java")) {
                javaFiles.add(fileEntry);
            }
        }
        return javaFiles;
    }

    public static void main(String[] args) throws IOException {

        System.out.println("                                                            ******************************" +
                "\n" +
                           "                                                            ****STATIC CODE ANALYSIS******"+
                "\n"+
                           "                                                            ******************************"
        );

        System.out.println("Welcome this application help you to analyze the source code of an application" +"\n");
        //Path application

        String projectSourcePath;
        System.out.println("Please enter your project source path");
        java.util.Scanner entree1 =   new java.util.Scanner(System.in);
        projectSourcePath = entree1.nextLine();

        // Scan jre path
        String jrePath;
        System.out.println("Please enter your project jre path");
        java.util.Scanner entree2 =   new java.util.Scanner(System.in);
        jrePath = entree1.nextLine();

        //Create AST
        final File folder = new File(projectSourcePath);
        ArrayList<File> javaFiles = listJavaFilesFromFolder(folder);
        ASTCreator ast = new ASTCreator(projectSourcePath, jrePath);

        // Menu
        System.out.println("\n" +
                "Enter 1 for the number of the class in your application " + "\n" +
                "Enter 2 for the  Number of lines of code in your application" +"\n" +
                "Enter 3 for the total number of methods in your application" + "\n"+
                "Enter 4 for the number of packages in your application" + "\n"+
                "Enter 5 for the average of methods in your application " + "\n"+
                "Enter 6 for the average number of code lines per method " + "\n"+
                "Enter 7 for the average number of attributes per class " + "\n"+
                "Enter 8 for the 10% of classes with the highest number of methods " + "\n"+
                "Enter 9 for the 10% of classes with the highest number of attributes " + "\n"+
                "Enter 10 for the classes with highest number of attributes and methods " + "\n"+
                "Enter 11 for the classes that have more than X methods  " + "\n"+
                "Enter 12 for The 10% of methods with the highest number of lines of code" + "\n"+
                "Enter 13 for The maximum number of parameters in relation to all the methods of the the application" +"\n"+
                "Enter 0 to exit and have the caller graph" + "\n");

        ClassMethods classNumber = new ClassMethods();

        //Number Method
        MethodMethods methodMethods = new MethodMethods();
        int nbMethods = methodMethods.getNumberOfMethods(javaFiles, ast);


        FieldMethods fieldMethods = new FieldMethods();
        int nbFields = fieldMethods.getNumberOfFields(javaFiles, ast);


        LineMethods lineMethods = new LineMethods();

        int entree;
        do{
            java.util.Scanner entree3 =   new java.util.Scanner(System.in);
            entree = entree3.nextInt();

            switch (entree){
                case 1 :
                    System.out.println("In this application there are " +
                            classNumber.getNumberOfClasses(javaFiles, ast) +
                            " classes");
                    break;
                case 2 :
                    int nbLines = lineMethods.getNumberOfLines(javaFiles, ast);
                    System.out.println("In this application there are " +
                            nbLines +
                            " lines");
                    break;
                case 3 :
                    System.out.println("In this application there are " +
                            nbMethods +
                            " methods");
                    break;
                case 4 :
                    PackageMethods packageNumber = new PackageMethods();
                    int nbPackages = packageNumber.gtePackageNumber( javaFiles, ast);
                    System.out.println(
                            "In this application there are " +
                                    nbPackages +
                                    " packages");
                    break;
                case 5 :
                    double averageMethods = (double)nbMethods/(double)javaFiles.size();
                    System.out.println(
                            "Average number of methods of the application " +
                                    averageMethods );
                    break;
                case 6 :
                    System.out.println(
                            "Average number of methods lines " +
                            lineMethods.averageNumberMethodLines(javaFiles, ast));
                    break;
                case 7 :
                    System.out.println(
                            "In this application there are " + nbFields+ " fields");

                    break;
                case 8 :
                    System.out.println("10% of classes that have the most number of methods : " +
                            classNumber.getClassesHaveHighestMethods(javaFiles,ast));
                    break;
                case 9 :
                    System.out.println("10% of classes that have the most number of fields : " +
                            classNumber.getClassesHaveHighestFields(javaFiles,ast));
                    break;
                case 10 :
                    System.out.println("Classes that have most methods and fields : " +
                            classNumber.getClassesWithHighestMethodsFields(javaFiles, ast));
                    break;
                case 11 :
                    int x;
                    System.out.println("Enter number of methods ");
                    java.util.Scanner entree4 =   new java.util.Scanner(System.in);
                    x = entree4.nextInt();
                    System.out.println("Classes that have more than "+ x +" method  : " +
                            classNumber.getClassesThatHaveMethodX(x, javaFiles, ast));

                case 12 :
                    System.out.println("10% of methods that have the most number of lines "
                            + methodMethods.getMethodsHaveHighestMethods(javaFiles,ast));

                    break;
                case 13 :
                    System.out.println(
                            "maximum number of parameters in relation to all the methods of the the application" +
                            methodMethods.getHighestNumberOfParameter(javaFiles,ast));
            }
            if (entree != 0){
                System.out.println("enter an other number");
            }
        }while (entree != 0);

        Graph graph = new Graph("graph");
        graph.createCallGraph(ast, javaFiles);
    }
}
