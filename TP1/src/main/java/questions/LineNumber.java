package questions;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import visitors.LineDeclarationVisitor;
import visitors.MethodDeclarationVisitor;
import visitors.TypeDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LineNumber {
    public int getNumberOfLines(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {
        LineDeclarationVisitor lineDeclarationVisitor = new LineDeclarationVisitor();
        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(lineDeclarationVisitor);
        }
        return lineDeclarationVisitor.getLinesNumber();
    }

    public int averageNumberMethodLines(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();
        int nbMethodsLines = 0;

        for( File javaFile : javaFiles){
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(methodDeclarationVisitor);
        }

        for (MethodDeclaration methodDeclaration : methodDeclarationVisitor.getMethods()){
            if (methodDeclaration.getBody() != null){
                nbMethodsLines += methodDeclaration.getBody().statements().size();
                //System.out.println(methodDeclaration.getBody().statements().size() + " "+ methodDeclaration.getName().getFullyQualifiedName());
            }
        }

        return nbMethodsLines/methodDeclarationVisitor.getMethods().size();
    }
}
