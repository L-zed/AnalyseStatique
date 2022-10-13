package visitors;


import org.eclipse.jdt.core.dom.*;


public class LineDeclarationVisitor extends ASTVisitor {
    private int linesNumber;

    public LineDeclarationVisitor() {
        this.linesNumber = 0;
    }

    public int getLinesNumber() {
        return linesNumber;
    }

    @Override
    public boolean visit(AssertStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(BreakStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(ConstructorInvocation node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(ContinueStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(DoStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(EmptyStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(EnhancedForStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(ExpressionStatement node){
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(ForStatement node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(FieldDeclaration node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(IfStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(ImportDeclaration node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(LabeledStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(SuperConstructorInvocation node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(PackageDeclaration node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(ReturnStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(SwitchCase node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(SwitchStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(ThrowStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(TryStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        linesNumber++;
        return super.visit(node);
    }

    @Override
    public boolean visit(SynchronizedStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(TypeDeclarationStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(VariableDeclarationStatement node) {
        linesNumber++;
        return true;
    }

    @Override
    public boolean visit(WhileStatement node) {
        linesNumber++;
        return true;
    }
}
