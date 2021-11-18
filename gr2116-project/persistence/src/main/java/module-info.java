module gr2116.persistence {
  requires transitive gr2116.core;
  requires transitive com.fasterxml.jackson.databind;

  exports gr2116.persistence;
  exports gr2116.persistence.internal;
}