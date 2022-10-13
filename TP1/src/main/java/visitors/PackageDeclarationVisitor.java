package visitors;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.PackageDeclaration;


public class PackageDeclarationVisitor extends ASTVisitor {
    private String packageName;

    @Override
    public boolean visit(PackageDeclaration packageDeclaration){
        packageName = packageDeclaration.getName().getFullyQualifiedName();
        return super.visit(packageDeclaration);
    }

    public String getPackageName() {
        return this.packageName;
    }
}
