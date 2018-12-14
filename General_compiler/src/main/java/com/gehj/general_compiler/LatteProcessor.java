package com.gehj.general_compiler;


import com.gehj.general_annotation.AppRegisterGenerator;
import com.gehj.general_annotation.EntryGenerator;
import com.gehj.general_annotation.PayEntryGenerator;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

/**
 * Created by 傅令杰 on 2017/4/22
 * 代码生成的核心类,处理注解; 本类主要模仿butterknife写的
 * 这个包的主要作用是动态生成微信登录支付的强制要求的类;
 */

@SuppressWarnings("unused") // 不是显示的调用
@AutoService(Processor.class) // 生成meta信息
public final class LatteProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        final Set<String> types = new LinkedHashSet<>();
        final Set<Class<? extends Annotation>> supportAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportAnnotations) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        final Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(EntryGenerator.class);
        annotations.add(PayEntryGenerator.class);
        annotations.add(AppRegisterGenerator.class);
        return annotations;
    }

    /*
    * @param annotations 当前要处理的注解类型
    * @param roundEnv 这个对象提供当前或者上一次注解处理中被注解标注的源文件元素。（获得所有被标注的元素）
    * 如果返回 true，则这些注解已声明并且不要求后续 Processor 处理它们；
    *  如果返回 false，则这些注解未声明并且可能要求后续 Processor 处理它们
    *
    * */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {//这是核心类
        generateEntryCode(env);
        generatePayEntryCode(env);
        generateAppRegisterCode(env);
        return true;
    }

    /*PackageElement        --->    包
    ExecuteableElement      --->    方法、构造方法
    VariableElement         --->    成员变量、enum常量、方法或构造方法参数、局部变量或异常参数。
    TypeElement             --->    类、接口
    TypeParameterElement    --->    在方法或构造方法、类、接口处定义的泛型参数。
    */
    //扫描每个类,
    private void scan(RoundEnvironment env, Class<? extends Annotation> annotation,
                      AnnotationValueVisitor visitor) { //env上下文环境;
        //Element表示类.属性.方法
        for (Element typeElement : env.getElementsAnnotatedWith(annotation)) {//找到环境中的所有包含注解的元素
            final List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();//获取继承注解;

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                //ExecutableElement,AnnotationValue需要点进去看源码的注解;
                final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror
                        .getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues
                        .entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

    private void generateEntryCode(RoundEnvironment env) {
        final EntryVisitor entryVisitor = new EntryVisitor(processingEnv.getFiler());
        scan(env, EntryGenerator.class, entryVisitor);
    }

    private void generatePayEntryCode(RoundEnvironment env) {
        final PayEntryVisitor payEntryVisitor = new PayEntryVisitor(processingEnv.getFiler());
        scan(env, PayEntryGenerator.class, payEntryVisitor);
    }

    private void generateAppRegisterCode(RoundEnvironment env) {
        final AppRegisterVisitor appRegisterVisitor = new AppRegisterVisitor(processingEnv.getFiler());
        scan(env, AppRegisterGenerator.class, appRegisterVisitor);
    }
}
