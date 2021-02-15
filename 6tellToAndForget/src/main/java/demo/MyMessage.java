package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class MyMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String s;
    public final ActorRef ref1;
    public final ActorRef ref2;

    public MyMessage(String s) {
        this.s = s;
        this.ref1 = null;
        this.ref2 = null;
    }

    public MyMessage(String s, ActorRef ref) {
        this.s = s;
        this.ref1 = ref;
        this.ref2 = null;
    }

    public MyMessage(ActorRef ref1, ActorRef ref2) {
        this.s = null;
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

  }