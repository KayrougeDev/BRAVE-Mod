package fr.kayrouge.brave.agents;

public enum Agents {

    NONE(0, null),
    REYNA(1, null),
    OMEN(2, null);


    final int id;
    final Agent agent;
    Agents(int id, Agent agent) {
        this.id = id;
        this.agent = agent;
    }

    public int getId() {
        return id;
    }

    public Agent getAgent() {
        return agent;
    }

    public static Agents getByID(int id) {
        for(Agents a : values()) {
            if(a.getId() == id) {
                return a;
            }
        }
        return NONE;
    }
}
