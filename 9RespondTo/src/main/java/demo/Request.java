package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String s;
    public final ActorRef ref;

    public Request(String s, ActorRef ref) {
        this.s = s;
        this.ref = ref;
    }

  }