module java.transaction {
    requires transitive java.rmi;
    requires transitive java.transaction.xa;

    exports javax.transaction;

}
