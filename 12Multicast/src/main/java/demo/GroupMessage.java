package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class GroupMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String grp;
    public final String s;

    public GroupMessage(String grp, String s) {
        this.grp = grp;
        this.s = s;
    }

  }