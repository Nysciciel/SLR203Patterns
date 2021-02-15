package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class GroupCreate implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String grp;
    public final ActorRef ref1;
    public final ActorRef ref2;

    public GroupCreate(String grp, ActorRef ref1, ActorRef ref2) {
        this.grp = grp;
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

  }