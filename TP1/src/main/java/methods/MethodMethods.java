package methods;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import visitors.MethodDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MethodMethods {

    public MethodMethods() {
    }

    public int getNumberOfMethods(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(methodDeclarationVisitor);
        }
        return methodDeclarationVisitor.getMethods().size();
    }

    public int getHighestNumberOfParameter(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {

        int nbMax = 0;

        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(methodDeclarationVisitor);
        }

        for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods()){
            if (methodDeclaration.parameters().size() > nbMax){
                nbMax = methodDeclaration.parameters().size();
            }
        }

        return nbMax;
    }

    public List<String> getMethodsHaveHighestMethods(ArrayList<File> javaFiles, ASTCreator astCreator)
            throws IOException {
        int tenPercentMethods = 0;
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        Map<MethodDeclaration, Integer> methodsNbLines = new HashMap<>();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(methodDeclarationVisitor);
        }

        for (MethodDeclaration nodeMethod : methodDeclarationVisitor.getMethods()){
            int nbMethodsLines = 0;
            if (nodeMethod.getBody() != null){
                nbMethodsLines += nodeMethod.getBody().statements().size();
            }
            methodsNbLines.put( nodeMethod, nbMethodsLines);
        }

        tenPercentMethods = (int) Math.ceil (0.1*(methodDeclarationVisitor.getMethods().size()));
        System.out.println(tenPercentMethods);

        List<MethodDeclaration>  classesList = methodsNbLines.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(tenPercentMethods)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<String> classesName = new ArrayList<>();
        for (MethodDeclaration ml : classesList){
            classesName.add(ml.getName().getFullyQualifiedName());
        }

        return classesName;
    }


}
