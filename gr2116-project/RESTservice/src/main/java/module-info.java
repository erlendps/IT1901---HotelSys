@SuppressWarnings("module") // This is to suppress warnings about jersey modules being "unstable"
module gr2116.RESTservice {
  requires jakarta.ws.rs;
  requires jersey.common;
  requires jersey.server;
  requires jersey.media.json.jackson;
  requires java.net.http;

  requires org.glassfish.hk2.api;
  requires org.slf4j;

  requires gr2116.core;
  requires gr2116.persistence;

  opens gr2116.RESTservice.restapi to jersey.server;
  opens gr2116.RESTservice.restserver to jersey.server;
}
