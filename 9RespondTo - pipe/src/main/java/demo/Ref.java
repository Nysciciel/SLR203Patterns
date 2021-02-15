package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class Ref implements Serializable {
    private static final long serialVersionUID = 1L;
    public final ActorRef ref1;
    public final ActorRef ref2;

    public Ref(ActorRef ref1, ActorRef ref2) {
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

  }