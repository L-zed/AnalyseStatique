package methods;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import visitors.MethodDeclarationVisitor;
import visitors.ClassDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ClassMethods {
    private int tenPercentClasses =0;

    public ClassMethods() {
    }

    public int getNumberOfClasses(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {
        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(classDeclarationVisitor);
        }
        return classDeclarationVisitor.getClasses().size();
    }

    public List<String> getClassesHaveHighestMethods(ArrayList<File> javaFiles, ASTCreator astCreator)
            throws IOException {
        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();
        Map<TypeDeclaration, Integer> classesNbMethods = new HashMap<>();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(classDeclarationVisitor);
        }

        for (TypeDeclaration nodeClass : classDeclarationVisitor.getClasses()){
            classesNbMethods.put( nodeClass, nodeClass.getMethods().length);
        }

        tenPercentClasses = (int) Math.ceil (0.1*(classDeclarationVisitor.getClasses().size()));

        List<TypeDeclaration>  classesList = classesNbMethods.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(tenPercentClasses)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<String> classesName = new ArrayList<>();
        for (TypeDeclaration cl : classesList){
            classesName.add(cl.getName().getFullyQualifiedName());
        }

        return classesName;
    }

    public List<String> getClassesHaveHighestFields(ArrayList<File> javaFiles, ASTCreator astCreator)
            throws IOException {
        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();
        Map<TypeDeclaration, Integer> classesNbField = new HashMap<>();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(classDeclarationVisitor);
        }

        for (TypeDeclaration nodeClass : classDeclarationVisitor.getClasses()){
            classesNbField.put( nodeClass, nodeClass.getFields().length);
        }

         tenPercentClasses = (int) Math.ceil (0.1*(classDeclarationVisitor.getClasses().size()));

        List<TypeDeclaration>  classesList = classesNbField.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(tenPercentClasses)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<String> classesName = new ArrayList<>();
        for (TypeDeclaration cl : classesList){
            classesName.add(cl.getName().getFullyQualifiedName());
        }

        return classesName;
    }

    public List<String> getClassesWithHighestMethodsFields(ArrayList<File> javaFiles, ASTCreator astCreator)
            throws IOException {
        List<String>  classesMethodsFields = getClassesHaveHighestMethods(javaFiles,astCreator);
        List<String>  classesFields = getClassesHaveHighestFields(javaFiles,astCreator);
        classesMethodsFields.retainAll(classesFields);
        return classesMethodsFields;

    }

    public List<String> getClassesThatHaveMethodX(int x,
                                                  ArrayList<File> javaFiles,
                                                  ASTCreator astCreator) throws IOException {
        List<TypeDeclaration> classesWithMethodX = new ArrayList<>();
        ClassDeclarationVisitor classDeclarationVisitor = new ClassDeclarationVisitor();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(classDeclarationVisitor);
        }

        for (TypeDeclaration typeDeclaration : classDeclarationVisitor.getClasses()){
            MethodDeclarationVisitor methodDeclarationVisitor =new MethodDeclarationVisitor();
            typeDeclaration.accept(methodDeclarationVisitor);
            if(methodDeclarationVisitor.getMethods().size() > x){
                classesWithMethodX.add(typeDeclaration);
            }
        }

        List<String> classesName = new ArrayList<>();
        for (TypeDeclaration cl : classesWithMethodX){
            classesName.add(cl.getName().getFullyQualifiedName());
        }

        return classesName;

    }
}
