package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.util.ArrayList;
import java.util.List;

public class TypeDeclarationVisitor extends ASTVisitor {
    List<TypeDeclaration> classes = new ArrayList<>();

    @Override
    public boolean visit(TypeDeclaration node) {
        if (!node.isInterface()){
            classes.add(node);
        }

        return super.visit(node);
    }

    public List<TypeDeclaration> getClasses() {
        return classes;
    }
}
