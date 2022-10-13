package questions;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.dom.CompilationUnit;

import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import visitors.MethodDeclarationVisitor;
import visitors.MethodInvocationVisitor;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Graph {

    private List<String> methodInvocations = new ArrayList<>();
    private final String path = "src/main/resources/";
    private String fileName;
    private String dotFilePath;
    private String pngFilePath;

    public Graph(String fileName) {
        this.fileName = fileName;
        dotFilePath = path + fileName + ".dot";
        pngFilePath = path + fileName + ".png";
    }

    public void createCallGraph(ASTCreator astCreator, ArrayList<File> javaFiles) throws IOException {
        FileWriter writer = new FileWriter(dotFilePath);
        writer.write("digraph \"graph\" {\n");
        MethodDeclarationVisitor methodDeclarationVisitor = new MethodDeclarationVisitor();

        for (File javaFile : javaFiles) {
            String content = FileUtils.readFileToString(javaFile);
            CompilationUnit cu = astCreator.parse(content.toCharArray());

            cu.accept(methodDeclarationVisitor);

            for (MethodDeclaration method : methodDeclarationVisitor.getMethods()) {

                MethodInvocationVisitor methodInvocationVisitor = new MethodInvocationVisitor();
                method.accept(methodInvocationVisitor);


                for (MethodInvocation methodInvocation : methodInvocationVisitor.getMethods()) {

                    methodInvocations.add("\t" + "\"" + getStaticTypeOfCallerMethod(method)
                            + "\"->\"" + getStaticTypeOfInvokedMethod(methodInvocation) + "()\";\n");
                }
            }
        }
        methodInvocations.stream().distinct().collect(Collectors.toList()).forEach(methodInvocation -> {
            try {
                writer.write(methodInvocation);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.write("}");
        writer.close();
        System.out.println("File generated");
        dotToPng();
    }

    private String getStaticTypeOfCallerMethod(MethodDeclaration method) {

        String callerMethodName = "";

        IMethodBinding resolveBinding = method.resolveBinding();
        if ((resolveBinding != null) && (resolveBinding.getDeclaringClass()!= null) ) {
            callerMethodName += method.resolveBinding().getDeclaringClass().getName() + ".";
        }
        callerMethodName += method.getName() + "()";

        return callerMethodName;
    }

    private String getStaticTypeOfInvokedMethod(MethodInvocation methodInvocation) {
        String invokedMethodName = "";

        if (( methodInvocation.getExpression() != null) &&
                (methodInvocation.getExpression().resolveTypeBinding() != null)) {
            invokedMethodName+= methodInvocation.getExpression().resolveTypeBinding().getName()+".";
        }
        else
        {
            if (methodInvocation.resolveMethodBinding() != null){
                invokedMethodName+= methodInvocation.resolveMethodBinding().getDeclaringClass().getName()+".";
            }
        }

        invokedMethodName+= methodInvocation.getName();
        return invokedMethodName;
    }

    private void dotToPng() {
        try (InputStream dotFile = new FileInputStream(dotFilePath)) {

            MutableGraph mutableGraph = new Parser().read(dotFile);
            Graphviz.fromGraph(mutableGraph).width(10000).render(Format.PNG).toFile(new File(pngFilePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}