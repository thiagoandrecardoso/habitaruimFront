module javassist {
    requires java.management;
    requires jdk.attach;

    requires transitive java.desktop;
    requires transitive java.instrument;
    requires transitive jdk.jdi;

    exports javassist;
    exports javassist.bytecode;
    exports javassist.bytecode.analysis;
    exports javassist.bytecode.annotation;
    exports javassist.bytecode.stackmap;
    exports javassist.compiler;
    exports javassist.compiler.ast;
    exports javassist.convert;
    exports javassist.expr;
    exports javassist.runtime;
    exports javassist.scopedpool;
    exports javassist.tools;
    exports javassist.tools.reflect;
    exports javassist.tools.rmi;
    exports javassist.tools.web;
    exports javassist.util;
    exports javassist.util.proxy;

}
