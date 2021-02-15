package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class EndSession implements Serializable {
    private static final long serialVersionUID = 1L;
    public final ActorRef ref;

    public EndSession(ActorRef ref) {
        this.ref = ref;
    }
  }