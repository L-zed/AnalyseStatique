package methods;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;
import visitors.PackageDeclarationVisitor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PackageMethods {
    public int gtePackageNumber(ArrayList<File> javaFiles, ASTCreator astCreator) throws IOException {

        Set<String> namePackages = new HashSet<>();
        Set<String> namePackagesSplit = new HashSet<>();
        PackageDeclarationVisitor packageDeclarationVisitor = new PackageDeclarationVisitor();
        for (File javaFile: javaFiles ) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());
            cu.accept(packageDeclarationVisitor);
            namePackages.add(packageDeclarationVisitor.getPackageName());
        }

        for (String name: namePackages) {
            namePackagesSplit.addAll(Arrays.asList(name.split("\\.")));
        }

        return namePackagesSplit.size();
    }
}
