module c3p0 {
    requires transitive java.desktop;
    requires transitive java.logging;
    requires transitive java.management;
    requires transitive java.naming;
    requires transitive java.sql;
    requires transitive java.xml;

    exports com.mchange;
    exports com.mchange.v2;
    exports com.mchange.v2.c3p0;
    exports com.mchange.v2.c3p0.cfg;
    exports com.mchange.v2.c3p0.codegen;
    exports com.mchange.v2.c3p0.debug;
    exports com.mchange.v2.c3p0.example;
    exports com.mchange.v2.c3p0.filter;
    exports com.mchange.v2.c3p0.impl;
    exports com.mchange.v2.c3p0.jboss;
    exports com.mchange.v2.c3p0.management;
    exports com.mchange.v2.c3p0.mbean;
    exports com.mchange.v2.c3p0.stmt;
    exports com.mchange.v2.c3p0.subst;
    exports com.mchange.v2.c3p0.test;
    exports com.mchange.v2.c3p0.util;
    exports com.mchange.v2.resourcepool;

}
