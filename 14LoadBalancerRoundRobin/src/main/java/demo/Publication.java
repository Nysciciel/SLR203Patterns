package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class Publication implements Serializable {
    private static final long serialVersionUID = 1L;
    public final String s;

    public Publication(String s) {
        this.s = s;
    }

  }