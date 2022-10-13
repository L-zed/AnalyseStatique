package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import java.util.ArrayList;
import java.util.List;

public class FieldDeclarationVisitor extends ASTVisitor {

    List<FieldDeclaration> fieldDeclarations = new ArrayList<>();

    public FieldDeclarationVisitor() {
    }

    @Override
    public boolean visit(FieldDeclaration node){
        fieldDeclarations.add(node);
        return super.visit(node);
    }

    public List<FieldDeclaration> getFieldDeclarations() {
        return fieldDeclarations;
    }
}
