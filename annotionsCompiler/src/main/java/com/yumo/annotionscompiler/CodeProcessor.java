package com.yumo.annotionscompiler;

import com.google.auto.service.AutoService;
import com.yumo.annotation.Code;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.annotation.processing.Filer;

import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class CodeProcessor extends AbstractProcessor {

    private final String SUFFIX = "$TestInfo";

    private Messager mMessager;
    private Filer mFiler;
    private Types mTypeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mMessager = processingEnvironment.getMessager();
        mFiler = processingEnvironment.getFiler();
        mTypeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Element e : roundEnvironment.getElementsAnnotatedWith(Code.class)){
            Code ca = e.getAnnotation(Code.class);
            TypeElement clazz = (TypeElement)e.getEnclosingElement();
            try {
                generateCode(e, ca, clazz);
            }catch (IOException xx){
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, xx.toString());
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(Code.class.getCanonicalName());
        mMessager.printMessage(Diagnostic.Kind.NOTE, Code.class.getCanonicalName());
        return annotations;
    }


    private void generateCode(Element e, Code ca, TypeElement clazz) throws IOException{

        JavaFileObject f = mFiler.createSourceFile(clazz.getQualifiedName()+SUFFIX);
        mMessager.printMessage(Diagnostic.Kind.NOTE, "Creating "+f.toUri());

        Writer w = f.openWriter();
        try {
            PrintWriter pw = new PrintWriter(w);
            String pack = clazz.getQualifiedName().toString();
            pw.println("package " + pack.substring(0, pack.lastIndexOf('.')) + ";"); //create package element
            pw.println("\n class " + clazz.getSimpleName() + "Autogenerate {");//create class element
            pw.println("\n    protected " + clazz.getSimpleName() + "Autogenerate() {}");//create class construction
            pw.println("    protected final void message() {");//create method
            pw.println("\n//" + e);
            pw.println("//" + ca);
            pw.println("\n        System.out.println(\"author:" + ca.author() + "\");");
            pw.println("\n        System.out.println(\"date:" + ca.date() + "\");");
            pw.println("    }");
            pw.println("}");
            pw.flush();
        }finally {
            w.close();
        }

    }
}
