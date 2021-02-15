package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class Content implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String s;

    public Content(String s) {
        this.s = s;
    }

  }