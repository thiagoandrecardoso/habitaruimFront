module org.hibernate.orm.c3p0 {

    requires transitive java.sql;
    requires transitive org.hibernate.orm.core;
    requires transitive org.jboss.logging;

    exports org.hibernate.c3p0.internal;

    provides org.hibernate.boot.registry.selector.StrategyRegistrationProvider with
            org.hibernate.c3p0.internal.StrategyRegistrationProviderImpl;

}
