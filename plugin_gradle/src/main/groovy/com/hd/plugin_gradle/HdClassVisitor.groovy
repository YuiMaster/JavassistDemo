package com.hd.plugin_gradle

import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.MethodVisitor

public class HdClassVisitor extends ClassVisitor {
    private String className;
    private String superName;

    public HdClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.className = name;
        this.superName = superName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("ClassVisitor visitMethod name-------" + name + ", superName is " + superName);
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (superName.equals("com/hd/javassistdemo/MainActivity")) {
            if (name.startsWith("onCreate")) {
                // 处理onCreate()方法
                return new HdMethodVisitor(mv, className, name);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}