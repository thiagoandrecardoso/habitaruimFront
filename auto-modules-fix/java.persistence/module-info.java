module java.persistence {
    requires java.logging;

    uses javax.persistence.spi.PersistenceProvider;

    requires transitive java.instrument;
    requires transitive java.sql;

    exports javax.persistence;
    exports javax.persistence.criteria;
    exports javax.persistence.metamodel;
    exports javax.persistence.spi;

}
