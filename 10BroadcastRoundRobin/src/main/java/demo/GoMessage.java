package demo;

import java.io.Serializable;
import java.util.ArrayList;
import akka.actor.ActorRef;

public class GoMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    public final ActorRef broad;

    public GoMessage(ActorRef broad) {this.broad = broad;
    }

  }