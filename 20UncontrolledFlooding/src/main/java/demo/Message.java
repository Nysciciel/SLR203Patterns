package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public final ArrayList<ActorRef> s;

    public Message(ArrayList<ActorRef> s) {
        this.s = s;
    }

  }