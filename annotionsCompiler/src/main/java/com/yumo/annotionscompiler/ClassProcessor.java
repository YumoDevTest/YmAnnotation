package com.yumo.annotionscompiler;

import com.google.auto.service.AutoService;
import com.yumo.annotation.YmClassTest;
import com.yumo.annotation.YmMethodTest;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class ClassProcessor extends AbstractProcessor {

    /**
     * 相当于处理器的Main函数，定义扫描、评估和处理器注解的代码，以及生成的Java文件。
     * @param set
     * @param roundEnvironment
     * @return
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.NOTE, "printMessage:test");

        for (Element element : roundEnvironment.getElementsAnnotatedWith(YmClassTest.class)){
            messager.printMessage(Diagnostic.Kind.NOTE, "printMessage:"+element.toString()+" kind:"+element.getKind());
            if (element.getKind() == ElementKind.CLASS){
                messager.printMessage(Diagnostic.Kind.NOTE, "printMessage:"+element.toString());
            }
        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    /**
     * 指定这个注解处理器是注册给哪个注解的。
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(YmClassTest.class.getCanonicalName());
        annotations.add(YmMethodTest.class.getCanonicalName());
        //return super.getSupportedAnnotationTypes();
        return annotations;
    }

    /**
     * 指定使用的java版本，通常为 SourceVersion.latestSupported() 最新版本。
     * @return
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        //return super.getSupportedSourceVersion();
        return SourceVersion.latestSupported();
    }
}
