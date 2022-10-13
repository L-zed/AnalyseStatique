package questions;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import visitors.MethodDeclarationVisitor;
import visitors.TypeDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;

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


}
