package questions;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import visitors.FieldDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class FieldMethods {


    public FieldMethods() {
    }

    public int getNumberOfFields(ArrayList<File> javaFiles, ASTCreator astCreator ) throws IOException {
        FieldDeclarationVisitor fieldDeclarationVisitor = new FieldDeclarationVisitor();

        int nbFields = 0;
        for (File javaFile : javaFiles) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(fieldDeclarationVisitor);
            nbFields+= fieldDeclarationVisitor.getFieldDeclarations().size();
        }
        return nbFields;
    }
}
