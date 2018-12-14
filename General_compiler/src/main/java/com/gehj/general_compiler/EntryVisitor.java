package com.gehj.general_compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor7;

/**
 * Created by 傅令杰 on 2017/4/22
 */
final class EntryVisitor extends SimpleAnnotationValueVisitor7<Void, Void> {

    private final Filer FILER;//需要遍历的对象
    private String mPackageName = null;//最终要拿到的;

    EntryVisitor(Filer FILER) {
        this.FILER = FILER;
    }

    @Override
    public Void visitString(String s, Void p) {
        mPackageName = s;
        return p;
    }

    @Override
    public Void visitType(TypeMirror t, Void p) {//TypeMirror需要找出的类型;
        generateJavaCode(t);//根据注解的类生成代码
        return p;
    }

    private void generateJavaCode(TypeMirror typeMirror) {
        final TypeSpec targetActivity = //TypeSpec==生成的class
                TypeSpec.classBuilder("WXEntryActivity")
                        .addModifiers(Modifier.PUBLIC)
                        .addModifiers(Modifier.FINAL)
                        .superclass(TypeName.get(typeMirror))//继承于模板类的类类型
                        .build();

        final JavaFile javaFile = JavaFile.builder(mPackageName + ".wxapi", targetActivity)
                .addFileComment("微信入口文件")
                .build();
        try {
            javaFile.writeTo(FILER);//javaFile为生成的文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
